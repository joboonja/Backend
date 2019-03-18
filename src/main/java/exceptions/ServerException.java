package exceptions;

import org.springframework.http.HttpStatus;

public class ServerException extends Exception{
    protected HttpStatus status;
    protected String message;
    ServerException(HttpStatus status, String message)
    {
        super();
        this.status = status;
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
