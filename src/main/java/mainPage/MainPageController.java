package mainPage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "MainPageController", urlPatterns = "/main")
public class MainPageController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestPageModel pageModel = RequestPageModel.fromRequestParameters(req);
        pageModel.setAsRequestAttributes(req);
        if (!pageModel.validate()) {
            req.setAttribute("violation", "Wrong password");
        }
        String url = determineUrl(pageModel.validate());
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

    private static class RequestPageModel {

        private final String login;
        private final String password;

        private RequestPageModel(String login, String password) {
            this.login = login;
            this.password = password;
        }

        public static RequestPageModel fromRequestParameters(HttpServletRequest req) {
            return new RequestPageModel(req.getParameter("login"), req.getParameter("password"));
        }

        public void setAsRequestAttributes(HttpServletRequest req) {
            req.setAttribute("login", login);
            req.setAttribute("password", password);
        }

        /**
         * checks valuations
         * @return false if there is any violation, true if not
         */
        public boolean validate() {
            return true;
        }
    }
}
