package controllers.user;


import config.JspConfig;
import config.ProjectConfig;
import config.ProjectServiceConfig;
import config.UserConfig;
import models.data.project.Project;
import models.data.project.ProjectRepo;
import models.services.project.ProjectService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet(value = "/projectList")
public class ProjectList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException{
        response.setContentType("text/html; charset=UTF-8");
        try {
            ArrayList<Project> projects = ProjectRepo.getInstance().getProjectsForUser(ProjectServiceConfig.USER_ID);
            request.setAttribute("projectList", projects);

            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(JspConfig.PROJECT_LIST_VIEW_PATH);
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            request.setAttribute("message", e.getMessage());
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(JspConfig.ERROR_VIEW);
            requestDispatcher.forward(request, response);
        }
    }
}
