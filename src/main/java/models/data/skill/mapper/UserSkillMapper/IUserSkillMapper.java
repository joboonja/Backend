package models.data.skill.mapper.UserSkillMapper;

import exceptions.DataBaseError;
import exceptions.DuplicateEndorse;
import models.data.mapper.IMapper;
import models.data.skill.UserSkill;

import java.sql.SQLException;

public interface IUserSkillMapper extends IMapper<UserSkill, String> {
    boolean canEndorse(String endorserId, String endorsedId, String userSkillName);
    void endorse(String endorserId, String endorsedId, String userSkillName) throws DuplicateEndorse, DataBaseError, SQLException;
    boolean getEndorsedOrNot(String endorserId, String endorsedId, String userSkillName);
}
