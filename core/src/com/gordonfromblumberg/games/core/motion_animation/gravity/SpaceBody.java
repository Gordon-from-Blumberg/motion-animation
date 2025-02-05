package com.gordonfromblumberg.games.core.motion_animation.gravity;

import com.gordonfromblumberg.games.core.common.model.PhysicsGameObject;

public class SpaceBody extends PhysicsGameObject {
    float mass;

    public SpaceBody() {
        setRegion("white-circle");
    }

    public void setSize(float size) {
        setSize(size, size);
    }
}
