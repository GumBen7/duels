package mainPage.duelPage;

import mainPage.MainPageController;
import mainPage.playersPool.game.GameController;
import mainPage.playersPool.player.PlayerController;
import mainPage.playersPool.player.PlayerModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DuelPageController", urlPatterns = "/duel")
public class DuelPageController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url ="/WEB-INF/views/duel.jsp";
        String login = req.getParameter("login");
        String game = req.getParameter("game");
        req.setAttribute("login", login);
        PlayerModel playerModel = MainPageController.getPlayersPoolController().getPlayer(login);
        if (game == null) {
            req.setAttribute("rating", playerModel.getRating());
        } else {
            GameController gameController = MainPageController.createGame(login);
            if (gameController != null) { //TODO time out !!!!!!!
                gameController.duel();
                req.setAttribute("duel", "duel");
                PlayerController client = gameController.getClientPlayer(login); //TODO show battle!!!! BATTLE MECHANICS  !!!!!!!
                PlayerController enemy = gameController.getEnemyPlayer(login);
            } else {
                req.setAttribute("pending", "pending");
            }
        }
        forwardResponse(url, req, resp);
    }

    private void forwardResponse(String url, HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher(url).forward(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
