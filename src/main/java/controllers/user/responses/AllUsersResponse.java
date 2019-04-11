package controllers.user.responses;


import models.data.user.User;

public class AllUsersResponse {
    private String firstName;
    private String lastName;
    private String id;
    private String jobTitle;
    private String profilePictureUrl;
    public AllUsersResponse(User user)
    {
        firstName = user.getFirstName();
        lastName = user.getLastName();
        id = user.getId();
        jobTitle = user.getJobTitle();
        profilePictureUrl = user.getProfilePictureURL();

    }

    public String getFirstName() {
        return firstName;
    }

    public String getId() {
        return id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getLastName() {
        return lastName;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }
}
