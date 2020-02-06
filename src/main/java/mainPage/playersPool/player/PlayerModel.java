package mainPage.playersPool.player;

import databases.Database;
import databases.DatabaseFactory;
import databases.DatabaseType;
import databases.entities.PlayersData;

import java.util.Properties;

public class PlayerModel {
    public static final int NEW_BORN_ATTACK = 10;
    public static final int NEW_BORN_STAMINA = 100;
    public static final int NEW_BORN_RATING = 0;
    private String login;
    private int attack;
    private int stamina;
    private int rating;


    public PlayerModel(String login, boolean isNewPlayer) {
        this.login = login;
        DatabaseFactory databaseFactory = DatabaseFactory.factory(DatabaseType.MYSQL);
        Database database = databaseFactory.getDatabase();
        if (isNewPlayer) {
            attack = NEW_BORN_ATTACK;
            stamina = NEW_BORN_STAMINA;
            rating = NEW_BORN_RATING;
        } else {
            PlayersData playersData = database.getPlayersData(login);
            attack = playersData.getAttack();
            stamina = playersData.getStamina();
            rating = playersData.getRating();
        }
    }
}
