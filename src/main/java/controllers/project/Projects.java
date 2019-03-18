package controllers.project;

import config.ProjectServiceConfig;
import controllers.exceptions.ProjectNotFound;
import controllers.exceptions.UserNotFound;
import models.data.project.Project;
import models.data.project.ProjectRepo;
import models.services.project.ProjectService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;

@RestController
public class Projects {
    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public ArrayList <Project> getAllProjects() {
        try {
            return ProjectRepo.getInstance().getProjectsForUser(ProjectServiceConfig.USER_ID);
        } catch (Exception e) {
            throw new UserNotFound(ProjectServiceConfig.USER_ID);
        }
    }
    @RequestMapping(value = "/projects/{id}", method = RequestMethod.GET)
    public Project getSingleProject(@PathVariable(value = "id") String id) {
        try {
            return ProjectService.getProjectByID(id);
        } catch (Exception e) {
            throw new ProjectNotFound(id);
        }
    }
}

