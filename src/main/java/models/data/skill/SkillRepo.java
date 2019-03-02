package models.data.skill;

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

    public void addNewSkills(ArrayList<Skill> skills)
    {
        skills.addAll(skills);
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
}
