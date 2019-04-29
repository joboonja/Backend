package models.data.project.mapper;

import models.data.mapper.IMapper;
import models.data.project.Project;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface IProjectMapper extends IMapper<Project , String> {
    void insert(Project project) throws SQLException;
}
