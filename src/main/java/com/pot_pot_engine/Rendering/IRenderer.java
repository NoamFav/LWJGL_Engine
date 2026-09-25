package com.pot_pot_engine.Rendering;

import com.pot_pot_engine.Camera;
import com.pot_pot_engine.Entity.Model;

/**
 * The interface for the renderer.
 *
 * @param <T> The type of the renderer.
 */
public interface IRenderer<T> {
    void init() throws Exception;
    void render(Camera camera);
    void cleanup();

    void bind(Model model);
    void unbind();
    void prepare(T t, Camera camera);
}
