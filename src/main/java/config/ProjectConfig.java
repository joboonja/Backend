package config;

import models.data.project.Project;

import java.text.MessageFormat;

public class ProjectConfig {
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String IMAGE_URL = "imageUrl";
    public static final String DEADLINE = "deadline";
    public static final String SKILLS = "skills";
    public static final String CREATION_DATE = "creationDate";
    public static final String BUDGET = "budget";
    public static final String PROJECT_NOT_FOUND_ERROR = "Project not found.";
    public static final String PROJECT_TITLE_ALREADY_EXISTS_ERROR = "This project title already exists.";
    public static final String PROJECT_INIT_URL = "projects";
    public static final String USER_CANNOT_SATISFY_PROJECT = "UserInfo cannot satisfy this project";
    public static String PROJECT_FIND_COLOUMNS(String p) {return   MessageFormat.format("{0}.creationDate, " +
            "{0}.pid, {0}.title, {0}.imageUrl, {0}.projectDescription, " +
            "{0}.budget, {0}.deadline ", p); }

    public static String PROJECT_REQ_FIND_COLOUMNS(String r) { return MessageFormat.format("{0}.name, {0}.points ", r);}
}
