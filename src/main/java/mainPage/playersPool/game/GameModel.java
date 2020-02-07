package mainPage.playersPool.game;

import mainPage.playersPool.player.PlayerModel;

public class GameModel {
    private PlayerModel player1;

    private PlayerModel player2;
    private boolean isReady;

    public GameModel(PlayerModel player1) {
        this.player1 = player1;
        this.isReady = false;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setPlayer2(PlayerModel player2) {
        this.player2 = player2;
    }

    public void setReadiness(boolean isReady) {
        this.isReady = isReady;
    }
}
