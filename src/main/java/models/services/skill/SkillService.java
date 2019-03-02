package models.services.skill;

import config.ProjectServiceConfig;
import models.data.skill.Skill;
import models.data.skill.SkillRepo;
import models.data.skill.UserSkill;
import models.data.user.User;
import models.data.user.UserRepo;

import java.util.ArrayList;

public class SkillService {
    public static void addSkill(String skillName) throws Exception
    {
        User user = UserRepo.getInstance().getUserById(ProjectServiceConfig.USER_ID);
        user.addSkill(new UserSkill(skillName, 0));
    }
    public static ArrayList<Skill> notSubmittedSkills(String userID) throws Exception
    {
        User user = UserRepo.getInstance().getUserById(userID);
        return SkillRepo.getInstance().notSubmittedSkills(user);
    }
}
