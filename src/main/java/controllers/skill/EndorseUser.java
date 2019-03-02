package controllers.skill;

import config.JspConfig;
import config.UserConfig;
import models.services.skill.EndorseService;

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
        try {
            EndorseService.endorseUserSkill(id, skill);
            request.setAttribute("skill", skill);
            request.setAttribute("userPath", "/user/" + id);
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(JspConfig.ENDORSE_USER_VIEW_PATH);
            requestDispatcher.forward(request, response);
        }
        catch (Exception e)
        {
            request.setAttribute("message", e.getMessage());
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(JspConfig.ERROR_VIEW);
            requestDispatcher.forward(request, response);
        }

    }
}
