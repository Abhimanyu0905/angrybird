package com.angrybird.entities;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class BlackBird extends Bird {
    private static final float SIZE = BASE_SIZE * 1.1f;  // Slightly larger
    private boolean exploded = false;
    private static final float EXPLOSION_RADIUS = 3.0f;
    private static final float EXPLOSION_FORCE = 10.0f;

    public BlackBird(World world, float x, float y) {
        super(world, x, y, SIZE, "blackbird.png");
        this.power = 1.2f;
    }

    @Override
    public void useSpecialPower() {
        if (!exploded && isLaunched && !isDestroyed) {
            explode();
        }
    }

    private void explode() {
        if (body == null) return;

        Vector2 explosionCenter = body.getPosition();
        Array<Body> bodiesToAffect = new Array<>();

        // Query the world for bodies within explosion radius
        world.QueryAABB(
            fixture -> {
                Body body = fixture.getBody();
                if (body != this.body) {
                    Vector2 bodyCenter = body.getPosition();
                    float distance = bodyCenter.dst(explosionCenter);
                    if (distance <= EXPLOSION_RADIUS) {
                        bodiesToAffect.add(body);
                    }
                }
                return true;
            },
            explosionCenter.x - EXPLOSION_RADIUS,
            explosionCenter.y - EXPLOSION_RADIUS,
            explosionCenter.x + EXPLOSION_RADIUS,
            explosionCenter.y + EXPLOSION_RADIUS
        );

        // Apply explosion force to affected bodies
        for (Body affectedBody : bodiesToAffect) {
            Vector2 bodyCenter = affectedBody.getPosition();
            Vector2 explosionDirection = bodyCenter.cpy().sub(explosionCenter);
            float distance = explosionDirection.len();

            if (distance == 0) continue;

            float forceMagnitude = EXPLOSION_FORCE * (1 - distance/EXPLOSION_RADIUS);
            explosionDirection.nor().scl(forceMagnitude);

            affectedBody.applyLinearImpulse(
                explosionDirection,
                bodyCenter,
                true
            );

            // Damage nearby objects
            GameObject gameObject = (GameObject)affectedBody.getUserData();
            if (gameObject != null) {
                float damage = forceMagnitude * power;
                gameObject.damage(damage);
            }
        }

        exploded = true;
        destroy();  // Destroy the bird after explosion
    }

    public boolean hasExploded() {
        return exploded;
    }
}
