package mainPage.playersPool;

import mainPage.playersPool.player.PlayerModel;

import java.util.HashMap;
import java.util.Map;

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
}
