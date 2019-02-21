package config;

import user.User;

public class JoboonjaConfig {
    public static final String projectTitle = "projectTitle";
    public static final String USER_NOT_FOUND_ERROR = "User not found.";
    public static final String PROJECT_NOT_FOUND_ERROR = "Project not found.";
    public static final String USERNAME_ALREADY_EXISTS_ERROR = "This username already exists.";
    public static final String PROJECT_TITLE_ALREADY_EXISTS_ERROR = "This project title already exists.";
    public static String WINNER_MSG(User user)
    {
        if(user == null)
            return "-> There is no Winner!";
        return "-> winner:" + user.getUsername();
    }

}
