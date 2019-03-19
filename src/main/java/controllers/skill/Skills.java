package controllers.skill;


import config.ProjectServiceConfig;
import exceptions.*;
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
        return SkillService.getSkillsOfUser();
    }

    @RequestMapping(value = "/skills", method = RequestMethod.POST)
    public ArrayList<UserSkill> addSkill(@RequestParam("skillName") String skillName)
            throws UserNotFound, DuplicateSkill, InvalidSkill
    {
        SkillService.addSkill(skillName);
        return SkillService.getSkillsOfUser();
    }

    @RequestMapping(value = "/skills/{skillName}", method = RequestMethod.DELETE)
    public void removeSkill(@PathVariable(value = "skillName") String skillName) throws UserNotFound
    {
        User user = UserRepo.getInstance().getUserById(ProjectServiceConfig.USER_ID);
        user.deleteSkill(skillName);
    }

    @RequestMapping(value = "/skills/{userId}", method = RequestMethod.POST)
    public void endorseSkill(@PathVariable(value = "userId") String userID,
                             @RequestParam("skillName") String skillName)
                                throws DuplicateEndorse, UserNotFound
    {

        EndorseService.endorseUserSkill(userID, skillName);

    }
}
