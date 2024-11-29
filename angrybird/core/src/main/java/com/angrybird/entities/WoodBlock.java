package com.angrybird.entities;

import com.angrybird.core.AssetLoader;
import com.badlogic.gdx.physics.box2d.*;

public class WoodBlock extends GameObject {
    private static final float WOOD_DURABILITY = 2.0f;
    private static final float WOOD_DENSITY = 0.5f;

    public WoodBlock(World world, float x, float y, float width, float height) {
        super(world, x, y, width, height, "woodenblock1.png");
        this.health = WOOD_DURABILITY;
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
        fixtureDef.density = WOOD_DENSITY;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.2f;

        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setUserData(this);

        shape.dispose();
    }

    @Override
    public void damage(float amount) {
        health -= amount;
        if (health <= 0) {
            // Change texture to damaged state before destruction
            if (!isDestroyed) {
                sprite.setTexture(AssetLoader.getTexture("woodenblock2.png"));
            }
            destroy();
        }
    }
}
