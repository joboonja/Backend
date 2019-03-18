package exceptions;

import config.ExceptionsConfig;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

public class ProjectNotFound extends ServerException {
    public ProjectNotFound() {
        super(ExceptionsConfig.ProjectNotFoundStatus, ExceptionsConfig.ProjectNotFoundMsg);
    }

}