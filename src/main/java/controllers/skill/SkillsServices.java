package controllers.skill;


import config.ProjectServiceConfig;
import exceptions.DuplicateSkill;
import exceptions.UserNotFound;
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
           throw new UserNotFound(ProjectServiceConfig.USER_ID);
        }
    }

    @RequestMapping(value = "/skills", method = RequestMethod.POST)
    public ArrayList<UserSkill> addSkill(@RequestParam("skill") String skillName) throws Exception
    {
        try {
            SkillService.addSkill(skillName);
            return SkillService.getSkillsOfUser();
        } catch (Exception e) {
            if(e instanceof DuplicateSkill)
                throw e;
            throw new UserNotFound(ProjectServiceConfig.USER_ID);
        }
    }
}
