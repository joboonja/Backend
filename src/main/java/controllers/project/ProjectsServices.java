package controllers.project;

import config.ProjectServiceConfig;
import models.data.project.Project;
import models.data.project.ProjectRepo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProjectsServices {
    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public ArrayList <Project> getDefaultData() {
        return ProjectRepo.getInstance().getProjectsForUser(ProjectServiceConfig.USER_ID);
    }
}
