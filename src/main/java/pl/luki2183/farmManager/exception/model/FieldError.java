package pl.luki2183.farmManager.exception.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FieldError {
    private String message;
    private String path;
    private String code;
}
