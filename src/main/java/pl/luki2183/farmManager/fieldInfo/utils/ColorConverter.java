package pl.luki2183.farmManager.fieldInfo.utils;

import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class ColorConverter {
    public String toHexString(Color color) {
        return String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
    }
}
