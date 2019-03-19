package exceptions;


import config.ExceptionsConfig;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

public class InvalidSkill extends ServerException{
    public InvalidSkill() {
        super(ExceptionsConfig.InvalidSkillStatus, ExceptionsConfig.InvalidSkillMsg);
    }

}