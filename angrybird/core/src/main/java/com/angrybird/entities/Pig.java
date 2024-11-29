package com.angrybird.entities;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.Vector2;

public class Pig extends GameObject {
    private static final float BASE_SIZE = 50f;
    private final float size;
    private float hitPoints;

    public Pig(World world, float x, float y, float sizeMultiplier) {
        super(world, x, y, BASE_SIZE * sizeMultiplier, BASE_SIZE * sizeMultiplier, "pig.png");
        this.size = sizeMultiplier;
        this.hitPoints = 2.0f * sizeMultiplier; // Bigger pigs have more health
        this.health = hitPoints;
    }

    @Override
    protected void createBody(World world, float x, float y, float width, float height) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        CircleShape shape = new CircleShape();
        shape.setRadius(width / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0.3f;

        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setUserData(this);

        shape.dispose();
    }

    @Override
    public void damage(float amount) {
        health -= amount;

        // Visual feedback when hit (you can add animation here)
        if (health <= hitPoints / 2 && health > 0) {
            // Show damaged state
            sprite.setAlpha(0.7f);
        }

        if (health <= 0 && !isDestroyed) {
            destroy();
        }
    }

    public float getSize() {
        return size;
    }

    // Add impact damage to nearby objects when pig falls from height
    public void checkFallDamage() {
        if (body != null) {
            Vector2 velocity = body.getLinearVelocity();
            float fallSpeed = Math.abs(velocity.y);
            if (fallSpeed > 10) { // Threshold for fall damage
                damage(fallSpeed * 0.2f);
            }
        }
    }
}
