package com.angrybird.entities;

import com.badlogic.gdx.physics.box2d.World;

public class RedBird extends Bird {
    private static final float SIZE = BASE_SIZE;

    public RedBird(World world, float x, float y) {
        super(world, x, y, SIZE, "redbird.png");
        this.power = 1.0f;  // Standard power
    }

    @Override
    public void useSpecialPower() {
        // Red bird has no special power but hits harder
        if (isLaunched && !isDestroyed) {
            this.power *= 1.5f;
        }
    }
}
