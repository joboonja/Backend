package models.services.project;

import models.data.project.Project;
import models.data.project.ProjectRepo;

public class ProjectService {
    public static Project getProjectByID(String id) throws Exception {
        return ProjectRepo.getInstance().getProjectByProjectID(id);
    }
}
