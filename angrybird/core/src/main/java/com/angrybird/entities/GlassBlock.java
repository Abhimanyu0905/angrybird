package com.angrybird.entities;

import com.angrybird.core.AssetLoader;
import com.badlogic.gdx.physics.box2d.*;

public class GlassBlock extends GameObject {
    private static final float GLASS_DURABILITY = 1.0f;
    private static final float GLASS_DENSITY = 0.3f;

    public GlassBlock(World world, float x, float y, float width, float height) {
        super(world, x, y, width, height, "iceblock1.png");
        this.health = GLASS_DURABILITY;
    }

    @Override
    protected void createBody(World world, float x, float y, float width, float height) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = GLASS_DENSITY;
        fixtureDef.friction = 0.2f;
        fixtureDef.restitution = 0.3f;

        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setUserData(this);

        shape.dispose();
    }

    @Override
    public void damage(float amount) {
        health -= amount * 1.2f; // Glass takes more damage
        if (health <= 0) {
            if (!isDestroyed) {
                sprite.setTexture(AssetLoader.getTexture("iceblock2.png"));
            }
            destroy();
        }
    }
}
