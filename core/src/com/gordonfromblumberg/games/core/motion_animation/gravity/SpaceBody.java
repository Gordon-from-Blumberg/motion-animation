package com.gordonfromblumberg.games.core.motion_animation.gravity;

import com.badlogic.gdx.utils.Pool;
import com.gordonfromblumberg.games.core.common.model.PhysicsGameObject;

public class SpaceBody extends PhysicsGameObject {
    private static final Pool<SpaceBody> pool = new Pool<>() {
        @Override
        protected SpaceBody newObject() {
            return new SpaceBody();
        }
    };

    float mass;

    public SpaceBody() {
        setRegion("white-circle");
    }

    public static SpaceBody instance() {
        return pool.obtain();
    }

    public void setSize(float size) {
        setSize(size, size);
    }

    public float getMass() {
        return mass;
    }

    @Override
    public void reset() {
        super.reset();

        this.mass = 0;
    }

    @Override
    public void release() {
        pool.free(this);
    }
}
