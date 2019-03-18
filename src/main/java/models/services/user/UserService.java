package models.services.user;

import exceptions.UserNotFound;
import models.data.user.User;
import models.data.user.UserRepo;

public class UserService {
   
    public static User getUserByID(String userId) throws UserNotFound {
        return UserRepo.getInstance().getUserById(userId);
    }

}
