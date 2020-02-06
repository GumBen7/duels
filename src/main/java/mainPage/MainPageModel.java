package mainPage;

import databases.Database;
import databases.DatabaseFactory;
import databases.DatabaseType;

public class MainPageModel {
    private MainPageController controller;
    private String login;
    private String password;

    public MainPageModel(String login, String password, MainPageController controller) {
        this.controller = controller;
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
        boolean isRegistered = database.isRegistered(login);
        if (isRegistered) {
            boolean isSuccessful = database.attemptToAuthorize(login, password);
            if (isSuccessful) {
                controller.logInPlayer(login, false);
            }
            return isSuccessful;
        } else {
            controller.logInPlayer(login, true); //TODO better behaviour in multiple login
            database.signUpNewPlayer(login, password);
            return true;
        }
    }
}
