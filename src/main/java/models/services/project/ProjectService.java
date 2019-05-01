package models.services.project;

import exceptions.ProjectNotFound;
import models.data.bid.Bid;
import models.data.bid.mapper.BidMapper;
import models.data.project.Project;
import models.data.project.mapper.ProjectMapper;
import models.data.user.User;
import models.data.user.mapper.UserMapper;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProjectService {
    public static Project getProjectByID(String id) throws ProjectNotFound{
        return ProjectMapper.getInstance().getProjectByProjectID(id);
    }

    public static boolean hasSkillsForProject(String project_id, String user_id) throws Exception {

        Project project = ProjectMapper.getInstance().getProjectByProjectID(project_id);
        User user = UserMapper.getInstance().getUserById(user_id);


        return project.checkSkillSatisfaction(user.getSkills());
    }

    public static boolean hasBidOnProject(String project_id, String user_id) throws Exception {
        ArrayList<Bid> bids = BidMapper.getInstance().getBidsOfProject(project_id);
        for(Bid bid : bids) {
            if(bid.getBiddingUserName().equals(user_id))
                return true;
        }
        return false;
    }
}
