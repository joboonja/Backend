package controllers.skill;


import config.ProjectServiceConfig;
import controllers.skill.Requests.AddSkillRequest;
import controllers.skill.Requests.EndorseRequest;
import exceptions.*;
import models.data.skill.Skill;
import models.data.skill.UserSkill;
import models.data.skill.mapper.UserSkillMapper.UserSkillMapper;
import models.data.user.User;
import models.data.user.mapper.UserMapper;
import models.services.skill.EndorseService;
import models.services.skill.SkillService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;

@RestController
public class Skills {

    @RequestMapping(value = "/skills", method = RequestMethod.GET)
    public ArrayList<UserSkill> getAllSkills() throws Exception
    {
        return SkillService.getSkillsOfUser();
    }

    @RequestMapping(value = "/skills", method = RequestMethod.POST)
    public ArrayList<UserSkill> addSkill(@RequestBody final AddSkillRequest request)
            throws UserNotFound, DuplicateSkill, InvalidSkill, SQLException {
        SkillService.addSkill(request.getSkillName());
        return SkillService.getSkillsOfUser();
    }

    @RequestMapping(value = "/skills/{skillName}", method = RequestMethod.DELETE)
    public void removeSkill(@PathVariable(value = "skillName") String skillName) throws UserNotFound, SQLException {
        User user = UserMapper.getInstance().getUserById(ProjectServiceConfig.USER_ID);
        UserSkillMapper.getInstance().deleteUserSkill(skillName);
    }

    @RequestMapping(value = "/skills/{userId}", method = RequestMethod.POST)
    public ArrayList<UserSkill> endorseSkill(@PathVariable(value = "userId") String userID,
                             @RequestBody final EndorseRequest request)
            throws DuplicateEndorse, UserNotFound, SQLException {

        EndorseService.endorseUserSkill(userID, request.getSkillName());
        return SkillService.getSkillsOfUser(userID);

    }

    @RequestMapping(value = "/endorsableSkills", method = RequestMethod.GET)
    public ArrayList<Skill> getAllEndorsableSkills() throws Exception
    {
        return SkillService.getNotSubmittedSkills(ProjectServiceConfig.USER_ID);
    }



}
