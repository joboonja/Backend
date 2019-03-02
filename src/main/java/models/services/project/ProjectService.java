package models.services.project;

import models.data.bid.Bid;
import models.data.bid.BidRepo;
import models.data.project.Project;
import models.data.project.ProjectRepo;
import models.data.user.User;
import models.data.user.UserRepo;

import java.util.ArrayList;

public class ProjectService {
    public static Project getProjectByID(String id) throws Exception {
        return ProjectRepo.getInstance().getProjectByProjectID(id);
    }

    public static boolean hasSkillsForProject(String project_id, String user_id) throws Exception {
        Project project = ProjectRepo.getInstance().getProjectByProjectID(project_id);
        User user = UserRepo.getInstance().getUserById(user_id);
        return project.checkSkillSatisfaction(user.getSkills());
    }

    public static boolean hasBidOnProject(String project_id, String user_id) throws Exception {
        ArrayList<Bid> bids = BidRepo.getInstance().getBidsOfProject(project_id);
        for(Bid bid : bids) {
            if(bid.getBiddingUserName().equals(user_id))
                return true;
        }
        return false;
    }
}
