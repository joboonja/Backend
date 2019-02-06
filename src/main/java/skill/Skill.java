package skill;

import config.SkillsConfig;
import org.json.JSONObject;

public class Skill {

    private String name;
    private int points;


    public Skill(JSONObject skillInfo){
        name = skillInfo.getString(SkillsConfig.NAME);
        points = skillInfo.getInt(SkillsConfig.POINTS);
    }
    public String getName()
    {
        return name;
    }
    public int getPoints()
    {
        return points;
    }
}
