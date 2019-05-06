package models.services.user;

import exceptions.UserNotFound;
import models.data.user.User;
import models.data.user.mapper.UserMapper;

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

}
