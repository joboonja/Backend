package project;

import config.ProjectConfig;

import java.util.ArrayList;
import user.User;
import user.UserRepo;

public class ProjectRepo {
    private static ProjectRepo ourInstance = new ProjectRepo();
    private ArrayList<Project> projects ;

    public static ProjectRepo getInstance() {
        return ourInstance;
    }

    private ProjectRepo() {
        projects = new ArrayList<Project>();
    }

    public Project getProjectByProjectID(String projectID) throws Exception
    {
        for (Project project : projects)
        {
            if (project.getID().equals(projectID))
                return project;
        }
        throw new Exception(ProjectConfig.PROJECT_NOT_FOUND_ERROR);
    }
    public Project getProjectByIDForUser(String projectID, String userID) throws Exception
    {
        Project project = getProjectByProjectID(projectID);
        User user = UserRepo.getInstance().getUserById(userID);
        if(!project.checkSkillSatisfaction(user.getSkills()))
            throw new Exception(ProjectConfig.USER_CANNOT_SATISFY_PROJECT);
        return project;
    }

    public void addNewProject(Project newProject)
    {
        for (Project project : projects)
            if (project.getTitle().equals(project.getTitle())){
                System.out.println(ProjectConfig.PROJECT_TITLE_ALREADY_EXISTS_ERROR);
                return;
            }
        projects.add(newProject);
    }
    public void addNewProjects(ArrayList<Project> projects)
    {
        projects.addAll(projects);
    }
    public ArrayList<Project> getProjectsForUser(String userId) throws Exception {
        ArrayList<Project> projectsForUser = new ArrayList<Project>();
        User user = UserRepo.getInstance().getUserById(userId);
        for(Project project : projects)
        {
            if(project.checkSkillSatisfaction(user.getSkills()))
            {
                projectsForUser.add(project);
            }
        }
        return projectsForUser;
    }

}
