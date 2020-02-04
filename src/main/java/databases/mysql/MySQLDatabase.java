package databases.mysql;

import com.mysql.cj.jdbc.MysqlDataSource;
import databases.Database;
import mainPage.MainPageModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQLDatabase extends Database {
    private final static String PROPS_FILE_NAME = "db.properties";
    private final static String PROPS_KEY_URL = "mysql.url";
    private final static String PROPS_KEY_USERNAME = "mysql.username";
    private final static String PROPS_KEY_PASSWORD = "mysql.password";

    private static MySQLDatabase mySqlDatabase = null;
    private MysqlDataSource ds;

    private MySQLDatabase() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(getClass().getClassLoader().getResource(PROPS_FILE_NAME).getFile())) {
            props.load(fis);
        } catch (IOException e) {
            Logger lgr = Logger.getLogger(MySQLDatabase.class.getName());
            lgr.log(Level.SEVERE, e.getMessage(), e);
        }
        ds = new MysqlDataSource();
        ds.setURL(props.getProperty(PROPS_KEY_URL));
        ds.setUser(props.getProperty(PROPS_KEY_USERNAME));
        ds.setPassword(props.getProperty(PROPS_KEY_PASSWORD));
    }

    public static MySQLDatabase getInstance() {
        if (mySqlDatabase == null) {
            mySqlDatabase = new MySQLDatabase();
        }
        return mySqlDatabase;
    }

    //region parent
    @Override
    public boolean isRegistered(MainPageModel mainPageModel) {
        String isRegisteredQuery = "SELECT EXISTS (SELECT * FROM duels.user_md5 WHERE user_name='" + mainPageModel.getLogin() + "')";
        try (Connection conn = ds.getConnection();
             PreparedStatement pst = conn.prepareStatement(isRegisteredQuery);
             ResultSet rs = pst.executeQuery()) {
            boolean result = false;
            if (rs.next()) {
                result = rs.getString(1).equals("1");
            }
            return result;
        } catch (SQLException e) {
            Logger lgr = Logger.getLogger(MySQLDatabase.class.getName());
            lgr.log(Level.SEVERE, e.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean attemptToAuthorize(MainPageModel mainPageModel) {
        String authorizeQuery = "SELECT EXISTS (SELECT * FROM duels.user_md5 " +
                "WHERE user_name='" + mainPageModel.getLogin() +
                "' AND password=MD5('" + mainPageModel.getPassword() + "'))";
        try (Connection conn = ds.getConnection();
             PreparedStatement pst = conn.prepareStatement(authorizeQuery);
             ResultSet rs = pst.executeQuery()) {
            boolean result = false;
            if (rs.next()) {
                result = rs.getString(1).equals("1");
            }
            return result;
        } catch (SQLException e) {
            Logger lgr = Logger.getLogger(MySQLDatabase.class.getName());
            lgr.log(Level.SEVERE, e.getMessage(), e);
        }
        return false;
    }

    @Override
    public void signUpNewPlayer(MainPageModel mainPageModel) {
        String signUpQuery = "INSERT INTO duels.user_md5 VALUES ('" + mainPageModel.getLogin() + "', MD5('" + mainPageModel.getPassword() + "'))";
        try (Connection conn = ds.getConnection()) {
            try (Statement st = conn.createStatement()) {
                conn.setAutoCommit(false);
                st.executeUpdate(signUpQuery);
                conn.commit();
            } catch (SQLException e) {
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    Logger lgr = Logger.getLogger(MySQLDatabase.class.getName());
                    lgr.log(Level.SEVERE, e1.getMessage(), e1);
                }
                Logger lgr = Logger.getLogger(MySQLDatabase.class.getName());
                lgr.log(Level.SEVERE, e.getMessage(), e);
            }
        } catch (SQLException e) {
            Logger lgr = Logger.getLogger(MySQLDatabase.class.getName());
            lgr.log(Level.SEVERE, e.getMessage(), e);
        }
    }
    //endregion
}