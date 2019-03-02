package controllers.skill;

import config.JspConfig;
import models.data.user.User;
import models.data.user.UserRepo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteSkill")
public class DeleteSkill extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String skill = request.getParameter("skill");
        String id = request.getParameter("id");
        try {
            User user = UserRepo.getInstance().getUserById(id);
            user.deleteSkill(skill);
            request.setAttribute("skill", skill);
            request.setAttribute("userPath", "/user/" + id);
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(JspConfig.DELETE_SKILL_VIEW_PATH);
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
