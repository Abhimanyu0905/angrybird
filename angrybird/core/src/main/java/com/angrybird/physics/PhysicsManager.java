package com.angrybird.physics;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.Vector2;

public class PhysicsManager {
    private World world;
    private static final float PIXELS_TO_METERS = 100f;
    private static final float TIME_STEP = 1/60f;
    private static final int VELOCITY_ITERATIONS = 6;
    private static final int POSITION_ITERATIONS = 2;

    public PhysicsManager() {
        world = new World(new Vector2(0, -9.81f), true);
        world.setContactListener(new CollisionDetector());
    }

    public void update() {
        world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
    }

    public World getWorld() {
        return world;
    }

    public static float convertToMeters(float pixels) {
        return pixels / PIXELS_TO_METERS;
    }

    public static float convertToPixels(float meters) {
        return meters * PIXELS_TO_METERS;
    }

    public void dispose() {
        if (world != null) {
            world.dispose();
        }
    }
}
