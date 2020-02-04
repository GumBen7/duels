package databases;

import mainPage.MainPageModel;

public abstract class Database {
    public abstract boolean isRegistered(MainPageModel mainPageModel);

    public abstract boolean attemptToAuthorize(MainPageModel mainPageModel);

    public abstract void signUpNewPlayer(MainPageModel mainPageModel);
}
