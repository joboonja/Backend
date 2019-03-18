package exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "You already have a bid on this project.")  // 406
public class AlreadyBid extends RuntimeException {
    public AlreadyBid() {
        super();
    }

    public AlreadyBid(String id) {
        super(id);
    }
}