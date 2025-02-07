package com.gordonfromblumberg.games.core.motion_animation.gravity;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.gordonfromblumberg.games.core.common.world.WorldUIRenderer;

import java.util.function.Supplier;

public class GravityUIRenderer extends WorldUIRenderer<GravityWorld> {

    public GravityUIRenderer(SpriteBatch batch, GravityWorld world, Supplier<Vector3> viewCoords) {
        super(batch, world, viewCoords);

        stage.addListener(new InputListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == Input.Keys.F4) {
                    world.loadContinue();
                    return true;
                }
                return false;
            }
        });
    }
}
