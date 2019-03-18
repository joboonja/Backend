package exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "You do not have the right requirements needed for this project.")  // 406
public class InvalidBidRequirements extends RuntimeException {
    public InvalidBidRequirements() {
        super();
    }

    public InvalidBidRequirements(String id) {
        super(id);
    }
}