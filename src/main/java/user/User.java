package user;

import config.Commands;
import config.UserConfig;
import org.json.JSONArray;
import org.json.JSONObject;
import skill.Skill;

import java.util.ArrayList;

public class User {
    private String username;
    private ArrayList <Skill> skills = new ArrayList<Skill>();

    public User(JSONObject userInfo){
        JSONArray skillsInfo;
        username = userInfo.getString(UserConfig.USERNAME);
        skillsInfo = (JSONArray) userInfo.get(UserConfig.SKILLS);
        for(int i = 0; i < skillsInfo.length(); i++){
            Skill skill = new Skill((JSONObject) skillsInfo.get(i));
            skills.add(skill);
        }
    }
}
