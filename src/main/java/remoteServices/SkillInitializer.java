package remoteServices;

import config.ProjectConfig;
import config.SkillsConfig;
import org.json.JSONArray;
import org.json.JSONObject;
import skill.Skill;
import skill.SkillRepo;
import tools.HttpRequest;
import tools.JSONDecoder;

import java.util.ArrayList;

public class SkillInitializer {
    private static ArrayList<Skill> getSkills()
    {
        JSONArray skillsInfo = null;
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
