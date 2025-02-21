package com.gordonfromblumberg.games.core.motion_animation.gravity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gordonfromblumberg.games.core.common.log.LogManager;
import com.gordonfromblumberg.games.core.common.log.Logger;
import com.gordonfromblumberg.games.core.common.ui.ButtonListWindow;
import com.gordonfromblumberg.games.core.common.utils.Assets;
import com.gordonfromblumberg.games.core.common.world.WorldUIRenderer;
import com.gordonfromblumberg.games.core.motion_animation.gravity.ui.SpaceBodyInfo;

import java.util.function.Supplier;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class GravityUIRenderer extends WorldUIRenderer<GravityWorld> {
    private static final Logger log = LogManager.create(GravityUIRenderer.class);

    private SpaceBodyInfo bodyInfo;

    public GravityUIRenderer(SpriteBatch batch, GravityWorld world, Supplier<Vector3> viewCoords) {
        super(batch, world, viewCoords);

        addSaveLoadWindow(1 << 14, "saves", "sav");

        final Skin skin = Assets.get("ui/uiskin.json", Skin.class);
        String mainMenu = "main-menu";
        windowManager.register(mainMenu, createMainMenu(skin, mainMenu));
        windowManager.setDefaultWindow(mainMenu);

        bodyInfo = new SpaceBodyInfo(skin);
    }

    @Override
    public void render(float dt) {
        if (world.underPointerChanged) {
            if (world.underPointer == null) {
                bodyInfo.hide();
            } else {
                if (bodyInfo.getStage() == null) {
                    bodyInfo.setSpaceBody(world.underPointer);
                    bodyInfo.show(stage);
                } else {
                    bodyInfo.hide(sequence(
                            run(() -> bodyInfo.setSpaceBody(world.underPointer)),
                            SpaceBodyInfo.showAction()));
                }
            }
        }

        super.render(dt);
    }

    private ButtonListWindow createMainMenu(Skin skin, String name) {
        ButtonListWindow window = new ButtonListWindow("Main menu", skin);
        window.setName(name);
        window.getButtonTable().defaults().minWidth(200f);
        window.button("Continue", (Runnable) () -> {
            windowManager.close();
            world.resetPause();
        });
        window.button("Save", (Runnable) () -> windowManager.open("save-window", false));
        window.button("Load", (Runnable) () -> windowManager.open("load-window", false));

        return window;
    }
}
