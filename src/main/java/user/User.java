package user;

import config.UserConfig;
import skill.Skill;
import skill.UserSkill;

import java.util.HashMap;

public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private String profilePictureURL;
    private String bio;
    private HashMap<String, UserSkill> skills;

    public User(String id,
                HashMap <String, UserSkill> skills,
                String firstName,
                String lastName,
                String jobTitle,
                String bio){
        this.id = id;
        this.skills = skills;
        this.firstName = firstName;
        this.lastName = lastName;
        this.jobTitle = jobTitle;
        this.bio = bio;
        this.profilePictureURL = UserConfig.DEFAULT_PROFILE_PIC_URL;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return  lastName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getBio() {
        return bio;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    public HashMap<String, UserSkill> getSkills()
    {
        return skills;
    }
}
