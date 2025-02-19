package com.gordonfromblumberg.games.core.motion_animation.gravity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gordonfromblumberg.games.core.common.ui.ZoomByScrollListener;
import com.gordonfromblumberg.games.core.common.world.WorldRenderer;
import com.gordonfromblumberg.games.core.common.world.WorldScreen;

public class GravityScreen extends WorldScreen<GravityWorld> {
    public GravityScreen(SpriteBatch batch) {
        super(batch, new GravityWorld());

        color = new Color(0.18f, 0.18f, 0.22f, 1f);
    }

    @Override
    protected WorldRenderer<GravityWorld> createWorldRenderer() {
        return new GravityWorldRenderer(world, batch);
    }

    @Override
    protected void createUiRenderer() {
        uiRenderer = new GravityUIRenderer(batch, world, this::getViewCoords3);
        uiRenderer.addListener(new ZoomByScrollListener(worldRenderer.getCamera(), 1.3f, 0.1f, 10f));
    }
}
