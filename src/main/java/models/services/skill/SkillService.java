package models.services.skill;

import models.data.skill.SkillRepo;
import models.data.skill.UserSkill;
import models.data.user.User;
import models.data.user.UserRepo;

import java.util.ArrayList;

public class SkillService {
    public static void addSkill(String skillName)
    {

    }
    public static ArrayList<UserSkill> notSubmittedSkills(String userID) throws Exception
    {
        ArrayList<UserSkill> notSumbitted = new ArrayList<>();
        User user = UserRepo.getInstance().getUserById(userID);
        for (UserSkill skill:
                user.getSkills().values()) {
            if(SkillRepo.getInstance().contains(skill.getName()))
                notSumbitted.add(skill);
        }
        return notSumbitted;
    }
}
