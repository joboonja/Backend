package project;

import config.ProjectConfig;
import org.json.JSONObject;
import skill.Skill;

import java.util.ArrayList;

public class Project {
    private String title;
    private int budget;
    private ArrayList<Skill> skills;
    public Project(JSONObject projectInfo)
    {
        title = projectInfo.getString(ProjectConfig.TITLE);
        budget = projectInfo.getInt(ProjectConfig.BUDGET);
        JSONObject skillsInfo[];
        skillsInfo = (JSONObject[]) projectInfo.get(ProjectConfig.SKILLS);
        for (JSONObject aSkillsInfo : skillsInfo) {
            Skill skill = new Skill(aSkillsInfo);
            skills.add(skill);
        }
    }

}
