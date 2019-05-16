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
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") String pageSize,
            @RequestAttribute("id") String userId) throws UserNotFound
    {
        ArrayList<Project> projects;
        if(query != null)
            projects =  ProjectService.searchProjects(
                    query,
                    Integer.valueOf(pageNumber),
                    Integer.valueOf(pageSize),
                    userId);
        else
            projects = ProjectMapper.getInstance().getProjectsForUser(
                userId,
                Integer.valueOf(pageNumber),
                Integer.valueOf(pageSize));

        for (Project project : projects)
            project.setHasBidOrNot(ProjectService.hasBidOnProject(project.getID(), userId));

        return projects;
    }

    @RequestMapping(value = "/projects/{id}", method = RequestMethod.GET)
    public Project getSingleProject(@PathVariable(value = "id") String id,
                                    @RequestAttribute("id") String userId) throws ProjectNotFound {
        Project project = ProjectService.getProjectByID(id);
        project.setHasBidOrNot(ProjectService.hasBidOnProject(project.getID(), userId));
        return project;
    }
}

