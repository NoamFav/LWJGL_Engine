package com.pot_pot_engine.Utils;

import com.pot_pot_engine.Consts;

import java.io.*;
import java.nio.file.*;
import java.util.List;

public class ResourceCloner {

    private static final String RESOURCE_DIR =
            System.getProperty("user.home") + "/.pot_pot_engine/resources/";

    /**
     * Initializes resources by copying them to the home directory if not already present. Updates
     * paths in the Consts class to point to the copied resources.
     */
    public static void initializeResources() throws IOException {
        // List of resources to copy
        List<String> resources =
                List.of(
                        // Fonts
                        "fonts/MightySouly-lxggD.ttf",

                        // Shaders
                        "shaders/entity_fragment.glsl",
                        "shaders/entity_vertex.glsl",
                        "shaders/terrain_fragment.glsl",
                        "shaders/terrain_vertex.glsl",

                        // Texture
                        "Texture/cartoonFlowers.jpg",
                        "Texture/cartoonGrass.jpg",
                        "Texture/cartoonSand.jpg",
                        "Texture/cartoonWater.jpg",
                        "Texture/Default.png");

        // Ensure resource directory exists
        File baseDir = new File(RESOURCE_DIR);
        if (!baseDir.exists()) {
            baseDir.mkdirs();
        }

        // Copy resources if not already copied
        for (String resource : resources) {
            copyResourceIfNeeded(resource);
        }

        // Update Consts paths
        Consts.updatePaths(RESOURCE_DIR);
    }

    private static void copyResourceIfNeeded(String resourcePath) throws IOException {
        File targetFile = new File(RESOURCE_DIR + resourcePath);

        // Skip copying if the file already exists
        if (targetFile.exists()) {
            return;
        }

        // Create parent directories if necessary
        targetFile.getParentFile().mkdirs();

        // Copy the resource
        try (InputStream inputStream =
                        ResourceCloner.class.getResourceAsStream("/" + resourcePath);
                OutputStream outputStream = new FileOutputStream(targetFile)) {

            if (inputStream == null) {
                throw new FileNotFoundException("Resource not found in JAR: " + resourcePath);
            }

            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }
}
