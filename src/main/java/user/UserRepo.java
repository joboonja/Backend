package user;

import config.JoboonjaConfig;
import config.UserConfig;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserRepo {
    private static UserRepo ourInstance = new UserRepo();
    private ArrayList<User> users = new ArrayList<User>();

    public static UserRepo getInstance() {
        return ourInstance;
    }

    private UserRepo() {
        users = new ArrayList<User>();
    }
    public User getUserByUsername(String name) throws Exception
    {
        for (User user : users) {
            if (user.getUsername().equals(name))
                return user;
        }
        throw new Exception(JoboonjaConfig.USER_NOT_FOUND_ERROR);
    }
    public void registerNewUser(User newUser)
    {
        for (User user : users) {
            if (user.getUsername().equals(newUser.getUsername())) {
                System.out.println(JoboonjaConfig.USERNAME_ALREADY_EXISTS_ERROR);
                return;
            }
        }
        users.add(newUser);
    }
}