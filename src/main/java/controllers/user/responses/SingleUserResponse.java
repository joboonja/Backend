package controllers.user.responses;

import models.data.skill.UserSkill;
import models.data.user.User;

import java.util.ArrayList;

public class SingleUserResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private String profilePictureURL;
    private ArrayList<UserSkill> skillsList;
    private String bio;

    public SingleUserResponse(User user, String loginUser) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.jobTitle = user.getJobTitle();
        this.profilePictureURL = user.getProfilePictureURL();
        this.skillsList =  user.getSkillsListWithEndorse(loginUser);
        this.bio = user.getBio();
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    public void setProfilePictureURL(String profilePictureUrl) {
        this.profilePictureURL = profilePictureUrl;
    }

    public ArrayList<UserSkill> getSkillsList() {
        return skillsList;
    }

    public void setSkillsList(ArrayList<UserSkill> skillsList) {
        this.skillsList = skillsList;
    }
}
