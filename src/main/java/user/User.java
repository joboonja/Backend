package user;

import config.UserConfig;
import org.json.JSONArray;
import org.json.JSONObject;
import skill.Skill;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private String username;
    private HashMap<String, Skill> skills;

    public User(String _username, HashMap <String, Skill> _skills){
        username = _username;
        skills = _skills;
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
