package com.gordonfromblumberg.games.core.motion_animation.gravity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gordonfromblumberg.games.core.common.factory.AbstractFactory;
import com.gordonfromblumberg.games.core.common.physics.AccelerationMovingStrategy;
import com.gordonfromblumberg.games.core.common.world.World;

public class GravityWorld extends World {
    private static final float DELAY = 1f / 60;
    private static final float G = AbstractFactory.getInstance().configManager().getFloat("gravity.gConstant");
    private static final Vector2 tempV2 = new Vector2();

    private final AccelerationMovingStrategy ams = new AccelerationMovingStrategy();

    final Array<SpaceBody> bodies = new Array<>();

    private float time;

    @Override
    public void initialize() {
        SpaceBody sb1 = new SpaceBody();
        sb1.mass = 1000f;
        sb1.setSize(40f);
        sb1.setPosition(700f, 200f);
        sb1.setColor(0.75f, 0.75f, 0.15f, 1f);
        sb1.setMovingStrategy(ams);
        
        SpaceBody sb2 = new SpaceBody();
        sb2.mass = 20f;
        sb2.setSize(20f);
        sb2.velocity.set(20f, 35f);
        sb2.setPosition(60f, 15f);
        sb2.setColor(0.5f, 0.3f, 0.25f, 1f);
        sb2.setMovingStrategy(ams);

        SpaceBody sb3 = new SpaceBody();
        sb3.mass = 1f;
        sb3.setSize(10f);
        sb3.velocity.set(10f, 60f);
        sb3.setPosition(50f, 50f);
        sb3.setColor(0.5f, 0.3f, 0.25f, 1f);
        sb3.setMovingStrategy(ams);

        SpaceBody sb4 = new SpaceBody();
        sb4.mass = 2f;
        sb4.setSize(10f);
        sb4.velocity.set(40f, 80f);
        sb4.setPosition(70f, 40f);
        sb4.setColor(0.5f, 0.3f, 0.25f, 1f);
        sb4.setMovingStrategy(ams);

        SpaceBody sb5 = new SpaceBody();
        sb5.mass = 10f;
        sb5.setSize(15f);
        sb5.velocity.set(0f, 50f);
        sb5.setPosition(900f, 200f);
        sb5.setColor(0.4f, 0.3f, 0.25f, 1f);
        sb5.setMovingStrategy(ams);

        bodies.add(sb1, sb2, sb3, sb4);
        bodies.add(sb5);
    }

    @Override
    public void update(float delta, float mouseX, float mouseY) {
        super.update(delta, mouseX, mouseY);

        if (!paused) {
            time += delta;

            if (time < DELAY) return;

            time = 0;

            final int count = bodies.size;
            final Vector2 temp = tempV2;
            final float g = G;
            for (int i = 0; i < count; ++i) {
                SpaceBody sb1 = bodies.get(i);
                for (int j = i + 1; j < count; ++j) {
                    SpaceBody sb2 = bodies.get(j);
                    temp.set(sb1.position).sub(sb2.position);
                    float r2 = temp.len2();
                    temp.nor();
                    sb2.acceleration.mulAdd(temp, g * sb1.mass / r2);
                    sb1.acceleration.mulAdd(temp, -g * sb2.mass / r2);
                }
            }

            for (SpaceBody sb : bodies) {
                sb.update(DELAY);
                sb.acceleration.setZero();
            }
        }
    }
}
