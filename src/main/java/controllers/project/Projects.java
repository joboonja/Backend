package controllers.project;

import config.ProjectServiceConfig;
import exceptions.ProjectNotFound;
import exceptions.UserNotFound;
import models.data.project.Project;
import models.data.project.mapper.ProjectMapper;
import models.services.project.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class Projects {
    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public ArrayList <Project> getAllProjects(@RequestParam(value = "search", required = false) String query) throws UserNotFound
    {
        if(query != null)
            return ProjectService.searchProjects(query);
        return ProjectMapper.getInstance().getProjectsForUser(ProjectServiceConfig.USER_ID);
    }

    @RequestMapping(value = "/projects/{id}", method = RequestMethod.GET)
    public Project getSingleProject(@PathVariable(value = "id") String id) throws ProjectNotFound {
        return ProjectService.getProjectByID(id);
    }
}

