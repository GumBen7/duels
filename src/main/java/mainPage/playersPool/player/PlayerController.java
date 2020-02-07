package mainPage.playersPool.player;

public class PlayerController {
    private PlayerModel playerModel;

    public PlayerController(PlayerModel playerModel) {
        this.playerModel = playerModel;
    }

    public PlayerModel getPlayerModel() {
        return playerModel;
    }

    public void setSpawnHp() {
        playerModel.setSpawnHp();
    }

    public boolean takeDamage(int d) {
        return playerModel.damage(d);
    }

    public boolean isDead() {
        return playerModel.isDead();
    }
}
