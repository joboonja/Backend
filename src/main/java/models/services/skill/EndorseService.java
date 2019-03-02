package models.services.skill;

import models.data.user.User;
import models.data.user.UserRepo;

public class EndorseService {
    public static void endorseUserSkill(String userID, String skillName) throws Exception
    {
        UserRepo userRepo = UserRepo.getInstance();
        User userToEndorse = userRepo.getUserById(userID);
        userToEndorse.endorse(skillName);
    }
}
