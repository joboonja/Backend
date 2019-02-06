package user;

import config.UserConfig;
import org.json.JSONArray;
import org.json.JSONObject;
import skill.Skill;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private String username;
    private HashMap<String, Skill> skills = new HashMap<String, Skill>();

    public User(JSONObject userInfo){
        JSONArray skillsInfo;
        username = userInfo.getString(UserConfig.USERNAME);
        skillsInfo = (JSONArray) userInfo.get(UserConfig.SKILLS);
        for(int i = 0; i < skillsInfo.length(); i++){
            Skill skill = new Skill((JSONObject) skillsInfo.get(i));
            skills.put(skill.getName(), skill);
        }
    }
    public String getUsername()
    {
        return username;
    }
    public HashMap<String, Skill> getSkills()
    {
        return skills;
    }
}
