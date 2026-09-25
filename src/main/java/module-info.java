module com.pot_pot_engine {

    requires org.lwjgl.glfw;
    requires org.lwjgl.opengl;
    requires org.joml;
    requires org.apache.logging.log4j;
    requires org.lwjgl.stb;
    requires org.lwjgl.assimp;
    requires java.desktop;
    requires org.lwjgl.nanovg;
    requires org.lwjgl.openal;
    requires org.jetbrains.annotations;

    exports com.pot_pot_engine;
    exports com.pot_pot_engine.AWT;
    exports com.pot_pot_engine.Entity;
    exports com.pot_pot_engine.Entity.Terrain;
    exports com.pot_pot_engine.Physics;
    exports com.pot_pot_engine.Rendering;
    exports com.pot_pot_engine.Utils;
    exports com.pot_pot_engine.demo;
}
