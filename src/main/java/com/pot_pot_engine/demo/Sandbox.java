package com.pot_pot_engine.demo;

import com.pot_pot_engine.Consts;
import com.pot_pot_engine.EngineManager;
import com.pot_pot_engine.Utils.ResourceCloner;
import com.pot_pot_engine.WindowManager;

/**
 * Minimal example of running the engine: a procedural terrain you can fly around.
 * Use it as the starting point for a new game.
 */
public class Sandbox {

    public static void main(String[] args) throws Exception {
        ResourceCloner.initializeResources(); // Copy engine resources and set up Consts paths

        Consts.Title = "Pot Pot Engine - Sandbox";
        WindowManager window = new WindowManager(Consts.Title, 0, 0, true);
        new EngineManager(window, new SandboxLogic()).start();
    }
}
