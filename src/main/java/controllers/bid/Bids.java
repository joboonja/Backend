package controllers.bid;

import config.ProjectServiceConfig;
import models.data.bid.Bid;
import models.data.bid.BidRepo;
import models.data.project.Project;
import models.data.project.ProjectRepo;
import org.springframework.web.bind.annotation.*;

@RestController
public class Bids {
    @RequestMapping(value = "/bids", method = RequestMethod.POST)
    public Bid addBid(@RequestParam("projectID") String projectID, @RequestParam("bidAmount") long bidAmount) {
        try {
            Project project = ProjectRepo.getInstance().getProjectByProjectID(projectID);
            Bid newBid = new Bid(ProjectServiceConfig.USER_ID, project, bidAmount);
            BidRepo.getInstance().addNewBid(newBid);
            return newBid;

        } catch (Exception e) {
            throw e;
        }
    }
}
