package exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Corresponding project not found.")  // 404
public class ProjectNotFound extends RuntimeException {
    public ProjectNotFound() {
        super();
    }

    public ProjectNotFound(String id) {
        super(id);
    }
}