package com.gordonfromblumberg.games.core.motion_animation.gravity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.gordonfromblumberg.games.core.common.world.WorldUIRenderer;

import java.util.function.Supplier;

public class GravityUIRenderer extends WorldUIRenderer<GravityWorld> {

    public GravityUIRenderer(SpriteBatch batch, GravityWorld world, Supplier<Vector3> viewCoords) {
        super(batch, world, viewCoords);
    }
}
