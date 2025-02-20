package com.gordonfromblumberg.games.core.motion_animation.gravity.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.StringBuilder;
import com.gordonfromblumberg.games.core.motion_animation.gravity.SpaceBody;

import static com.gordonfromblumberg.games.core.common.utils.MathHelper.clampAngleDeg;

public class SpaceBodyInfo extends Window {
    private final StringBuilder sb = new StringBuilder();
    private final Label massLbl;
    private final Label positionLbl;
    private final Label velocityLbl;
    private final Label velocityAngleLbl;

    private SpaceBody spaceBody;

    public SpaceBodyInfo(Skin skin) {
        super("", skin);

        defaults().pad(5f);
        columnDefaults(0).align(Align.right);
        columnDefaults(1).align(Align.left);

        add("Mass");
        add(this.massLbl = new Label("", skin));

        row();
        add("Position");
        add(this.positionLbl = new Label("", skin));

        row();
        add("Velocity");
        add(this.velocityLbl = new Label("", skin));

        row();
        add("Velocity angle");
        add(this.velocityAngleLbl = new Label("", skin));
    }

    public void setSpaceBody(SpaceBody spaceBody) {
        this.spaceBody = spaceBody;

        sb.clear();
        sb.append(spaceBody.getMass());
        massLbl.setText(sb);

        sb.clear();
        sb.append(spaceBody.position.x).append("; ").append(spaceBody.position.y);
        positionLbl.setText(sb);

        sb.clear();
        sb.append(spaceBody.velocity.len());
        velocityLbl.setText(sb);

        sb.clear();
        sb.append(clampAngleDeg(spaceBody.velocity.angleDeg()));
        velocityAngleLbl.setText(sb);
    }
}
