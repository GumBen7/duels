package databases;

import databases.entities.PlayersData;

public abstract class Database {
    //region accounts
    public abstract boolean isRegistered(String login);

    public abstract boolean attemptToAuthorize(String login, String password);

    public abstract void signUpNewPlayer(String login, String password);
    //endregion
    //region players data
    public abstract PlayersData getPlayersData(String login);
    //endregion
}
