package com.gamestore.demo.service.validation;

import com.gamestore.demo.model.Platform;
import org.springframework.stereotype.Component;

@Component
public class PlatformValidator {

    private PlatformValidator() {
    }

    public static boolean isValidPlatform(Platform platform) {
        return isValidPlatformName(platform.getName());
    }

    public static boolean isValidPlatformName(String name) {
        return name != null && !name.isEmpty();
    }
}
