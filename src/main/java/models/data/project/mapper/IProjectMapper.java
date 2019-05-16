package models.data.project.mapper;

import models.data.mapper.IMapper;
import models.data.project.Project;
import models.data.skill.UserSkill;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public interface IProjectMapper extends IMapper<Project , String> {
    void insert(Project project) throws SQLException;
    ArrayList<Project> findUserProjects(String userId, int pageNumber, int pageSize) throws SQLException;
    HashMap<String, UserSkill> findProjectRequires(String projectId) throws SQLException;
    ArrayList<Project> searchByDescriptionOrName(String query, String userId, int pageNumber, int pageSize) throws SQLException;
    ArrayList<Project> getPassedDeadlineProjects() throws SQLException;
    String getPassedDeadlineProjectsStatement();
    void setWinner(String userId, String projectId) throws SQLException;
    String getSetWinnerStatement();
}
