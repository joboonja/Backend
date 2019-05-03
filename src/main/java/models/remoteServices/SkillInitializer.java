package models.remoteServices;

import config.SkillsConfig;
import models.data.skill.Skill;
import models.data.skill.mapper.SkillMapper.SkillMapper;
import models.data.skill.mapper.UserSkillMapper.UserSkillMapper;
import tools.HttpRequest;
import tools.JSONDecoder;

import java.sql.SQLException;
import java.util.ArrayList;

public class SkillInitializer {
    private static ArrayList<Skill> getSkills()
    {
        String skillsInfo = null;
        try {
            skillsInfo = HttpRequest.getRemoteData(SkillsConfig.SKILL_INIT_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSONDecoder.decodeJSONListToSkillList(skillsInfo);
    }
    public static void initSkills() throws SQLException {
        ArrayList<Skill> skills = getSkills();
        SkillMapper skillMapper =  SkillMapper.getInstance();
        skillMapper.addNewSkills(skills);
    }
}
