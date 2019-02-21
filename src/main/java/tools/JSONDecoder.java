package tools;

import bid.Bid;
import config.BidConfig;
import config.ProjectConfig;
import config.SkillsConfig;
import config.UserConfig;
import org.json.JSONArray;
import org.json.JSONObject;
import project.Project;
import project.ProjectRepo;
import services.auction.Auction;
import skill.Skill;
import user.User;

import java.util.HashMap;

public class JSONDecoder {

    public static User decodeJSONtoUser(JSONObject userInfo){
        JSONArray skillsInfo;
        String username = userInfo.getString(UserConfig.USERNAME);
        HashMap<String, Skill> skills = new HashMap<String, Skill>();
        skillsInfo = (JSONArray) userInfo.get(UserConfig.SKILLS);
        for(Object skillInfo : skillsInfo){
            Skill skill = decodeJSONtoSkill((JSONObject)skillInfo);
            skills.put(skill.getName(), skill);
        }

        return new User(username, skills);
    }

    public static Project decodeJSONtoProject(JSONObject projectInfo){
        String id = projectInfo.getString(ProjectConfig.ID);
        String title = projectInfo.getString(ProjectConfig.TITLE);
        String description = projectInfo.getString(ProjectConfig.DESCRIPTION);
        String imageURL = projectInfo.getString(ProjectConfig.IMAGE_URL);
        long budget = projectInfo.getLong(ProjectConfig.BUDGET);
        long deadline = projectInfo.getLong(ProjectConfig.DEADLINE);
        HashMap<String, Skill> skills = new HashMap<String, Skill>();
        JSONArray skillsInfo;
        skillsInfo = (JSONArray) projectInfo.get(ProjectConfig.SKILLS);

        for(Object skillInfo : skillsInfo){
            Skill skill = decodeJSONtoSkill((JSONObject)skillInfo);
            skills.put(skill.getName(), skill);
        }

        return new Project(id, title, description, imageURL, budget, skills, deadline);
    }

    public static Bid decodeJSONtoBid(JSONObject bidInfo) throws Exception{
        String biddingUserName = bidInfo.getString(BidConfig.BIDDING_USER);
        String projectID = bidInfo.getString(BidConfig.PROJECT_ID);
        long offer = bidInfo.getInt(BidConfig.BID_AMOUNT);

        return new Bid(biddingUserName, ProjectRepo.getInstance().getProjectByProjectID(projectID), offer);
    }

    public static Skill decodeJSONtoSkill(JSONObject skillInfo){
        String name = skillInfo.getString(SkillsConfig.NAME);
        int points = skillInfo.getInt(SkillsConfig.POINTS);

        return new Skill(name, points);
    }

    public static Auction decodeJSONToAuction(JSONObject auctionInfo){
        String projectTitle = auctionInfo.getString(BidConfig.PROJECT_ID);

        return new Auction(projectTitle);
    }
}
