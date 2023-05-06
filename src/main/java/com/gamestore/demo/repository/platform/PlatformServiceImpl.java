package com.gamestore.demo.repository.platform;

import com.gamestore.demo.exceptions.platform.PlatformNotFoundException;
import com.gamestore.demo.model.Platform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlatformServiceImpl implements PlatformService {

    @Autowired
    private PlatformRepository platformRepository;

    @Override
    public List<Platform> getAllPlatforms() {
        return platformRepository.findAll();
    }

    @Override
    public Platform getPlatformById(Long id) {
        return platformRepository.findById(id).orElseThrow(() -> new PlatformNotFoundException(id));
    }

    @Override
    public Platform savePlatform(Platform platform) {
        return platformRepository.save(platform);
    }

    @Override
    public Platform updatePlatform(Long id, Platform platform) {
        Optional<Platform> optionalPlatform = platformRepository.findById(id);
        if (optionalPlatform.isPresent()) {
            Platform existingPlatform = optionalPlatform.get();
            existingPlatform.setName(platform.getName());
            return platformRepository.save(existingPlatform);
        } else {
            throw new PlatformNotFoundException(id);
        }
    }

    @Override
    public void deletePlatformById(Long id) {
        Optional<Platform> optionalPlatform = platformRepository.findById(id);
        if (optionalPlatform.isPresent()) {
            platformRepository.deleteById(id);
        } else {
            throw new PlatformNotFoundException(id);
        }
    }
}
