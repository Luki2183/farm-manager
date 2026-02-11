package pl.luki2183.farmManager.fieldInfo.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FieldInfoNotFoundException extends RuntimeException {
    public FieldInfoNotFoundException(String message) {
        super(message);
    }
}
