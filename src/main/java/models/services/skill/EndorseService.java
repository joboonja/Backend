package models.services.skill;

import config.ProjectServiceConfig;
import exceptions.DataBaseError;
import exceptions.DuplicateEndorse;
import models.data.skill.mapper.UserSkillMapper.UserSkillMapper;

import java.sql.SQLException;


public class EndorseService {
    public static void endorseUserSkill(String userID, String endorsedUserId,  String skillName) throws  DuplicateEndorse, DataBaseError {
        try {
            UserSkillMapper.getInstance().endorse(userID,
                                                    endorsedUserId,
                                                    skillName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
