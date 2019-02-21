package tools;

import bid.Bid;
import config.BidConfig;
import config.ProjectConfig;
import config.SkillsConfig;
import config.UserConfig;
import org.json.JSONArray;
import org.json.JSONObject;
import project.Project;
import services.auction.Auction;
import skill.Skill;
import user.User;

import java.util.HashMap;

public class JSONDecoder {

    public User decodeJSONtoUser(JSONObject userInfo){
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

    public Project decodeJSONtoProject(JSONObject projectInfo){
        String title = projectInfo.getString(ProjectConfig.TITLE);
        long budget = projectInfo.getInt(ProjectConfig.BUDGET);
        HashMap<String, Skill> skills = new HashMap<String, Skill>();
        JSONArray skillsInfo;
        skillsInfo = (JSONArray) projectInfo.get(ProjectConfig.SKILLS);

        for(Object skillInfo : skillsInfo){
            Skill skill = decodeJSONtoSkill((JSONObject)skillInfo);
            skills.put(skill.getName(), skill);
        }

        return new Project(title, budget, skills);
    }

    public Bid decodeJSONtoBid(JSONObject bidInfo){
        String biddingUserName = bidInfo.getString(BidConfig.BIDDING_USER);
        String projectTitle = bidInfo.getString(BidConfig.PROJECT_TITLE);
        long offer = bidInfo.getInt(BidConfig.BID_AMOUNT);

        return new Bid(biddingUserName, projectTitle, offer);
    }

    public Skill decodeJSONtoSkill(JSONObject skillInfo){
        String name = skillInfo.getString(SkillsConfig.NAME);
        int points = skillInfo.getInt(SkillsConfig.POINTS);

        return new Skill(name, points);
    }

    public Auction decodeJSONToAuction(JSONObject auctionInfo){
        String projectTitle = auctionInfo.getString(BidConfig.PROJECT_TITLE);

        return new Auction(projectTitle);
    }
}
