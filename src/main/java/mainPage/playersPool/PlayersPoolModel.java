package mainPage.playersPool;

import mainPage.playersPool.game.GameModel;
import mainPage.playersPool.player.PlayerModel;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class PlayersPoolModel {
    private Map<String, PlayerModel> players;

    public PlayersPoolModel() {
        players = new HashMap<>();
    }

    /** attempts to add new online player
     *
     * @param login - player's unique login
     * @return new model if successful, null if not; TODO better behaviour in multiple login
     */
    public PlayerModel add(String login, boolean isNewPlayer) {
        players.put(login, new PlayerModel(login, isNewPlayer));
        return players.get(login);
    }

    public PlayerModel get(String login) {
        return players.get(login);
    }

}
