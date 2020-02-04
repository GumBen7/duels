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

    //region getters/setters
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
    //endregion

    /**
     * checks authorization info in db
     * creates new player if such doesn't exist
     * @return false if there is any violation, true if not
     */
    public boolean validate() {
        DatabaseFactory databaseFactory = DatabaseFactory.factory(DatabaseType.MYSQL);
        Database database = databaseFactory.getDatabase();
        boolean isRegistered = database.isRegistered(this);
        if (isRegistered) {
            return database.attemptToAuthorize(this);
        } else {
            database.signUpNewPlayer(this);
            return true;
        }
    }
}
