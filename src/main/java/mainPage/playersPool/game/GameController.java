package mainPage.playersPool.game;

import mainPage.playersPool.player.PlayerController;

import java.util.Timer;
import java.util.TimerTask;

public class GameController {
    private GameModel gameModel;
    private PlayerController player1;
    private PlayerController player2;
    private PlayerController winner;

    public GameController(GameModel gameModel, PlayerController player1) {
        this.gameModel = gameModel;
        this.player1 = player1;
        winner = null;
    }

    public boolean isGameReady() {
        return gameModel.isReady();
    }

    public void setPlayer2IntoGame(PlayerController player2) {
        this.player2 = player2;
        gameModel.setPlayer2(player2.getPlayerModel());
        gameModel.setReadiness(true);
    }

    public PlayerController getClientPlayer(String clientLogin) {
        if (player1.getPlayerModel().getLogin().equals(clientLogin)) {
            return player1;
        }
        return player2;
    }

    public PlayerController getEnemyPlayer(String clientLogin) {
        if (player2.getPlayerModel().getLogin().equals(clientLogin)) {
            return player2;
        }
        return player1;
    }

    public PlayerController duel() { //TODO rating refreshing logic !
        player1.setSpawnHp();
        player2.setSpawnHp();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                while (winner == null) {
                    if (player1.isDead()) {
                        winner = player2;
                    }
                    if (player2.isDead()) {
                        winner = player1;
                    }
                }
            }
        };
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(task, 0, 1*1000);
        return winner;
    }
}
