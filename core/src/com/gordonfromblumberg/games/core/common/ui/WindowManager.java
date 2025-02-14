package com.gordonfromblumberg.games.core.common.ui;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.utils.ObjectMap;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class WindowManager {
    private final Stage stage;
    private float duration = 0.3f;
    private String defaultWindow;

    private final ObjectMap<String, Dialog> windows = new ObjectMap<>();
    private Dialog previous;
    private Dialog current;
    private boolean showPrevOnClose;

    public WindowManager(Stage stage) {
        this(stage, "default");
    }

    public WindowManager(Stage stage, String defaultWindow) {
        this.stage = stage;
        this.defaultWindow = defaultWindow;
    }

    public void register(String name, Dialog window) {
        windows.put(name, window);
    }

    public void open(String name, boolean showPrevOnClose) {
        Dialog window = windows.get(name);
        if (window == null) return;

        this.showPrevOnClose = showPrevOnClose;
        previous = current;
        if (current != null) {
            closeCurrent(window);
        } else {
            open(window);
        }
    }

    /**
     * Closes current window and opens previous if {@link #showPrevOnClose} == true
     */
    public void close() {
        if (current == null) return;

        closeCurrent(showPrevOnClose ? previous : null);
    }

    /**
     * Invoke {@link #close()} if current is not null, open default window otherwise
     */
    public void escape() {
        if (current != null) {
            close();
        } else {
            open(defaultWindow, false);
        }
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    private void open(Dialog window) {
        current = window;
        if (window != null) {
            window.show(stage, sequence(alpha(0), fadeIn()));
        }
    }

    private void closeCurrent(Dialog open) {
        current.hide(sequence(fadeOut(),
                run(() -> open(open))));
    }

    private Action fadeOut() {
        return Actions.fadeOut(duration, Interpolation.fade);
    }

    private Action fadeIn() {
        return Actions.fadeIn(duration, Interpolation.fade);
    }
}
