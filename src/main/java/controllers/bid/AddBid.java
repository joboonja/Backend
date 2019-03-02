package controllers.bid;
import config.BidConfig;
import config.JspConfig;
import config.ProjectServiceConfig;
import models.data.bid.Bid;
import models.data.bid.BidRepo;
import models.data.project.Project;
import models.data.project.ProjectRepo;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/bid")
public class AddBid extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        try {
            long bidAmount = Long.parseLong(request.getParameter("bidAmount"));
            String id = request.getParameter("id");
            Project project = ProjectRepo.getInstance().getProjectByProjectID(id);
            boolean isSuccess = project.checkBudgetSatisfaction(bidAmount);
            if(isSuccess)
                BidRepo.getInstance().addNewBid(new Bid(ProjectServiceConfig.USER_ID, project, bidAmount));
            request.setAttribute("isSuccess", isSuccess);
            request.setAttribute("projectPath", "/project/" + id);
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(JspConfig.ADD_BID_RESULT_VIEW_PATH);
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            request.setAttribute("message", e.getMessage());
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(JspConfig.ERROR_VIEW);
            requestDispatcher.forward(request, response);
        }

    }
}
