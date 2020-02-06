package databases.mysql;

import com.mysql.cj.jdbc.MysqlDataSource;
import databases.Database;
import databases.entities.PlayersData;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import static mainPage.playersPool.player.PlayerModel.*;

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
    //region accounts
    @Override
    public boolean isRegistered(String login) {
        String isRegisteredQuery = "SELECT EXISTS (SELECT * FROM duels.user_md5 WHERE user_name='" + login + "')";
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
    public boolean attemptToAuthorize(String login, String password) {
        String authorizeQuery = "SELECT EXISTS (SELECT * FROM duels.user_md5 " +
                "WHERE user_name='" + login +
                "' AND password=MD5('" + password + "'))";
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
    public void signUpNewPlayer(String login, String password) {
        String signUpQuery = "INSERT INTO duels.user_md5 VALUES ('" + login + "', MD5('" + password + "'))";
        String createNewDataQuery = "INSERT INTO duels.user_data VALUES ('" + login + "', " + NEW_BORN_ATTACK + ", " + NEW_BORN_STAMINA + ", " + NEW_BORN_RATING + ")";
        try (Connection conn = ds.getConnection()) {
            try (Statement st = conn.createStatement();
                 Statement st2 = conn.createStatement()) {
                conn.setAutoCommit(false);
                st.executeUpdate(signUpQuery);
                st2.executeUpdate(createNewDataQuery);
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
    //region players data
    public PlayersData getPlayersData(String login) {
        PlayersData playersData = new PlayersData();
        String getDataQuery = "SELECT attack, stamina, rating FROM duels.user_data";
        try (Connection conn = ds.getConnection();
             PreparedStatement pst = conn.prepareStatement(getDataQuery);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                playersData.setAttack(rs.getInt(1));
                playersData.setAttack(rs.getInt(2));
                playersData.setAttack(rs.getInt(3));
            }
        } catch (SQLException e) {
            Logger lgr = Logger.getLogger(MySQLDatabase.class.getName());
            lgr.log(Level.SEVERE, e.getMessage(), e);
        }
        return playersData;
    }
    //endregion
    //endregion
}