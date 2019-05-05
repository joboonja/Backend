package models.data.skill.mapper.UserSkillMapper;

import models.data.mapper.IMapper;
import models.data.skill.UserSkill;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface IUserSkillMapper extends IMapper<UserSkill, String> {
    void deleteUserSkill(String name);
    String getDeleteUserSkillStatement();
}
