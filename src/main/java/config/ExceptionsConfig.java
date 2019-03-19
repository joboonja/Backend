package config;

import org.springframework.http.HttpStatus;

public class ExceptionsConfig {
    public static final String  UserNotFoundMsg = "Corresponding user not found.";
    public static final HttpStatus UserNotFoundStatus = HttpStatus.NOT_FOUND;
    public static final String ProjectNotFoundMsg = "Corresponding project not found.";
    public static final HttpStatus ProjectNotFoundStatus = HttpStatus.NOT_FOUND;
    public static final String DuplicateSkillMsg = "This user already has the skill";
    public static final HttpStatus DuplicateSkillStatus  = HttpStatus.NOT_ACCEPTABLE;
    public static final String DuplicateEndorseMsg = "This skill is already endorsed by the user.";
    public static final HttpStatus DuplicateEndorseStatus  = HttpStatus.NOT_ACCEPTABLE;
    public static final String AlreadyBidMsg =  "You already have a bid on this project.";
    public static final HttpStatus AlreadyBidStatus  = HttpStatus.NOT_ACCEPTABLE;
    public static final String InvalidBidRequirementsMsg = "You do not have the right requirements needed for this project.";
    public static final HttpStatus InvalidBidRequirementsStatus  = HttpStatus.FORBIDDEN;
    public static final String InvalidSkillMsg = "This skill is not found.";
    public static final HttpStatus InvalidSkillStatus  = HttpStatus.NOT_FOUND;

}
