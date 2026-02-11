package pl.luki2183.farmManager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
@ResponseBody
public class PrimaryKeyViolationException extends RuntimeException {
    public PrimaryKeyViolationException() {
        super("Cannot process POST request. Resource with the same data already exists.");
    }
}
