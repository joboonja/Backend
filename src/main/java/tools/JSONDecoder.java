package tools;

import models.data.bid.Bid;
import config.BidConfig;
import config.ProjectConfig;
import config.SkillsConfig;
import config.UserConfig;
import org.json.JSONArray;
import org.json.JSONObject;
import models.data.project.Project;
import models.data.project.mapper.ProjectMapper;
import models.data.auction.Auction;
import models.data.skill.Skill;
import models.data.skill.UserSkill;
import models.data.user.User;

import java.util.ArrayList;
import java.util.HashMap;

public class JSONDecoder {

    public static Project decodeJSONtoProject(JSONObject projectInfo){
        String id = projectInfo.getString(ProjectConfig.ID);
        String title = projectInfo.getString(ProjectConfig.TITLE);
        String description = projectInfo.getString(ProjectConfig.DESCRIPTION);
        String imageURL = projectInfo.getString(ProjectConfig.IMAGE_URL);
        long budget = projectInfo.getLong(ProjectConfig.BUDGET);
        long deadline = projectInfo.getLong(ProjectConfig.DEADLINE);
        long creationDate = projectInfo.getLong(ProjectConfig.CREATION_DATE);
        HashMap<String, UserSkill> skills = new HashMap<String, UserSkill>();
        JSONArray skillsInfo;
        skillsInfo = (JSONArray) projectInfo.get(ProjectConfig.SKILLS);

        for(Object skillInfo : skillsInfo){
            UserSkill skill = decodeJSONtoUserSkill((JSONObject)skillInfo);
            skills.put(skill.getName(), skill);
        }

        return new Project(id, title, description, imageURL, budget, skills, deadline, creationDate);
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
