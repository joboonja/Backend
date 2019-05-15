package models.services.user;

import com.google.common.hash.Hashing;
import exceptions.InvalidUser;
import exceptions.UserAlreadyExists;
import exceptions.UserNotFound;
import models.data.user.User;
import models.data.user.mapper.UserMapper;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserService {
   
    public static User getUserByID(String userId) throws UserNotFound, SQLException {
        return UserMapper.getInstance().getUserById(userId);
    }

    public static ArrayList<User> searchUsers(String query) throws UserNotFound {
        try {
            return UserMapper.getInstance().searchByName(query);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserNotFound();
        }
    }

    public static void registerUser(User user) throws InvalidUser, UserAlreadyExists {
        if(user.getId().equals("") || user.getFirstName().equals("") || user.getLastName().equals("")
            || user.getJobTitle().equals("") || user.getPassword().equals(""))
            throw new InvalidUser();
        user.setPassword(
                Hashing.sha256().hashString(user.getPassword(), StandardCharsets.UTF_8)
                .toString()
        );
        try {
            UserMapper.getInstance().registerNewUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidUser();
        }
    }

}
