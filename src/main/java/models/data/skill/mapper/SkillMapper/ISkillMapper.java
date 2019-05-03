package models.data.skill.mapper.SkillMapper;

import models.data.mapper.IMapper;
import models.data.skill.Skill;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ISkillMapper extends IMapper<Skill, String> {
    ArrayList<Skill> getNotSubmittedSkills(String userId) throws SQLException;
}
