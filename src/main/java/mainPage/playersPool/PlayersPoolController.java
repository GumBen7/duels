package mainPage.playersPool;

import mainPage.playersPool.game.GameController;
import mainPage.playersPool.game.GameModel;
import mainPage.playersPool.player.PlayerController;
import mainPage.playersPool.player.PlayerModel;

import java.util.*;

public class PlayersPoolController {
    private PlayersPoolModel playersPoolModel;
    private Map<String, PlayerController> playersControllers;
    private Queue<GameController> gamesControllers;
    private Set<String> pendingPlayers;

    public PlayersPoolController() {
        playersPoolModel = new PlayersPoolModel();
        playersControllers = new HashMap<>();
        gamesControllers = new LinkedList<>();
        pendingPlayers = new HashSet<>();
    }

    public PlayerModel add(String login, boolean isNewPlayer) {
        PlayerModel playerModel = playersPoolModel.add(login, isNewPlayer);
        playersControllers.put(login, new PlayerController(playerModel));
        return playerModel;
    }

    public PlayerModel getPlayer(String login) {
        return playersPoolModel.get(login);
    }

    public GameController createGame(String login) {
        if (!isPlayerPending(login)) {
            pendingPlayers.add(login);
            if (gamesControllers.isEmpty()) {
                GameModel gameModel = new GameModel(playersPoolModel.get(login));
                gamesControllers.add(new GameController(gameModel, playersControllers.get(login)));
                return null;
            } else {
                GameController gameController = gamesControllers.element();
                if (!gameController.isGameReady()) {
                    gameController.setPlayer2IntoGame(playersControllers.get(login));
                    return gameController;
                } else {
                    pendingPlayers.remove(gameController.getClientPlayer(login).getPlayerModel().getLogin());
                    pendingPlayers.remove(gameController.getEnemyPlayer(login).getPlayerModel().getLogin());
                    return gamesControllers.remove();
                }
            }
        } else {
            return null;
        }
    }

    public boolean isPlayerPending(String login) {
        return pendingPlayers.contains(login);
    }

    public GameController getPlayerGame(String login) {
        for (GameController game : gamesControllers) {
            if (game.isPlayer1Game(login)) {
                return game;
            }
        }
        return null;
    }
}
