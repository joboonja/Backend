package controllers.project;
import config.ProjectConfig;
import models.data.project.Project;
import models.services.project.ProjectService;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = "/project/*")
public class ProjectInfo extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        try {
            String projectID = tools.HttpTokenizer.getID(request);
            Project project = ProjectService.getProjectByID(projectID);
            boolean canBid = ProjectService.hasSkillsForProject(projectID, "1") &&
                    !ProjectService.hasBidOnProject(projectID, "1");
            request.setAttribute("project", project);

            request.setAttribute("canBid", canBid);
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(ProjectConfig.SINGLE_PROJECT_VIEW_PATH);
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
