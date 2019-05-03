package models.services.skill;

import config.ProjectServiceConfig;
import exceptions.DuplicateSkill;
import exceptions.InvalidSkill;
import exceptions.UserNotFound;
import models.data.skill.Skill;
import models.data.skill.mapper.SkillMapper.SkillMapper;
import models.data.skill.UserSkill;
import models.data.user.User;
import models.data.user.mapper.UserMapper;

import java.sql.SQLException;
import java.util.ArrayList;

public class SkillService {
    public static void addSkill(String skillName) throws UserNotFound, DuplicateSkill, InvalidSkill, SQLException {
        User user = UserMapper.getInstance().getUserById(ProjectServiceConfig.USER_ID);
        if(user.haveSkill(skillName))
            throw new DuplicateSkill();
        ArrayList <Skill> notSubmitted = getNotSubmittedSkills(ProjectServiceConfig.USER_ID);
        for(Skill skill: notSubmitted) {
            if (skillName.equals(skill.getName())) {
                user.addSkill(new UserSkill(skillName, 0, ProjectServiceConfig.USER_ID));
                return ;
            }
        }
        throw new InvalidSkill();
    }
    public static ArrayList<Skill> getNotSubmittedSkills(String userID) throws UserNotFound, SQLException {
        User user = UserMapper.getInstance().getUserById(userID);
        return SkillMapper.getInstance().getNotSubmittedSkills(user.getId());
    }
    public static ArrayList<UserSkill> getSkillsOfUser() throws UserNotFound {
        User user = UserMapper.getInstance().getUserById(ProjectServiceConfig.USER_ID);
        return user.getSkillsList();

    }
    public static ArrayList<UserSkill> getSkillsOfUser(String userID) throws UserNotFound {
        User user = UserMapper.getInstance().getUserById(userID);
        return user.getSkillsList();

    }
}
