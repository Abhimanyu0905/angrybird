package com.angrybird.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.math.Vector2;

public class YellowBird extends Bird {
    private static final float SIZE = BASE_SIZE * 0.9f;  // Slightly smaller
    private boolean speedBoostActive = false;
    private static final float SPEED_MULTIPLIER = 2.0f;

    public YellowBird(World world, float x, float y) {
        super(world, x, y, SIZE, "yellowbird.png");
        this.power = 0.8f;  // Less initial power but faster
    }

    @Override
    public void useSpecialPower() {
        if (!speedBoostActive && isLaunched && !isDestroyed) {
            // Boost speed in current direction
            Vector2 velocity = body.getLinearVelocity();
            float currentSpeed = velocity.len();
            velocity.nor().scl(currentSpeed * SPEED_MULTIPLIER);
            body.setLinearVelocity(velocity);

            // Increase power during speed boost
            power *= 1.5f;
            speedBoostActive = true;
        }
    }

    public boolean isSpeedBoostActive() {
        return speedBoostActive;
    }
}
