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
import auction.Auction;
import skill.Skill;
import skill.UserSkill;
import user.User;

import java.util.ArrayList;
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
        String id = projectInfo.getString(ProjectConfig.ID);
        String title = projectInfo.getString(ProjectConfig.TITLE);
        String description = projectInfo.getString(ProjectConfig.DESCRIPTION);
        String imageURL = projectInfo.getString(ProjectConfig.IMAGE_URL);
        long budget = projectInfo.getLong(ProjectConfig.BUDGET);
        long deadline = projectInfo.getLong(ProjectConfig.DEADLINE);
        HashMap<String, UserSkill> skills = new HashMap<String, UserSkill>();
        JSONArray skillsInfo;
        skillsInfo = (JSONArray) projectInfo.get(ProjectConfig.SKILLS);

        for(Object skillInfo : skillsInfo){
            UserSkill skill = decodeJSONtoUserSkill((JSONObject)skillInfo);
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

    public static UserSkill decodeJSONtoUserSkill(JSONObject skillInfo){
        String name = skillInfo.getString(SkillsConfig.NAME);
        int points = skillInfo.getInt(SkillsConfig.POINTS);

        return new UserSkill(name, points);
    }

    public static Auction decodeJSONToAuction(JSONObject auctionInfo){
        String projectTitle = auctionInfo.getString(BidConfig.PROJECT_ID);

        return new Auction(projectTitle);
    }

    public static ArrayList<Project> decodeJSONListToProjectList(String info)
    {
        ArrayList<Project> projects = new ArrayList<Project>();
        JSONArray projectsInfo = new JSONArray(info);
        for(int i = 0; i < projectsInfo.length(); i++)
        {
            projects.add(decodeJSONtoProject(projectsInfo.getJSONObject(i)));
        }
        return projects;
    }

    public static Skill decodeJSONtoSkill(JSONObject json)
    {
        return new Skill(json.getString(SkillsConfig.NAME));
    }

    public static ArrayList<Skill> decodeJSONListToSkillList(String info)
    {
        JSONArray skillsInfo = new JSONArray(info);
        ArrayList<Skill> skills = new ArrayList<Skill>();
        for(int i = 0; i < skillsInfo.length(); i++)
        {
            skills.add(decodeJSONtoSkill(skillsInfo.getJSONObject(i)));
        }
        return skills;
    }
}
