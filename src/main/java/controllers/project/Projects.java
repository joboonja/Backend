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
    public ArrayList <Project> getAllProjects(
            @RequestParam(value = "search", required = false) String query,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") String pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") String pageSize) throws UserNotFound
    {
        if(query != null)
            return ProjectService.searchProjects(
                    query,
                    Integer.valueOf(pageNumber),
                    Integer.valueOf(pageSize));

        return ProjectMapper.getInstance().getProjectsForUser(
                ProjectServiceConfig.USER_ID,
                Integer.valueOf(pageNumber),
                Integer.valueOf(pageSize));

    }

    @RequestMapping(value = "/projects/{id}", method = RequestMethod.GET)
    public Project getSingleProject(@PathVariable(value = "id") String id) throws ProjectNotFound {
        return ProjectService.getProjectByID(id);
    }
}

