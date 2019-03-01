package controllers.project;
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
            Project project = ProjectService.getProjectByID(tools.HttpTokenizer.getID(request));
            request.setAttribute("project", project);
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/views/project/projecInfo.jsp");
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
