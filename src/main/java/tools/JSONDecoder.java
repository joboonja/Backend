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
import skill.UserSkill;
import user.User;

import java.util.HashMap;

public class JSONDecoder {

    public static User decodeJSONtoUser(JSONObject userInfo){
        JSONArray skillsInfo;
        String id = userInfo.getString(UserConfig.ID);
        HashMap<String, UserSkill> skills = new HashMap<String, UserSkill>();
        skillsInfo = (JSONArray) userInfo.get(UserConfig.SKILLS);
        for(Object skillInfo : skillsInfo){
            UserSkill skill = decodeJSONtoUserSkill((JSONObject)skillInfo);
            skills.put(skill.getName(), skill);
        }
        String firstName = userInfo.getString(UserConfig.FIRST_NAME);
        String lastName = userInfo.getString(UserConfig.LAST_NAME);
        String jobTitle = userInfo.getString(UserConfig.JOB_TITLE);
        String bio = userInfo.getString(UserConfig.BIO);
        User user = new User(id, skills, firstName, lastName, jobTitle, bio);
        if(userInfo.getString(UserConfig.PROFILE_URL) != null)
            user.setProfilePictureURL(userInfo.getString(UserConfig.PROFILE_URL));

        return user;
    }

    public static Project decodeJSONtoProject(JSONObject projectInfo){
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

    public static  Bid decodeJSONtoBid(JSONObject bidInfo){
        String biddingUserName = bidInfo.getString(BidConfig.BIDDING_USER);
        String projectTitle = bidInfo.getString(BidConfig.PROJECT_TITLE);
        long offer = bidInfo.getInt(BidConfig.BID_AMOUNT);

        return new Bid(biddingUserName, projectTitle, offer);
    }

    public static UserSkill decodeJSONtoUserSkill(JSONObject skillInfo){
        String name = skillInfo.getString(SkillsConfig.NAME);
        int points = skillInfo.getInt(SkillsConfig.POINTS);

        return new UserSkill(name, points);
    }

    public static Auction decodeJSONToAuction(JSONObject auctionInfo){
        String projectTitle = auctionInfo.getString(BidConfig.PROJECT_TITLE);

        return new Auction(projectTitle);
    }
}
