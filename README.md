# Pot Pot Engine

A small 3D game engine in Java on LWJGL (OpenGL), extracted from [Pot Pot Golf](https://github.com/NoamFav/Pot_Pot_Golf).

## What's in it

| Package | Contents |
|---------|----------|
| `com.pot_pot_engine` | Game loop (`EngineManager`), window, camera, mouse input, shaders, OBJ/Assimp loader, audio, `Consts` config |
| `Entity` | Entities, models, materials, textures, scene |
| `Entity.Terrain` | Simplex noise heightmaps, terrain mesh generation, blend-map texturing, A* heightmap pathfinder |
| `Rendering` | Entity and terrain renderers |
| `Physics` | Ball-on-terrain physics (simple and full models) with RK2/RK4 solvers |
| `Utils` | Collision detection, transformations, resource loading |
| `AWT` | NanoVG GUI widgets: button, text field, text pane, title |
| `demo` | `Sandbox`: a procedural terrain you can fly around, and a starting point for a new game |

## Requirements

- Java 22+
- Maven

## Run the demo

```bash
mvn clean package
mvn exec:exec
```

Controls: WASD to move, Space / Shift for up / down, hold the right mouse button to look, Esc to quit.

> **Mac users:** add `-XstartOnFirstThread` as a JVM argument when running from an IDE.

## Making a game

Implement `ILogic` (`init`, `input`, `update`, `render`, `cleanUp`) and hand it to the engine:

```java
ResourceCloner.initializeResources();
WindowManager window = new WindowManager("My Game", 0, 0, true);
new EngineManager(window, new MyGame()).start();
```

`demo/SandboxLogic.java` shows a full setup. Engine settings (terrain size, noise, camera, physics) live in `Consts`.

## Credits

- Terrain textures: upklyak (Freepik)
- Font: Mighty Souly by graphicsauceco
- Original engine authors (Pot Pot Golf): Noam Favier, Ole Rotteveel, Beatrice Boccolari, Bruna Calvancati Lauro, Evi Levels, Ahmet Akman, Nehir Sarihan

## License

MIT, see [LICENSE](LICENSE).
