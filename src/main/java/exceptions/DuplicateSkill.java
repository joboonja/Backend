package exceptions;


import config.ExceptionsConfig;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

public class DuplicateSkill extends ServerException{
    public DuplicateSkill() {
        super(ExceptionsConfig.DuplicateSkillStatus, ExceptionsConfig.DuplicateSkillMsg);
    }

}