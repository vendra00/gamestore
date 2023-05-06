package com.gamestore.demo.repository.platform;

import com.gamestore.demo.model.Platform;

import java.util.List;

public interface PlatformService {
    List<Platform> getAllPlatforms();
    Platform getPlatformById(Long id);
    Platform savePlatform(Platform platform);
    Platform updatePlatform(Long id, Platform platform);
    void deletePlatformById(Long id);
}
