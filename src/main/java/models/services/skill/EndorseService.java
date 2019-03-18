package models.services.skill;

import exceptions.DuplicateEndorse;
import exceptions.UserNotFound;
import models.data.user.User;
import models.data.user.UserRepo;

public class EndorseService {
    public static void endorseUserSkill(String userID, String skillName) throws UserNotFound, DuplicateEndorse
    {
        UserRepo userRepo = UserRepo.getInstance();
        User userToEndorse = userRepo.getUserById(userID);
        userToEndorse.endorse(skillName);
    }
}
