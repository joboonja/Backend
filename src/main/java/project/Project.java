package project;

import config.ProjectConfig;
import org.json.JSONArray;
import org.json.JSONObject;
import skill.Skill;
import user.User;

import java.util.ArrayList;
import java.util.HashMap;

public class Project {
    private String id;
    private String title;
    private String description;
    private String imageURL;
    private long budget;
    private HashMap<String, Skill> skills;
    private long deadline;
//    private List <Bid> bids;
//    private User winner;

    public Project(String _title, long _budget, HashMap <String, Skill> _skills)
    {
        title = _title;
        budget = _budget;
        skills = _skills;
    }

    public String getTitle()
    {
        return title;
    }

    public HashMap<String, Skill> getSkills() { return skills; }

    public long getBudget()
    {
        return budget;
    }

    public boolean checkSatisfaction(HashMap<String, Skill> userSkills, long bidAmount)
    {
        if(userSkills.size() < skills.size())
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
}
