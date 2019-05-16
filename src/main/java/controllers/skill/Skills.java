package controllers.skill;


import config.ProjectServiceConfig;
import controllers.skill.requests.AddSkillRequest;
import controllers.skill.requests.EndorseRequest;
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
    public ArrayList<UserSkill> getAllSkills(@RequestAttribute("id") String userId) throws Exception
    {
        return SkillService.getSelfSkills(userId);
    }

    @RequestMapping(value = "/skills", method = RequestMethod.POST)
    public ArrayList<UserSkill> addSkill(@RequestBody final AddSkillRequest request,
                                         @RequestAttribute("id") String userId)
            throws UserNotFound, DuplicateSkill, InvalidSkill, SQLException {
        SkillService.addSkill(request.getSkillName(), userId);
        return SkillService.getSelfSkills(userId);
    }

    @RequestMapping(value = "/skills/{skillName}", method = RequestMethod.DELETE)
    public void removeSkill(@PathVariable(value = "skillName") String skillName,
                            @RequestAttribute("id") String userId) throws UserNotFound, SQLException {
        UserMapper.getInstance().getUserById(userId);
        if(skillName.equals("Node"))
            skillName = "Node.js";
        UserSkillMapper.getInstance().deleteUserSkill(skillName, userId);
    }

    @RequestMapping(value = "/skills/{userId}", method = RequestMethod.POST)
    public ArrayList<UserSkill> endorseSkill(@PathVariable(value = "userId") String endorsedUserId,
                                             @RequestBody final EndorseRequest request,
                                             @RequestAttribute("id") String userId)
            throws DuplicateEndorse, UserNotFound, SQLException, DataBaseError, EndorseSelf {
        if(userId.equals(endorsedUserId))
            throw new EndorseSelf();
        EndorseService.endorseUserSkill(userId, endorsedUserId, request.getSkillName());
        return SkillService.getSkillsOfUser(endorsedUserId, userId);
    }

    @RequestMapping(value = "/endorsableSkills", method = RequestMethod.GET)
    public ArrayList<Skill> getAllEndorsableSkills(@RequestAttribute("id") String userId) throws Exception
    {
        return SkillService.getNotSubmittedSkills(userId);
    }



}
