package project;

import config.ProjectConfig;
import org.json.JSONArray;
import org.json.JSONObject;
import skill.Skill;

import java.util.ArrayList;

public class Project {
    private String title;
    private int budget;
    private ArrayList<Skill> skills = new ArrayList<Skill>();
    public Project(JSONObject projectInfo)
    {
        title = projectInfo.getString(ProjectConfig.TITLE);
        budget = projectInfo.getInt(ProjectConfig.BUDGET);
        JSONArray skillsInfo;
        skillsInfo = (JSONArray) projectInfo.get(ProjectConfig.SKILLS);

        for(int i = 0; i < skillsInfo.length(); i++){
            Skill skill = new Skill((JSONObject) skillsInfo.get(i));
            skills.add(skill);
        }
    }

}
