package models.data.skill.mapper;

import models.data.mapper.Mapper;
import models.data.skill.Skill;
import models.data.user.User;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SkillMapper extends Mapper<Skill, String> implements ISkillMapper {
    private static SkillMapper ourInstance = new SkillMapper();
    private ArrayList<Skill> skills;

    public static SkillMapper getInstance() {
        return ourInstance;
    }

    private SkillMapper() {
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

    @Override
    protected ArrayList<String> getCreateTableStatement() {
        ArrayList<String> statements = new ArrayList<String>();
        String stmt1 = "CREATE TABLE IF NOT EXISTS Skill(" +
                "name CHAR(20)," +
                "PRIMARY KEY(name)" +
                ");";
        String stmt2 = "CREATE TABLE IF NOT EXISTS UserSkill(" +
                "usid CHAR(20)," +
                "points INTEGER," +
                "name CHAR(20)," +
                "PRIMARY KEY(name, usid)," +
                "FOREIGN KEY(name) REFERENCES Skill ON DELETE CASCADE" +
                ");";
        statements.add(stmt1);
        statements.add(stmt2);
        return statements;
    }
}
