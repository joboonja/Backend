package controllers;

import models.data.bid.mapper.BidMapper;
import models.data.project.Project;
import models.data.project.mapper.ProjectMapper;
import models.data.skill.UserSkill;
import models.data.user.mapper.UserMapper;
import models.remoteServices.ProjectInitializer;
import models.remoteServices.SkillInitializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.util.HashMap;

@Component
public class Initializer {
    @PostConstruct
    public void init() {
        BidMapper.getInstance();
        UserSkill userSkill = new UserSkill("C++", 12);
        HashMap<String, UserSkill> skills = new HashMap<>();
        skills.put("C++", userSkill);
        skills.put("java", new UserSkill("java", 10));
        Project project = new Project("1", "hello", "red", "http",
                12, skills, 122, 123);
        try {
            ProjectMapper.getInstance().insert(project);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            Project p = ProjectMapper.getInstance().find("1");
            System.out.println(p);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SkillInitializer.initSkills();
        ProjectInitializer.initProjects();
        UserMapper.getInstance().addDefaultUser();
    }
}


