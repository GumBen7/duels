package mainPage;

import mainPage.playersPool.PlayersPoolController;
import mainPage.playersPool.game.GameController;
import mainPage.playersPool.game.GameModel;
import mainPage.playersPool.player.PlayerModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "MainPageController", urlPatterns = "/main")
public class MainPageController extends HttpServlet {
    private final static String WRONG_PASSWORD_MESSAGE = "Wrong password";
    private final static String VIOLATION_PARAMETER_NAME = "violation";
    private static PlayersPoolController playersPoolController;

    public MainPageController() {
        playersPoolController = new PlayersPoolController();
    }

    //region servlet
    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MainPageModel mainPageModel = new MainPageModel(req.getParameter("login"), req.getParameter("password"), this);
        boolean isThereViolation = mainPageModel.validate();
        if (!isThereViolation) {
            req.setAttribute(VIOLATION_PARAMETER_NAME, WRONG_PASSWORD_MESSAGE);
        }
        req.setAttribute("login", mainPageModel.getLogin());
        String url = determineUrl(isThereViolation);
        forwardResponse(url, req, resp);
    }
    //region utils
    private String determineUrl(boolean violation) {
        if (!violation) {
            return "/";
        } else {
            return "/WEB-INF/views/main.jsp";
        }
    }

    private void forwardResponse(String url, HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher(url).forward(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
    //endregion
    //endregion

    public PlayerModel logInPlayer(String login, boolean isNewPlayer) {
        return playersPoolController.add(login, isNewPlayer);
    }

    public static PlayersPoolController getPlayersPoolController() {
        return playersPoolController;
    }

    public static GameController createGame(String login) {
        System.out.println(login);
        return playersPoolController.createGame(login);
    }
}
