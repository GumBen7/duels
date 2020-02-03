package mainPage;

import databases.Database;
import databases.DatabaseFactory;
import databases.DatabaseType;

public class MainPageModel {
    private String login;
    private String password;

    public MainPageModel(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * checks authorization info in db
     * @return false if there is any violation, true if not
     */
    public boolean validate() {
        DatabaseFactory databaseFactory = DatabaseFactory.factory(DatabaseType.MYSQL);
        Database database = databaseFactory.getDatabase();
        boolean result = true;
        boolean isRegistered = database.isRegistered(this);
        if (isRegistered) {
            result = database.attemptToAuthorize(this);
        } else {
            database.signUpNewPlayer(this);
        }
        return result;
    }
}
