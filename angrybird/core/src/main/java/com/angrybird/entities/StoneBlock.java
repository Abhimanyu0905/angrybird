package com.angrybird.entities;

import com.angrybird.core.AssetLoader;
import com.badlogic.gdx.physics.box2d.*;

public class StoneBlock extends GameObject {
    private static final float STONE_DURABILITY = 3.0f;
    private static final float STONE_DENSITY = 0.8f;

    public StoneBlock(World world, float x, float y, float width, float height) {
        super(world, x, y, width, height, "stoneblock1.png");
        this.health = STONE_DURABILITY;
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
        fixtureDef.density = STONE_DENSITY;
        fixtureDef.friction = 0.6f;
        fixtureDef.restitution = 0.1f;

        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setUserData(this);

        shape.dispose();
    }

    @Override
    public void damage(float amount) {
        health -= amount * 0.8f; // Stone takes less damage
        if (health <= 0) {
            if (!isDestroyed) {
                sprite.setTexture(AssetLoader.getTexture("stoneblock2.png"));
            }
            destroy();
        }
    }
}
