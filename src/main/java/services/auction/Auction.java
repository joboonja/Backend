package services.auction;

import bid.Bid;
import config.BidConfig;
import config.JoboonjaConfig;
import org.json.JSONObject;
import project.Project;
import skill.Skill;
import user.User;

import java.util.HashMap;

public class Auction {
    public static void auction(JSONObject projectIdentifier)
    {
        String projectTitle = projectIdentifier.getString(BidConfig.PROJECT_TITLE);
        User user;
        Project project;
        int maxAuctionRate = 0;
        boolean firstVisited = false;
        User winner = null;

        try {
            project = getProjectByProjectTitle(projectTitle);

            if(bids.size() == 0)
                throw new Exception(JoboonjaConfig.NO_BID_TO_AUCTION_ERROR);

            for(Bid bid : bids) {
                if (!bid.getProjectTitle().equals(projectTitle))
                    continue;
                user = getUserByUsername(bid.getBiddingUserName());
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
        System.out.println(JoboonjaConfig.WINNER_MSG(winner));
    }
    private static int calcAuctionFormula(User user, Project project, Bid bid)
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
