package user;

import skill.Skill;
import java.util.HashMap;

public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private String profilePictureURL;
    private String bio;
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
