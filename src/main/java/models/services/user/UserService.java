package models.services.user;

import models.data.user.User;
import models.data.user.UserRepo;

public class UserService {
   
    public static User getUserByID(String userId) throws Exception {
        return UserRepo.getInstance().getUserById(userId);
    }
}
