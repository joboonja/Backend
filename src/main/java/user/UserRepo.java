package user;

import config.UserConfig;

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
    public User getUserById(String name) throws Exception
    {
        for (User user : users) {
            if (user.getId().equals(name))
                return user;
        }
        throw new Exception(UserConfig.USER_NOT_FOUND_ERROR);
    }
    public void registerNewUser(User newUser)
    {
        for (User user : users) {
            if (user.getId().equals(newUser.getId())) {
                System.out.println(UserConfig.USERNAME_ALREADY_EXISTS_ERROR);
                return;
            }
        }
        users.add(newUser);
    }
}
