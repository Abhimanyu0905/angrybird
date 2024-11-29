package com.angrybird.entities;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.Vector2;
import com.angrybird.physics.PhysicsManager;

public abstract class Bird extends GameObject {
    protected boolean isLaunched;
    protected float power;
    protected static final float BASE_SIZE = 40f; // Reduced size for the bird
    private static final float GROUND_LEVEL = 100f; // Ground level in pixels

    public Bird(World world, float x, float y, float size, String textureName) {
        super(world, x, y, size, size, textureName);
        this.isLaunched = false;
        this.power = 1.0f;
        this.health = 1.0f;
    }

    @Override
    protected void createBody(World world, float x, float y, float width, float height) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(
            PhysicsManager.convertToMeters(x),
            PhysicsManager.convertToMeters(y)
        );

        CircleShape shape = new CircleShape();
        shape.setRadius(PhysicsManager.convertToMeters(width / 2));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.2f;
        fixtureDef.restitution = 0.4f;

        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setUserData(this);

        shape.dispose();
    }

    public void updatePosition() {
        if (body != null) {
            Vector2 position = body.getPosition();
            float groundLevelInMeters = PhysicsManager.convertToMeters(GROUND_LEVEL);
            if (position.y < groundLevelInMeters) {
                // Clamp the Y position to ground level if it goes below
                body.setTransform(position.x, groundLevelInMeters, body.getAngle());
            }
        }
    }

    public abstract void useSpecialPower();

    public void launch(Vector2 force) {
        if (!isLaunched && body != null) {
            body.setAwake(true);
            body.applyLinearImpulse(force, body.getWorldCenter(), true);
            isLaunched = true;
        }
    }

    public boolean isLaunched() {
        return isLaunched;
    }

    public float getPower() {
        return power;
    }
}
