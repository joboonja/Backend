package models.services.skill;

import config.ProjectServiceConfig;
import exceptions.DataBaseError;
import exceptions.DuplicateEndorse;
import models.data.skill.mapper.UserSkillMapper.UserSkillMapper;

import java.sql.SQLException;


public class EndorseService {
    public static void endorseUserSkill(String userID, String skillName) throws  DuplicateEndorse, DataBaseError {
        try {
            UserSkillMapper.getInstance().endorse(ProjectServiceConfig.USER_ID,
                                                    userID,
                                                    skillName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
