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
     * @return true if successful, false if not; TODO better behaviour in multiple login
     */
    public PlayerModel add(String login, boolean isNewPlayer) {
        return players.put(login, new PlayerModel(login, isNewPlayer));
    }

    public PlayerModel get(String login) {
        return players.get(login);
    }

}
