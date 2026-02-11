package pl.luki2183.farmManager.fieldInfo.model;

import jakarta.persistence.Embeddable;
import org.springframework.stereotype.Component;

@Component
@Embeddable
public class Color {
    private Integer redValue;
    private Integer greenValue;
    private Integer blueValue;

    @Override
    public String toString() {
//        todo create logic
        return "";
    }
}
