package skill;

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
}
