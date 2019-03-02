package controllers.project;
import config.JspConfig;
import config.ProjectConfig;
import config.ProjectServiceConfig;
import models.data.project.Project;
import models.services.project.ProjectService;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/project/*")
public class ProjectInfo extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        try {
            String projectID = tools.HttpTokenizer.getID(request);
            Project project = ProjectService.getProjectByID(projectID);
            boolean canBid = ProjectService.hasSkillsForProject(projectID, ProjectServiceConfig.USER_ID) &&
                    !ProjectService.hasBidOnProject(projectID, ProjectServiceConfig.USER_ID);
            request.setAttribute("project", project);
            request.setAttribute("canBid", canBid);
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(JspConfig.SINGLE_PROJECT_VIEW_PATH);
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            request.setAttribute("message", e.getMessage());
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(JspConfig.ERROR_VIEW);
            requestDispatcher.forward(request, response);
        }
    }
}
