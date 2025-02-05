package com.gordonfromblumberg.games.core.common.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gordonfromblumberg.games.core.common.Main;
import com.gordonfromblumberg.games.core.common.log.LogManager;
import com.gordonfromblumberg.games.core.common.log.Logger;
import com.gordonfromblumberg.games.core.game_template.TemplateScreen;
import com.gordonfromblumberg.games.core.motion_animation.gravity.GravityScreen;

public class MainMenuScreen extends AbstractScreen {
    private static final Logger log = LogManager.create(MainMenuScreen.class);

    TextButton textButton;

    public MainMenuScreen(SpriteBatch batch) {
        super(batch);

        color = Color.FOREST;

        log.debug("Local storage path = " + Gdx.files.getLocalStoragePath());
        log.debug("External storage path = " + Gdx.files.getExternalStoragePath());
    }

    @Override
    protected void update(float delta) {
    }

    @Override
    protected void createUiRenderer() {
        super.createUiRenderer();

        final Skin uiSkin = assets.get("ui/uiskin.json", Skin.class);

        textButton = new TextButton("GRAVITY", uiSkin);
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                ConfigManager config = AbstractFactory.getInstance().configManager();
//                worldParams.save(config.getConfigPreferences());
//                config.flushPreferences();
                Main.getInstance().setScreen(new GravityScreen(batch));
            }
        });

        uiRenderer.rootTable.add(textButton);
    }
}
