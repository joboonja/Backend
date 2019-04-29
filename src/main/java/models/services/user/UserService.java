package models.services.user;

import exceptions.UserNotFound;
import models.data.user.User;
import models.data.user.mapper.UserMapper;

public class UserService {
   
    public static User getUserByID(String userId) throws UserNotFound {
        return UserMapper.getInstance().getUserById(userId);
    }

}
