package controllers.skill;

import java.io.Serializable;

public class AddSkillRequest implements Serializable {
    private String skillName;

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getSkillName() {
        return skillName;
    }
}
