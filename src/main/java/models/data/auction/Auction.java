package models.data.auction;

import models.data.bid.Bid;
import models.data.bid.mapper.BidMapper;
import config.AuctionConfig;
import models.data.project.Project;
import models.data.project.mapper.ProjectMapper;
import models.data.skill.UserSkill;
import models.data.user.User;
import models.data.user.mapper.UserMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Auction implements Runnable{

    @Override
    public void run() {
        ProjectMapper projectMapper = ProjectMapper.getInstance();
        ArrayList<Project> waitingProjects;
        try {
            waitingProjects = projectMapper.getPassedDeadlineProjects();
            for(Project project: waitingProjects) {
                start(project.getID());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void start(String projectId)
    {
        User user;
        Project project;
        int maxAuctionRate = 0;
        boolean firstVisited = false;
        User winner = null;
        BidMapper bidMapper = BidMapper.getInstance();
        ProjectMapper projectMapper = ProjectMapper.getInstance();
        UserMapper userMapper = UserMapper.getInstance();

        try {
            project = projectMapper.getProjectByProjectID(projectId);
            ArrayList <Bid> bidsOfProject = bidMapper.getAllBidsOfProject(projectId);

            for(Bid bid: bidsOfProject) {
                user = userMapper.getUserById(bid.getBiddingUserName());
                int auctionRate = calcAuctionFormula(user, project, bid);
                if (!firstVisited) {
                    maxAuctionRate = auctionRate;
                    firstVisited = true;
                    winner = user;
                } else if (auctionRate > maxAuctionRate) {
                    maxAuctionRate = auctionRate;
                    winner = user;
                }
                projectMapper.setWinner(winner.getId(), projectId);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int calcAuctionFormula(User user, Project project, Bid bid)
    {
        HashMap<String, UserSkill> userSkills = user.getSkills();
        HashMap<String, UserSkill> projectSkills = project.getSkills();

        int auctionRate = 0;

        for(UserSkill projectSkill : projectSkills.values())
        {
            UserSkill userSkill = userSkills.get(projectSkill.getName());
            auctionRate += 10000 *  Math.pow(userSkill.getPoints() - projectSkill.getPoints(), 2);
        }
        auctionRate += (project.getBudget() - bid.getOffer());
        return auctionRate;
    }
}
