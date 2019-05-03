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
    ArrayList<Project> findUserProjects(String userId) throws SQLException;
    HashMap<String, UserSkill> findProjectRequires(String projectId) throws SQLException;
    ArrayList<Project> searchByDescriptionOrName(String query, String userId) throws SQLException;
}
