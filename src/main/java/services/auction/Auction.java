package services.auction;

import bid.Bid;
import bid.BidRepo;
import config.AuctionConfig;
import project.Project;
import project.ProjectRepo;
import skill.Skill;
import user.User;
import user.UserRepo;

import java.util.HashMap;

public class Auction {

    private String projectTitle;
    public Auction(String projectTitle)
    {
        this.projectTitle = projectTitle;
    }

    public void start()
    {
        User user;
        Project project;
        int maxAuctionRate = 0;
        boolean firstVisited = false;
        User winner = null;
        BidRepo bidRepo = BidRepo.getInstance();
        UserRepo userRepo = UserRepo.getInstance();
        ProjectRepo projectRepo = ProjectRepo.getInstance();

        try {
            project = projectRepo.getProjectByProjectTitle(projectTitle);

            for(Bid bid : bidRepo.getBidsOfProject(projectTitle)) {

                user = userRepo.getUserById(bid.getBiddingUserName());
                int auctionRate = calcAuctionFormula(user, project, bid);
                if (!firstVisited) {
                    maxAuctionRate = auctionRate;
                    firstVisited = true;
                    winner = user;
                } else if (auctionRate > maxAuctionRate) {
                    maxAuctionRate = auctionRate;
                    winner = user;
                }
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println(AuctionConfig.WINNER_MSG(winner));
    }

    private int calcAuctionFormula(User user, Project project, Bid bid)
    {
        HashMap<String, Skill> userSkills = user.getSkills();
        HashMap<String, Skill> projectSkills = project.getSkills();

        int auctionRate = 0;

        for(Skill projectSkill : projectSkills.values())
        {
            Skill userSkill = userSkills.get(projectSkill.getName());
            auctionRate += 10000 *  Math.pow(userSkill.getPoints() - projectSkill.getPoints(), 2);
        }
        auctionRate += (project.getBudget() - bid.getOffer());
        return auctionRate;
    }
}
