package mainPage.playersPool.player;

import databases.Database;
import databases.DatabaseFactory;
import databases.DatabaseType;
import databases.entities.PlayersData;

public class PlayerModel {
    public static final int NEW_BORN_ATTACK = 10;
    public static final int NEW_BORN_STAMINA = 100;
    public static final int NEW_BORN_RATING = 0;
    private String login;
    private int attack;
    private int stamina;
    private int rating;
    private int hp;

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

    public String getLogin() {
        return login;
    }

    public int getAttack() {
        return attack;
    }

    public int getStamina() {
        return stamina;
    }

    public int getRating() {
        return rating;
    }

    public void setSpawnHp() {
        this.hp = this.stamina;
    }

    public boolean damage(int d) {
        hp -= d;
        return hp > 0;
    }

    public boolean isDead() {
        return hp <= 0;
    }


}
