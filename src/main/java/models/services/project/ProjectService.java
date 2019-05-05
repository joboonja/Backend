package models.services.project;

import config.ProjectServiceConfig;
import exceptions.ProjectNotFound;
import exceptions.UserNotFound;
import models.data.bid.Bid;
import models.data.bid.mapper.BidMapper;
import models.data.project.Project;
import models.data.project.mapper.ProjectMapper;
import models.data.user.User;
import models.data.user.mapper.UserMapper;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProjectService {
    public static Project getProjectByID(String id) throws ProjectNotFound{
        return ProjectMapper.getInstance().getProjectByProjectID(id);
    }

    public static boolean hasBidOnProject(String projectId, String userId) throws Exception {
        return BidMapper.getInstance().hasBidOnProject(projectId, userId);
    }

    public static ArrayList<Project> searchProjects(String query, int pageNumber, int pageSize) throws UserNotFound {
        try {
            return ProjectMapper.getInstance().searchByDescriptionOrName(
                    query, ProjectServiceConfig.USER_ID, pageNumber, pageSize);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserNotFound();
        }
    }
}
