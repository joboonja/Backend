package user;

import config.Commands;
import config.UserConfig;
import org.json.JSONObject;
import skill.Skill;

import java.util.ArrayList;

public class User {
    private String username;
    private ArrayList <Skill> skills;

    public User(JSONObject userInfo){
        JSONObject skillsInfo[];
        username = userInfo.getString(UserConfig.USERNAME);
        skillsInfo = (JSONObject[])userInfo.get(UserConfig.SKILLS);
        for(int i = 0; i < skillsInfo.length; i++){
            Skill skill = new Skill(skillsInfo[i]);
            skills.add(skill);
        }
    }
}
