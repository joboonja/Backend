package remoteServices;

import config.SkillsConfig;
import org.json.JSONArray;
import skill.Skill;
import skill.SkillRepo;
import tools.HttpRequest;
import tools.JSONDecoder;

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
    public static void initSkills()
    {
        ArrayList<Skill> skills = getSkills();
        SkillRepo skillRepo =  SkillRepo.getInstance();
        skillRepo.addNewSkills(skills);
    }
}
