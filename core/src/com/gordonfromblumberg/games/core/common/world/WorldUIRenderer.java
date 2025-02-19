package com.gordonfromblumberg.games.core.common.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.gordonfromblumberg.games.core.common.debug.DebugOptions;
import com.gordonfromblumberg.games.core.common.factory.AbstractFactory;
import com.gordonfromblumberg.games.core.common.log.LogManager;
import com.gordonfromblumberg.games.core.common.log.Logger;
import com.gordonfromblumberg.games.core.common.screens.UIRenderer;
import com.gordonfromblumberg.games.core.common.ui.SaveLoadWindow;
import com.gordonfromblumberg.games.core.common.ui.UpdatableLabel;
import com.gordonfromblumberg.games.core.common.utils.Assets;
import com.gordonfromblumberg.games.core.common.utils.ConfigManager;

import java.util.function.Supplier;

import static com.gordonfromblumberg.games.core.common.utils.StringUtils.floatToString;

public class WorldUIRenderer<T extends World> extends UIRenderer {
    private static final Logger log = LogManager.create(WorldUIRenderer.class);
    private static final String SHOW_COORDS_DEBUG_PRF = "debug.coords.show";

    private final WorldCameraParams worldCameraParams = new WorldCameraParams();
    private final Supplier<Vector3> viewCoords;

    protected final T world;

    private Window coordsDebugWindow;

    public WorldUIRenderer(SpriteBatch batch, T world, Supplier<Vector3> viewCoords) {
        super(batch);
        log.info("WorldUIRenderer constructor for " + getClass().getSimpleName());
        this.world = world;
        this.viewCoords = viewCoords;

        this.keyBindings.bind(Input.Keys.ESCAPE, "Esc", () -> {
            world.pause();
            windowManager.escape();
        });

        addPauseListener();

        if (DebugOptions.DEBUG) {
            final AssetManager assets = Assets.manager();
            final Skin uiSkin = assets.get("ui/uiskin.json", Skin.class);
            coordsDebugWindow = createCoordsDebugWindow(uiSkin);
            stage.addActor(coordsDebugWindow);
            keyBindings.bind(Input.Keys.F9, "showOrHideCoordsDebug", this::showOrHideCoordsDebug);
        }
    }

    WorldCameraParams getWorldCameraParams() {
        return worldCameraParams;
    }

    protected void addSaveLoadWindow(int bufferSize, String defaultSaveDir, String saveExtWoPoint) {
        final Skin uiSkin = Assets.manager().get("ui/uiskin.json", Skin.class);
        SaveLoadWindow saveWindow = createSaveLoadWindow(bufferSize, false, uiSkin, defaultSaveDir, saveExtWoPoint);
        String saveWindowName = "save-window";
        saveWindow.setName(saveWindowName);
        windowManager.register(saveWindowName, saveWindow);
        keyBindings.bind(Input.Keys.F5, saveWindowName, () -> {
            world.pause();
            windowManager.toggle(saveWindowName);
        });

        SaveLoadWindow loadWindow = createSaveLoadWindow(bufferSize, true, uiSkin, defaultSaveDir, saveExtWoPoint);
        String loadWindowName = "load-window";
        loadWindow.setName(loadWindowName);
        windowManager.register(loadWindowName, loadWindow);
        keyBindings.bind(Input.Keys.F6, loadWindowName, () -> {
            world.pause();
            windowManager.toggle(loadWindowName);
        });
    }

    protected SaveLoadWindow createSaveLoadWindow(int bufferSize, boolean load, Skin skin, String defaultSaveDir, String saveExt) {
        ConfigManager config = AbstractFactory.getInstance().configManager();
        SaveLoadWindow window = new SaveLoadWindow(
                skin,
                bufferSize,
                config.getString("saves.dir", defaultSaveDir),
                saveExt,
                load,
                load ? world::load : world::save
        );

        window.setWidth(config.getFloat("ui.saveload.width"));
        window.setHeight(config.getFloat("ui.saveload.height"));

        return window;
    }

    protected void addPauseListener() {
        keyBindings.bind(Input.Keys.SPACE, "pause", () -> {
            if (!world.isPaused()) world.pause();
            else if (!windowManager.isOpened()) world.resetPause();
        });
    }

    private void showOrHideCoordsDebug() {
        coordsDebugWindow.setVisible(!coordsDebugWindow.isVisible());
        Preferences config = AbstractFactory.getInstance().configManager().getConfigPreferences();
        config.putBoolean(SHOW_COORDS_DEBUG_PRF, coordsDebugWindow.isVisible());
        config.flush();
    }

    private Window createCoordsDebugWindow(Skin skin) {
        final Window window = new Window("Coords debug", skin);
        window.setName("coords_debug");
        window.setWidth(250f);
        window.setHeight(200f);
        window.setY(viewport.getWorldHeight());
        window.add("Camera pos");
        window.add(new UpdatableLabel(skin, () ->
                floatToString(worldCameraParams.position.x, 2) + ", " + floatToString(worldCameraParams.position.y, 2)));

        window.row();
        window.add("Zoom");
        window.add(new UpdatableLabel(skin, () -> floatToString(worldCameraParams.zoom, 3)));

        window.row();
        window.add("Screen");
        window.add(new UpdatableLabel(skin, () -> Gdx.input.getX() + ", " + Gdx.input.getY()));

        window.row();
        window.add("Viewport");
        window.add(new UpdatableLabel(skin, () -> {
            Vector3 coords = viewCoords.get();
            return floatToString(coords.x, 2) + ", " + floatToString(coords.y, 2);
        }));

        window.row();
        window.add("World");
        window.add(new UpdatableLabel(skin,
                () -> floatToString(world.getMouseX(), 2) + ", " + floatToString(world.getMouseY(), 2)));

        Preferences config = AbstractFactory.getInstance().configManager().getConfigPreferences();
        if (!config.getBoolean(SHOW_COORDS_DEBUG_PRF, true)) window.setVisible(false);
        return window;
    }
}
