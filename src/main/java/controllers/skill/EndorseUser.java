package controllers.skill;

import config.JspConfig;
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
        String id = request.getParameter("id");

        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(JspConfig.ENDORSE_USER_VIEW_PATH);
        requestDispatcher.forward(request, response);
    }
}
