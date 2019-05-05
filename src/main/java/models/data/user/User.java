package models.data.user;

import config.ProjectServiceConfig;
import config.UserConfig;
import exceptions.DuplicateEndorse;
import exceptions.DuplicateSkill;
import models.data.skill.UserSkill;
import models.services.project.ProjectService;

import java.util.ArrayList;
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
        this.profilePictureURL = id == ProjectServiceConfig.USER_ID ? UserConfig.MAIN_USER_PROFILE_PIC_URL : UserConfig.DEFAULT_PROFILE_PIC_URL;
    }
    public void addSkill(UserSkill skill) throws DuplicateSkill
    {
        if(skills.containsKey(skill.getName()))
            throw new DuplicateSkill();
        skills.put(skill.getName(), skill);
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

    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    public HashMap<String, UserSkill> getSkills()
    {
        return skills;
    }

    public void deleteSkill(String name) {
        skills.remove(name);
    }


    public boolean haveSkill(String skillName)
    {
        for(UserSkill skill : skills.values())
        {
            if(skill.getName().equals(skillName))
                return true;
        }
        return false;
    }
    public ArrayList<UserSkill> getSkillsList()
    {
        return new ArrayList<>(skills.values());
    }
}
