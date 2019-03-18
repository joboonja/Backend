package exceptions;
import config.ExceptionsConfig;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

public class DuplicateEndorse extends ServerException {
    public DuplicateEndorse() {
        super(ExceptionsConfig.DuplicateEndorseStatus, ExceptionsConfig.DuplicateEndorseMsg);
    }


}