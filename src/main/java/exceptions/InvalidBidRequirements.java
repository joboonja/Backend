package exceptions;

import config.ExceptionsConfig;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

public class InvalidBidRequirements extends ServerException {
    public InvalidBidRequirements() {
        super(ExceptionsConfig.InvalidBidRequirementsStatus, ExceptionsConfig.InvalidBidRequirementsMsg);
    }

}