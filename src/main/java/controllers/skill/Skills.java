package controllers.skill;


import config.ProjectServiceConfig;
import exceptions.DuplicateEndorse;
import exceptions.DuplicateSkill;
import exceptions.UserNotFound;
import models.data.skill.UserSkill;
import models.data.user.User;
import models.data.user.UserRepo;
import models.services.skill.EndorseService;
import models.services.skill.SkillService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class Skills {

    @RequestMapping(value = "/skills", method = RequestMethod.GET)
    public ArrayList<UserSkill> getAllSkills() throws Exception
    {
        try {
            return SkillService.getSkillsOfUser();
        } catch (Exception e) {
           throw new UserNotFound();
        }
    }

    @RequestMapping(value = "/skills", method = RequestMethod.POST)
    public ArrayList<UserSkill> addSkill(@RequestParam("skillName") String skillName) throws DuplicateSkill, UserNotFound
    {
        try {
            SkillService.addSkill(skillName);
            return SkillService.getSkillsOfUser();
        } catch (Exception e) {
            if(e instanceof DuplicateSkill)
                throw (DuplicateSkill) e;
            throw new UserNotFound();
        }
    }

    @RequestMapping(value = "/skills/{skillName}", method = RequestMethod.DELETE)
    public void removeSkill(@PathVariable(value = "skillName") String skillName) throws UserNotFound
    {
        try
        {
            User user = UserRepo.getInstance().getUserById(ProjectServiceConfig.USER_ID);
            user.deleteSkill(skillName);
        } catch (Exception e) {
            throw new UserNotFound();
        }
    }

    @RequestMapping(value = "/skills/{userId}", method = RequestMethod.POST)
    public void endorseSkill(@PathVariable(value = "userId") String userID,
                             @RequestParam("skillName") String skillName) throws DuplicateEndorse, UserNotFound
    {
        try {
            EndorseService.endorseUserSkill(userID, skillName);
        } catch (Exception e) {
            if(e instanceof DuplicateEndorse)
                throw (DuplicateEndorse) e;
            throw new UserNotFound();
        }
    }
}
