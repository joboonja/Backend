package controllers.skill.Requests;

import java.io.Serializable;

public class EndorseRequest implements Serializable {
    public String skillName;

    public String getSkillName() {
        return skillName;
    }


    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }


}
