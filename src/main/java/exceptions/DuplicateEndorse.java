package exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "This skill for this user endorsed already")  // 406
public class DuplicateEndorse extends RuntimeException {
    public DuplicateEndorse() {
        super();
    }

    public DuplicateEndorse(String endorseName) {
        super(endorseName);
    }
}