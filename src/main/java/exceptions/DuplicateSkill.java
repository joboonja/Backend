package exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "This user have this skill already.")  // 406
public class DuplicateSkill extends RuntimeException {
    public DuplicateSkill() {
        super();
    }

    public DuplicateSkill(String skillName) {
        super(skillName);
    }
}