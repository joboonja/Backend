package controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Corresponding user not found.")  // 404
public class UserNotFound extends RuntimeException {
    public UserNotFound() {
        super();
    }

    public UserNotFound(String id) {
        super(id);
    }
}