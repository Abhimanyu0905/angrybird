package com.angrybird.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;

public class Slingshot {
    private final Texture texture;
    private final Vector2 position;
    private final Vector2 anchor;
    private Vector2 pullPosition;
    private final float SLING_WIDTH = 50f;
    private final float SLING_HEIGHT = 100f;
    private final float MAX_PULL_DISTANCE = 100f;
    private ShapeRenderer shapeRenderer;
    private Bird currentBird;

    // Slingshot head position
    private Vector2 headPosition;

    public Slingshot(Texture slingshotTexture, float x, float y) {
        this.texture = slingshotTexture;
        this.position = new Vector2(x, y);
        this.anchor = new Vector2(x + SLING_WIDTH / 2, y + SLING_HEIGHT * 0.75f);
        this.pullPosition = anchor.cpy();
        this.shapeRenderer = new ShapeRenderer();
        this.headPosition = new Vector2(x + SLING_WIDTH / 2, y + SLING_HEIGHT);  // Head of the slingshot
    }

    public void render(SpriteBatch batch) {
        // Draw slingshot sprite
        batch.draw(texture, position.x, position.y, SLING_WIDTH, SLING_HEIGHT);
    }

    public void renderSling(SpriteBatch batch) {
        // Draw the rubber band (pulling mechanism)
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BROWN);
        shapeRenderer.line(anchor.x, anchor.y, pullPosition.x, pullPosition.y);
        shapeRenderer.end();
    }

    public Vector2 getPullPosition() {
        return pullPosition;
    }

    public void setPullPosition(float x, float y) {
        Vector2 newPull = new Vector2(x, y);
        Vector2 direction = newPull.cpy().sub(anchor);
        float distance = direction.len();

        if (distance > MAX_PULL_DISTANCE) {
            direction.nor().scl(MAX_PULL_DISTANCE);
            pullPosition = anchor.cpy().add(direction);
        } else {
            pullPosition = newPull;
        }
    }

    public Vector2 getAnchor() {
        return anchor;
    }

    public Vector2 getHeadPosition() {
        return headPosition;  // Return the slingshot head position
    }

    public void setCurrentBird(Bird bird) {
        this.currentBird = bird;
        // Teleport the bird to the head of the slingshot
        if (currentBird != null) {
            currentBird.getBody().setTransform(headPosition.x, headPosition.y, 0);  // Set bird to slingshot head
        }
    }

    public float getLaunchForce(Vector2 releasePoint) {
        return anchor.dst(releasePoint) / MAX_PULL_DISTANCE;
    }

    public Vector2 getLaunchVelocity(Vector2 releasePoint) {
        Vector2 direction = anchor.cpy().sub(releasePoint).nor();
        float force = getLaunchForce(releasePoint);
        return direction.scl(force * 20f); // Adjust multiplier for desired launch speed
    }

    public boolean isWithinRange(float x, float y) {
        return new Vector2(x, y).dst(anchor) <= MAX_PULL_DISTANCE;
    }

    public void launch() {
        if (currentBird != null) {
            Vector2 velocity = getLaunchVelocity(pullPosition);
            currentBird.launch(velocity);
            pullPosition.set(anchor);  // Reset the pull position after launch
        }
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}
