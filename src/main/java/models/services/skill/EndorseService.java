package models.services.skill;

import exceptions.DuplicateEndorse;
import exceptions.UserNotFound;
import models.data.user.User;
import models.data.user.mapper.UserMapper;

import java.sql.SQLException;

public class EndorseService {
    public static void endorseUserSkill(String userID, String skillName) throws UserNotFound, DuplicateEndorse, SQLException {
        UserMapper userMapper = UserMapper.getInstance();
        User userToEndorse = userMapper.getUserById(userID);
        userToEndorse.endorse(skillName);
    }
}
