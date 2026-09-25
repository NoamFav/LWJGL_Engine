package com.pot_pot_engine.demo;

import com.pot_pot_engine.Camera;
import com.pot_pot_engine.Consts;
import com.pot_pot_engine.EngineManager;
import com.pot_pot_engine.Entity.Entity;
import com.pot_pot_engine.Entity.Material;
import com.pot_pot_engine.Entity.SceneManager;
import com.pot_pot_engine.Entity.Terrain.BlendMapTerrain;
import com.pot_pot_engine.Entity.Terrain.HeightMap;
import com.pot_pot_engine.Entity.Terrain.Terrain;
import com.pot_pot_engine.Entity.Terrain.TerrainTexture;
import com.pot_pot_engine.Entity.Texture;
import com.pot_pot_engine.ILogic;
import com.pot_pot_engine.MouseInput;
import com.pot_pot_engine.ObjectLoader;
import com.pot_pot_engine.Rendering.RenderManager;
import com.pot_pot_engine.Utils.CollisionsDetector;
import com.pot_pot_engine.WindowManager;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Sandbox game logic: generates a simplex noise terrain with water and a free-fly camera.
 * WASD to move, Space / Shift for up / down, hold the right mouse button to look around, Esc to quit.
 */
public class SandboxLogic implements ILogic {

    private final SceneManager scene = new SceneManager();
    private final ObjectLoader loader = new ObjectLoader();
    private final HeightMap heightMap = new HeightMap();
    private final CollisionsDetector collisions = new CollisionsDetector();
    private final Camera camera = new Camera();
    private final Vector3f cameraInc = new Vector3f();

    private WindowManager window;
    private RenderManager renderer; // Created in init, once the engine has registered the window
    private MouseInput mouseInput;

    @Override
    public void init(MouseInput mouseInput) throws Exception {
        this.mouseInput = mouseInput;
        window = EngineManager.getWindow();
        renderer = new RenderManager();

        scene.setDefaultTexture(new Texture(loader.loadTexture(Consts.DEFAULT_TEXTURE)));
        scene.setTreePositions(new ArrayList<>()); // No trees, but the collision detector expects a list
        window.setAntiAliasing(true);
        window.setResized(false);
        window.setClearColor(0.529f, 0.808f, 0.922f, 0.0f);
        renderer.init();

        heightMap.createHeightMap(); // Also writes the blend map image used below
        createTerrain();

        camera.setPosition(0, heightMap.getHeight(new Vector3f()) + 10, 0);
    }

    /** Creates the land terrain and a flat water plane, textured through the generated blend map. */
    private void createTerrain() throws Exception {
        TerrainTexture sand = new TerrainTexture(loader.loadTexture(Consts.TerrainTexture.SAND));
        TerrainTexture grass = new TerrainTexture(loader.loadTexture(Consts.TerrainTexture.GRASS));
        TerrainTexture fairway = new TerrainTexture(loader.loadTexture(Consts.TerrainTexture.FAIRWAY));
        TerrainTexture water = new TerrainTexture(loader.loadTexture(Consts.TerrainTexture.WATER));
        TerrainTexture plain = new TerrainTexture(loader.loadTexture(Consts.DEFAULT_TEXTURE));
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture(Consts.HEIGHTMAP));

        // The shader expects MAX_TEXTURES slots, one per blend map color band
        List<TerrainTexture> landTextures = new ArrayList<>(List.of(sand, grass, fairway, plain, plain, plain, plain));
        List<TerrainTexture> waterTextures = new ArrayList<>(Collections.nCopies(Consts.MAX_TEXTURES, water));

        Vector3f origin = new Vector3f(-Consts.SIZE_X / 2, 0, -Consts.SIZE_Z / 2);
        Terrain land = new Terrain(origin, loader, new Material(new Vector4f(0, 0, 0, 0), 0.1f),
                new BlendMapTerrain(landTextures), blendMap, false);
        Terrain ocean = new Terrain(origin, loader, new Material(new Vector4f(0, 0, 0, 0), 0.1f),
                new BlendMapTerrain(waterTextures), blendMap, true);
        ocean.getModel().getMaterial().setDisableCulling(true);

        scene.addTerrain(land);
        scene.addTerrain(ocean);
    }

    @Override
    public void input() {
        if (window.is_keyPressed(GLFW.GLFW_KEY_ESCAPE)) {
            GLFW.glfwSetWindowShouldClose(window.getWindow(), true);
        }

        float moveSpeed = Consts.CAMERA_MOVEMENT_SPEED / Math.max(1, EngineManager.getFps());
        cameraInc.set(0, 0, 0);
        if (window.is_keyPressed(GLFW.GLFW_KEY_W)) cameraInc.z = -moveSpeed;
        if (window.is_keyPressed(GLFW.GLFW_KEY_S)) cameraInc.z = moveSpeed;
        if (window.is_keyPressed(GLFW.GLFW_KEY_A)) cameraInc.x = -moveSpeed;
        if (window.is_keyPressed(GLFW.GLFW_KEY_D)) cameraInc.x = moveSpeed;
        if (window.is_keyPressed(GLFW.GLFW_KEY_SPACE)) cameraInc.y = moveSpeed;
        if (window.is_keyPressed(GLFW.GLFW_KEY_LEFT_SHIFT)) cameraInc.y = -moveSpeed;

        if (mouseInput.isRightButtonPressed()) {
            Vector2f rotVec = mouseInput.getDisplayVec();
            camera.moveRotation(rotVec.x * Consts.MOUSE_SENSITIVITY, rotVec.y * Consts.MOUSE_SENSITIVITY, 0);
        }
    }

    @Override
    public void update() {
        camera.movePosition(
                cameraInc.x * Consts.CAMERA_MOVEMENT_SPEED,
                cameraInc.y * Consts.CAMERA_MOVEMENT_SPEED,
                cameraInc.z * Consts.CAMERA_MOVEMENT_SPEED);
        collisions.checkCollision(camera, cameraInc, heightMap, scene);

        for (Entity entity : scene.getEntities()) {
            renderer.processEntity(entity);
        }
        for (Terrain terrain : scene.getTerrains()) {
            renderer.processTerrain(terrain);
        }
    }

    @Override
    public void render() {
        renderer.clear();
        renderer.render(camera);
    }

    @Override
    public void cleanUp() {
        renderer.cleanup();
        loader.cleanUp();
    }
}
