package mainPage.playersPool;

import mainPage.playersPool.player.PlayerModel;

public class PlayersPoolController {
    PlayersPoolModel playersPoolModel;

    public PlayersPoolController() {
        playersPoolModel = new PlayersPoolModel();
    }

    public PlayerModel add(String login, boolean isNewPlayer) {
        return playersPoolModel.add(login, isNewPlayer);
    }
}
