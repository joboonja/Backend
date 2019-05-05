package models.services.user;

import exceptions.UserNotFound;
import models.data.user.User;
import models.data.user.mapper.UserMapper;

import java.sql.SQLException;

public class UserService {
   
    public static User getUserByID(String userId) throws UserNotFound, SQLException {
        return UserMapper.getInstance().getUserById(userId);
    }

}
