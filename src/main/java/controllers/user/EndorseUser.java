package controllers.user;

import config.UserConfig;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/endorse")
public class EndorseUser extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String skill = request.getParameter("skill");
        request.setAttribute("skill", skill);
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(UserConfig.ENDORSE_USER_VIEW_PATH);
        requestDispatcher.forward(request, response);
    }
}
