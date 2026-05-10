package pl.luki2183.farmManager.exception.dto;

import lombok.Data;
import pl.luki2183.farmManager.exception.model.FieldError;

import java.util.List;

@Data
public class ErrorResponse {
    List<FieldError> errorList;
}
