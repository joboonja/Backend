package models.data.user;

import config.ProjectServiceConfig;
import config.UserConfig;
import exceptions.DuplicateEndorse;
import exceptions.DuplicateSkill;
import models.data.skill.UserSkill;
import models.data.skill.mapper.UserSkillMapper.UserSkillMapper;
import models.data.user.mapper.UserMapper;
import models.services.project.ProjectService;
import models.services.user.UserService;

import java.sql.SQLException;
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
    private String password;

    public User(String id,
                HashMap <String, UserSkill> skills,
                String firstName,
                String lastName,
                String jobTitle,
                String bio,
                String profilePictureURL,
                String password){
        this.skills = skills;
        initFields(id, firstName, lastName, jobTitle, bio, profilePictureURL, password);
    }
    public User(String id,
                String firstName,
                String lastName,
                String jobTitle,
                String bio,
                String profilePictureURL,
                String password){
        this.skills = new HashMap<>();
        initFields(id, firstName, lastName, jobTitle, bio, profilePictureURL, password);
    }
    private void initFields(String id,
                            String firstName,
                            String lastName,
                            String jobTitle,
                            String bio,
                            String profilePictureURL,
                            String password)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.jobTitle = jobTitle;
        this.bio = bio;
        this.profilePictureURL = profilePictureURL;
        this.password = password;
    }
    public void addSkill(UserSkill skill) throws DuplicateSkill, SQLException {
        if(skills.containsKey(skill.getName()))
            throw new DuplicateSkill();
        skills.put(skill.getName(), skill);
        UserSkillMapper.getInstance().insert(skill);
    }

    public String getPassword() {
        return password;
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

    public void setPassword(String password) {
        this.password = password;
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
    public ArrayList<UserSkill> getSkillsListWithEndorse(String endorserId)
    {
        if(endorserId.equals(id))
            return new ArrayList<>(this.skills.values());
        ArrayList<UserSkill> skills = new ArrayList<>();
        for (UserSkill skill:
             this.skills.values()) {
            skill.setEndorsedOrNot(UserSkillMapper.getInstance().getEndorsedOrNot(
                    endorserId,
                    skill.getUserId(),
                    skill.getName()
            ));
            skills.add(skill);
        }
        return skills;
    }
}
