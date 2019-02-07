package project;

import config.ProjectConfig;
import org.json.JSONArray;
import org.json.JSONObject;
import skill.Skill;

import java.util.ArrayList;
import java.util.HashMap;

public class Project {
    private String title;
    private long budget;
    private HashMap<String, Skill> skills = new HashMap<String, Skill>();

    public Project(JSONObject projectInfo)
    {
        title = projectInfo.getString(ProjectConfig.TITLE);
        budget = projectInfo.getInt(ProjectConfig.BUDGET);
        JSONArray skillsInfo;
        skillsInfo = (JSONArray) projectInfo.get(ProjectConfig.SKILLS);

        for(int i = 0; i < skillsInfo.length(); i++){
            Skill skill = new Skill((JSONObject) skillsInfo.get(i));
            skills.put(skill.getName(), skill);
        }
    }

    public String getTitle()
    {
        return title;
    }

    public boolean checkSatisfaction(HashMap<String, Skill> userSkills, long bidAmount)
    {
        if(userSkills.size() != skills.size())
            return false;

        for (Skill skill : skills.values()) {
            String skillName = skill.getName();
            if(!userSkills.containsKey(skillName))
                return false;
            if(userSkills.get(skillName).getPoints() < skill.getPoints())
                return false;
        }

        if(budget < bidAmount)
            return false;

        return true;
    }

    public HashMap<String, Skill> getSkills() { return skills; }

    public long getBudget()
    {
        return budget;
    }

}
