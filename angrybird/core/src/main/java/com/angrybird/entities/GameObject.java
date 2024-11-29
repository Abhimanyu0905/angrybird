package com.angrybird.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.Vector2;
import com.angrybird.core.AssetLoader;
import com.angrybird.physics.PhysicsManager;

public abstract class GameObject {
    protected Body body;
    protected Sprite sprite;
    protected boolean isDestroyed;
    protected float health;
    protected World world;

    public GameObject(World world, float x, float y, float width, float height, String textureName) {
        this.world = world;
        this.isDestroyed = false;
        createSprite(textureName, width, height);
        createBody(world, x, y, width, height);
    }

    protected void createSprite(String textureName, float width, float height) {
        sprite = new Sprite(AssetLoader.getTexture(textureName));
        sprite.setSize(width, height);
        sprite.setOriginCenter();
    }

    protected abstract void createBody(World world, float x, float y, float width, float height);

    public void render(SpriteBatch batch) {
        if (!isDestroyed && sprite != null && body != null) {
            Vector2 position = body.getPosition();
            float x = PhysicsManager.convertToPixels(position.x) - sprite.getWidth()/2;
            float y = PhysicsManager.convertToPixels(position.y) - sprite.getHeight()/2;
            float rotation = (float) Math.toDegrees(body.getAngle());

            sprite.setPosition(x, y);
            sprite.setRotation(rotation);
            sprite.draw(batch);
        }
    }

    public void damage(float amount) {
        health -= amount;
        if (health <= 0) {
            destroy();
        }
    }

    public void destroy() {
        isDestroyed = true;
    }

    public Body getBody() { return body; }
    public boolean isDestroyed() { return isDestroyed; }
}
