package controllers.skill;

import config.JspConfig;
import config.ProjectServiceConfig;
import models.services.skill.SkillService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/addSkill")
public class AddSkill extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String skillName = request.getParameter("skill");
        try
        {
            SkillService.addSkill(skillName);
            request.setAttribute("skill", skillName);
            request.setAttribute("userPath", "/user/" + ProjectServiceConfig.USER_ID);
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(JspConfig.ADD_SKILL_VIEW_PATH);
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
