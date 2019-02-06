package config;

import user.User;

public class JoboonjaConfig {
    public static final String projectTitle = "projectTitle";
    public static final String USER_NOT_FOUND_ERROR = "user not found";
    public static final String PROJECT_NOT_FOUND_ERROR = "project not found";
    public static String WINNER_MSG(User user)
    {
        if(user == null)
            return "-> There is no Winner!";
        return "-> winner:" + user.getUsername();
    }

}
