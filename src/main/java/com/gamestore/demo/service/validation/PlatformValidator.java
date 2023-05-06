package com.gamestore.demo.service.validation;

import com.gamestore.demo.model.Platform;

public final class PlatformValidator {

    private PlatformValidator() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean isValidPlatform(Platform platform) {
        return isValidPlatformName(platform.getName());
    }

    public static boolean isValidPlatformName(String name) {
        return name != null && !name.isEmpty();
    }
}
