package controllers.skill;


import models.data.skill.Skill;
import models.data.skill.UserSkill;
import models.services.skill.SkillService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class SkillsServices {

    @RequestMapping(value = "/skills", method = RequestMethod.GET)
    public ArrayList<UserSkill> getAllSkills() throws Exception
    {
        try {
            return SkillService.getSkillsOfUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/skills", method = RequestMethod.POST)
    public ArrayList<UserSkill> addSkill(@RequestParam("skill") String skillName)
    {
        try {
            SkillService.addSkill(skillName);
//            SkillService.
        } catch (Exception e) {

        }

    }
}
