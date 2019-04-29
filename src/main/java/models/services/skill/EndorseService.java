package models.services.skill;

import exceptions.DuplicateEndorse;
import exceptions.UserNotFound;
import models.data.user.User;
import models.data.user.mapper.UserMapper;

public class EndorseService {
    public static void endorseUserSkill(String userID, String skillName) throws UserNotFound, DuplicateEndorse
    {
        UserMapper userMapper = UserMapper.getInstance();
        User userToEndorse = userMapper.getUserById(userID);
        userToEndorse.endorse(skillName);
    }
}
