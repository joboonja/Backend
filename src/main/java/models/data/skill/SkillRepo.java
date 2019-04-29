package models.data.skill;

import models.data.user.User;

import java.util.ArrayList;

public class SkillRepo {
    private static SkillRepo ourInstance = new SkillRepo();
    private ArrayList<Skill> skills;

    public static SkillRepo getInstance() {
        return ourInstance;
    }

    private SkillRepo() {
        skills = new ArrayList<Skill>();
    }
    public void addNewSkills(ArrayList<Skill> skillsToAdd)
    {
        skills.addAll(skillsToAdd);
    }

    public boolean contains(String skillName)
    {
        for (Skill skill :
                skills) {
            if(skill.getName().equals(skillName))
                return true;
        }
        return  false;
    }

    public ArrayList<Skill> notSubmittedSkills(User user)
    {

        ArrayList<Skill> notSumbitted = new ArrayList<>();
        for (Skill skill:
                skills) {
            if(!user.haveSkill(skill.getName()))
                notSumbitted.add(skill);
        }
        return notSumbitted;
    }
}
