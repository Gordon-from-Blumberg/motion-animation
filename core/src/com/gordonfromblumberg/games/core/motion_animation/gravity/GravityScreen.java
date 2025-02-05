package com.gordonfromblumberg.games.core.motion_animation.gravity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gordonfromblumberg.games.core.common.world.WorldRenderer;
import com.gordonfromblumberg.games.core.common.world.WorldScreen;

public class GravityScreen extends WorldScreen<GravityWorld> {
    public GravityScreen(SpriteBatch batch) {
        super(batch, new GravityWorld());

        color = new Color(0.2f, 0.2f, 0.25f, 1f);
    }

    @Override
    protected WorldRenderer<GravityWorld> createWorldRenderer() {
        return new GravityWorldRenderer(world, batch);
    }
}
