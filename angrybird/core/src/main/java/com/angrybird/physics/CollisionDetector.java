package com.angrybird.physics;

import com.badlogic.gdx.physics.box2d.*;
import com.angrybird.entities.Bird;
import com.angrybird.entities.Pig;
import com.angrybird.entities.GameObject;

public class CollisionDetector implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        GameObject objectA = (GameObject) bodyA.getUserData();
        GameObject objectB = (GameObject) bodyB.getUserData();

        if (objectA instanceof Bird) {
            handleBirdCollision((Bird) objectA, objectB);
        } else if (objectB instanceof Bird) {
            handleBirdCollision((Bird) objectB, objectA);
        }
    }

    private void handleBirdCollision(Bird bird, GameObject other) {
        if (other instanceof Pig) {
            // Bird hits pig
            float damage = calculateCollisionDamage(bird.getBody(), other.getBody());
            ((Pig) other).damage(damage);
        } else {
            // Bird hits obstacle
            float damage = calculateCollisionDamage(bird.getBody(), other.getBody());
            other.damage(damage);
        }
    }

    private float calculateCollisionDamage(Body bodyA, Body bodyB) {
        float relativeVelocity = bodyA.getLinearVelocity()
            .sub(bodyB.getLinearVelocity()).len();
        return relativeVelocity * 0.5f;
    }

    @Override
    public void endContact(Contact contact) {}

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        float maxImpulse = 0f;
        for (float imp : impulse.getNormalImpulses()) {
            maxImpulse = Math.max(maxImpulse, imp);
        }

        if (maxImpulse > 1.0f) {
            Body bodyA = contact.getFixtureA().getBody();
            Body bodyB = contact.getFixtureB().getBody();

            GameObject objectA = (GameObject) bodyA.getUserData();
            GameObject objectB = (GameObject) bodyB.getUserData();

            if (objectA != null) objectA.damage(maxImpulse * 0.1f);
            if (objectB != null) objectB.damage(maxImpulse * 0.1f);
        }
    }
}
