package models.data.skill.mapper;

import models.data.mapper.IMapper;
import models.data.skill.Skill;
import models.data.skill.UserSkill;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ISkillMapper extends IMapper<Skill, String> {
    ArrayList<Skill> getNotSubmittedSkills(String userId) throws SQLException;
    void insertUserSkill(UserSkill userskill) throws SQLException;
    String getInsertUserSkillStatement();
}
