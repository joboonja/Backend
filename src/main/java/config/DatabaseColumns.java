package config;

import java.text.MessageFormat;

public class DatabaseColumns {
    public static final String USER_COLUMNS = " userId, firstName, lastName, profilePictureUrl, bio, jobTitle ";
    public static final String USER_SKILL = " usid, name, points ";
    public static final String SKILL = " name ";

    public static String PROJECT_FIND_COLOUMNS(String p) {return   MessageFormat.format("{0}.creationDate, " +
            "{0}.pid, {0}.title, {0}.imageUrl, {0}.projectDescription, " +
            "{0}.budget, {0}.deadline ", p); }

    public static String PROJECT_REQ_FIND_COLOUMNS(String r) { return MessageFormat.format("{0}.name, {0}.points ", r);}
}
