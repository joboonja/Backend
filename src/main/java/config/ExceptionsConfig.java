package config;

import org.springframework.http.HttpStatus;

public class ExceptionsConfig {
    public static final String  UserNotFoundMsg = "Corresponding user not found.";
    public static final HttpStatus UserNotFoundStatus = HttpStatus.NOT_FOUND;
    public static final String ProjectNotFoundMsg = "Corresponding project not found.";
    public static final HttpStatus ProjectNotFoundStatus = HttpStatus.NOT_FOUND;
    public static final String DuplicateSkillMsg = "This user have this skill already.";
    public static final HttpStatus DuplicateSkillStatus  = HttpStatus.NOT_ACCEPTABLE;
    public static final String DuplicateEndorseMsg = "This skill for this user endorsed already";
    public static final HttpStatus DuplicateEndorseStatus  = HttpStatus.NOT_ACCEPTABLE;
}
