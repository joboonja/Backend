package models.data.skill;

import config.ProjectConfig;
import config.ProjectServiceConfig;
import exceptions.DuplicateEndorse;
import exceptions.DuplicateSkill;
import models.data.project.Project;
import models.services.project.ProjectService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class UserSkill extends Skill {
    private int points;
    private String userId;
    private ArrayList<String> peopleWhoEndrosed;
    public UserSkill(String name, int points) {
        super(name);
        this.points = points;
        peopleWhoEndrosed = new ArrayList<>();
    }
    public UserSkill(String name, int points, String userId) {
        super(name);
        this.points = points;
        this.userId = userId;
        peopleWhoEndrosed = new ArrayList<>();
    }
    public int getPoints()
    {
        return points;
    }
    public void setUserId(String userId) { this.userId = userId; }
    public String getUserId() { return userId; }
    public boolean canEndorse(String userID)
    {
        return !peopleWhoEndrosed.contains(userID);
    }
    public void endorse(String userID) throws DuplicateEndorse
    {
        if(canEndorse(userID)){
            peopleWhoEndrosed.add(userID);
            points ++;
        }
        else
            throw new DuplicateEndorse();
    }

    public boolean getEndorsedOrNot() {
        for( String person : peopleWhoEndrosed)
        {
            if(person.equals(ProjectServiceConfig.USER_ID))
                return true;
        }
        return false;
    }

    public static HashMap<String, UserSkill> convertToNameAndSkill(ArrayList <UserSkill> allUserSkills) {
        HashMap<String, UserSkill> skills = new HashMap<>();
        for(UserSkill skill : allUserSkills) {
            skills.put(skill.getName(), skill);
        }
        return skills;
    }
}
