package controllers.bid;
import config.BidConfig;
import models.data.bid.Bid;
import models.data.bid.BidRepo;
import models.data.project.Project;
import models.data.project.ProjectRepo;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = "/bid")
public class AddBid extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        try {
            long bidAmount = Long.parseLong(request.getParameter("bidAmount"));
            Project project = ProjectRepo.getInstance().getProjectByProjectID(request.getParameter("projectID"));
            boolean isSuccess = project.checkBudgetSatisfaction(bidAmount);
            if(isSuccess)
                BidRepo.getInstance().addNewBid(new Bid("1", project, bidAmount));
            request.setAttribute("isSuccess", isSuccess);
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(BidConfig.ADD_BID_RESULT_VIEW_PATH);
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
