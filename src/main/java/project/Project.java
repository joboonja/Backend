package project;

import skill.Skill;
import user.User;

import java.util.ArrayList;
import skill.UserSkill;

import java.util.HashMap;

public class Project {
    private String id;
    private String title;
    private String description;
    private String imageURL;
    private long budget;
    private HashMap<String, UserSkill> skills;
    private long deadline;
//    private List <Bid> bids;
//    private User winner;

    public Project(String id, String title, String description, String imageURL, long budget, HashMap <String, Skill> skills, long deadline)
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageURL = imageURL;
        this.budget = budget;
        this.skills = skills;
        this.deadline = deadline;
    }

    public String getTitle()
    {
        return title;
    }

    public String getID() { return id; }

    public HashMap<String, Skill> getSkills() { return skills; }

    public long getBudget()
    {
        return budget;
    }

    public boolean checkSatisfaction(HashMap<String, UserSkill> userSkills, long bidAmount)
    {
        if(userSkills.size() < skills.size())
            return false;

        for (UserSkill skill : skills.values()) {
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
