package com.gamestore.demo;

import com.gamestore.demo.model.Platform;
import com.gamestore.demo.repository.PlatformRepository;
import com.gamestore.demo.service.platform.PlatformServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class PlatformServiceTests {
    @Mock
    private PlatformRepository platformRepository;

    @InjectMocks
    private PlatformServiceImpl platformService;

    @Test
    public void testSavePlatform() {
        Platform platform = new Platform();
        platform.setName("Test Platform");
        platform.setDescription("A platform for testing");

        Mockito.when(platformRepository.save(Mockito.any(Platform.class))).thenReturn(platform);

        Platform savedPlatform = platformService.savePlatform(platform);

        assertEquals("Test Platform", savedPlatform.getName());
        assertEquals("A platform for testing", savedPlatform.getDescription());
    }

    @Test
    public void testSavePlatformWithEmptyName() {
        Platform platform = new Platform();
        platform.setName("");
        platform.setDescription("A platform with an empty name");

        try {
            platformService.savePlatform(platform);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be empty", e.getMessage());
        }
    }

    @Test
    public void testSavePlatformWithNullName() {
        Platform platform = new Platform();
        platform.setName(null);
        platform.setDescription("A platform with an empty name");

        try {
            platformService.savePlatform(platform);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be null", e.getMessage());
        }
    }

    @Test
    public void testSavePlatformWithEmptyDescription() {
        Platform platform = new Platform();
        platform.setName("Test Platform");
        platform.setDescription("");

        try {
            platformService.savePlatform(platform);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Description cannot be empty", e.getMessage());
        }

        // Ensure that the platform was not saved to the repository
        assertNull(platform.getId());
    }


    @Test
    public void testSavePlatformWithNullDescription() {
        Platform platform = new Platform();
        platform.setName("Test Platform");
        platform.setDescription(null);

        try {
            platformService.savePlatform(platform);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Description cannot be null", e.getMessage());
        }
    }
}
