package mainPage;

import databases.Database;
import databases.DatabaseFactory;
import databases.DatabaseType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "MainPageController", urlPatterns = "/main")
public class MainPageController extends HttpServlet {
    private final static String WRONG_PASSWORD_MESSAGE = "Wrong password";
    private final static String VIOLATION_ATTRIBUTE_NAME = "violation";
    private MainPageModel mainPageModel;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        RequestModel requestModel = RequestModel.fromRequestParameters(req);
//        requestModel.setAsRequestAttributes(req);
        mainPageModel = new MainPageModel(req.getParameter("login"), req.getParameter("password"));
        boolean isThereViolation = mainPageModel.validate();
        if (!isThereViolation) {
            req.setAttribute(VIOLATION_ATTRIBUTE_NAME, WRONG_PASSWORD_MESSAGE);
        } else {
        }
        String url = determineUrl(isThereViolation);
        forwardResponse(url, req, resp);
    }

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

    /*private class RequestModel {
        private final String login;
        private final String password;

        private RequestModel(String login, String password) {
            this.login = login;
            this.password = password;
        }

//        public static RequestModel fromRequestParameters(HttpServletRequest req) {
//            return new RequestModel(req.getParameter("login"), req.getParameter("password"));
//        }

        public void setAsRequestAttributes(HttpServletRequest req) {
            req.setAttribute("login", login);
            req.setAttribute("password", password);
        }


    }*/
}
