package com.pot_pot_engine;

import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.function.BiFunction;

/**
 * The consts class. This class is responsible for the constants of the engine. All key settings are
 * stored here. Made to be easily accessible and modifiable.
 */
public class Consts {

    public static String Title = "Pot Pot Engine"; // Title of the window (games can override it)

    public static final float REFERENCE_WIDTH =
            3840f; // Reference width for the GUI scaling (don't change)
    public static final float REFERENCE_HEIGHT =
            2160f; // Reference height for the GUI scaling (don't change)

    /* If too much lag, reduce the power to 8 (256 meters, and 1024 vertices).
    Or reduce the number of vertices to either power + 1 or power + 0.
    (less detail but more performance). */
    public static final int POWER = 9; // Power of 2 for the terrain size
    public static final float SIZE_X =
            (float) Math.pow(2, POWER); // Size of the terrain in the x direction
    public static final float SIZE_Z =
            (float) Math.pow(2, POWER); // Size of the terrain in the z direction
    public static final int VERTEX_COUNT =
            (int)
                    Math.pow(
                            2,
                            POWER
                                    + 2); // Number of vertices in the terrain (Higher = more detail
                                          // but also more performance heavy)
    public static final float TREE_SIZE = 1.5f; // Radius used for collisions with tree entities
    public static final float MAX_HEIGHT = SIZE_X / 2f; // Maximum height reachable by the camera

    public static final int MAX_TEXTURES = 7; // Maximum number of terrain textures (don't change)

    // Configuration for the terrain generation
    // Change at your own risk (maybe unstable)
    // The recommended values are the default values.
    // Scale = 0.005, Octaves = 11, Persistence = 0.4, Amplitude = 1
    public static final double SCALE = 0.005; // Used for scaling the terrain with simplex noise
    public static final int OCTAVES =
            11; // Number of octaves used in simplex noise (higher = more detail)
    public static final double PERSISTENCE =
            0.4; // Used to determine how much each octave contributes to the overall shape (higher
                 // = more detail)
    public static final double AMPLITUDE =
            1; // Used for increasing the height of the terrain (higher = more height)

    public static final long NANOSECOND = 1000000000L; // 1 second in nanoseconds (don't change)
    public static final float FRAME_CAP =
            5000f; // Maximum frame rate (reduce for capping the frame rate)
    public static final float FRAMERATE = 1.0f / FRAME_CAP; // maximum frame rate in seconds

    // Configuration for the lighting
    public static final int MAX_POINT_LIGHTS = 5; // Maximum number of point lights

    // Configuration for the camera
    public static final float FOV = (float) Math.toRadians(60); // Field of view
    public static final float Z_NEAR = 0.1f; // Near plane (Used for clipping)
    public static final float Z_FAR = 10000.0f; // Far plane (Used for freeing up resources)
    public static final float SPECULAR_POWER = 10f; // Specular power (Higher = more shiny)

    // Configuration for the first-person camera
    public static final float PLAYER_HEIGHT = 1.75f; // Height of the camera above the terrain (meters)
    public static final float MOUSE_SENSITIVITY =
            0.2f; // Mouse sensitivity (Higher = more sensitive)
    public static final float CAMERA_MOVEMENT_SPEED = 3f; // camera movement speed (Higher = faster)

    // Make the scene darker or brighter with the ambient light
    public static final Vector4f DEFAULT_COLOR =
            new Vector4f(1f, 1f, 1f, 1f); // Default color of the object (don't change)
    public static final Vector3f AMBIENT_LIGHT =
            new Vector3f(0.8f, 0.8f, 0.8f); // Ambient light color (higher = brighter)

    // When true, the terrain uses HEIGHT_FUNCTION instead of the simplex noise heightmap.
    public static boolean USE_HEIGHT_FUNCTION = false;

    public static final BiFunction<Float, Float, Float> HEIGHT_FUNCTION =
            (x, z) -> (float) (0.5 * Math.sin((x + z) / 10) + 1); // Height function for the terrain

    // Terrain below this height is painted as sand in the blend map and treated as a wall by the
    // pathfinder.
    public static final float SAND_HEIGHT = 0.2f; // Height of the sand (meters)

    public static final float MAX_TERRAIN_HEIGHT = 10f; // Height of the terrain (meters)
    /* The max height is only a scaling factor for the simplex noise.
    It is not the actual height of the terrain has simplex doesn't have to be 1 at any point.
    The terrains use octaves simplex generations, so the height is a sum of multiple simplex noise functions.
    This means that the actual height will be lower than the max height.
    From test, the max height tends to be around +/- 0.8,
    so the actual height would be +/- 8 meters when left untouched. */

    // Configuration for the ball physics
    // Gravity is set to the real-world value, but can be changed for a more arcade feel.
    public static final float GRAVITY = 9.81f; // Gravity constant (m/s^2)

    // The requirement of friction is tiny,
    // so on the simplex noise terrain, the ball will have trouble climbing the hills.
    public static final float KINETIC_FRICTION_GRASS =
            0.08f; // Friction of the grass (recommended: 0.2)
    public static final float STATIC_FRICTION_GRASS =
            0.2f; // Friction of the grass (recommended: 0.4)

    public static final float KINETIC_FRICTION_SAND =
            0.2f; // Friction of the sand (recommended: 0.4)
    public static final float STATIC_FRICTION_SAND =
            0.4f; // Friction of the sand (recommended: 0.8)

    // Resources for the engine
    // Do not change, except if you have full knowledge of the path you want to change
    public static String DEFAULT_TEXTURE;
    public static String HEIGHTMAP;

    public static class TerrainTexture {
        public static String SAND;
        public static String GRASS;
        public static String FAIRWAY;
        public static String WATER;
    }

    public static class GUI {
        public static String FONT;
    }

    /**
     * Updates paths to resources dynamically based on the base directory.
     *
     * @param baseDir The base directory where resources are located.
     */
    public static void updatePaths(String baseDir) {
        DEFAULT_TEXTURE = baseDir + "Texture/Default.png";
        HEIGHTMAP = baseDir + "Texture/heightmap.png";

        TerrainTexture.SAND = baseDir + "Texture/cartoonSand.jpg";
        TerrainTexture.GRASS = baseDir + "Texture/cartoonFlowers.jpg";
        TerrainTexture.FAIRWAY = baseDir + "Texture/cartoonGrass.jpg";
        TerrainTexture.WATER = baseDir + "Texture/cartoonWater.jpg";

        GUI.FONT = baseDir + "fonts/MightySouly-lxggD.ttf";
    }
}
