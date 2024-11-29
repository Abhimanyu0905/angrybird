package com.angrybird.core;

import com.angrybird.entities.*;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Random;

public class LevelManager {
    private final World world;
    private Array<Bird> birds;
    private Array<Pig> pigs;
    private Array<GameObject> blocks;
    private int currentBirdIndex;
    private static final float GROUND_LEVEL = 100f; // Ground level in pixels
    private Random random;

    public LevelManager(World world) {
        this.world = world;
        this.birds = new Array<>();
        this.pigs = new Array<>();
        this.blocks = new Array<>();
        this.random = new Random();
        this.currentBirdIndex = 0;
    }

    public void render(SpriteBatch batch) {
        // Render birds
        for(Bird bird : birds) {
            if(bird != null && !bird.isDestroyed()) {
                bird.render(batch);
            }
        }

        // Render pigs
        for(Pig pig : pigs) {
            if(pig != null && !pig.isDestroyed()) {
                pig.render(batch);
            }
        }

        // Render blocks
        for(GameObject block : blocks) {
            if(block != null && !block.isDestroyed()) {
                block.render(batch);
            }
        }
    }

    public void loadLevel(int levelNumber) {
        clearLevel();
        generateBirds();

        switch(levelNumber) {
            case 1:
                createWoodenLevel();
                break;
            case 2:
                createIceLevel();
                break;
            case 3:
                createStoneLevel();
                break;
        }
    }

    private void generateBirds() {
        float startX = 90f; // Position of slingshot at x = 90
        float birdY = GROUND_LEVEL + 50f; // Bird Y position (can be adjusted)
        float spacing = 40f; // Space between birds (adjust as needed)

        // Generate 5 random birds
        for (int i = 0; i < 5; i++) {
            Bird bird = null;
            int birdType = random.nextInt(3);

            switch(birdType) {
                case 0:
                    bird = new RedBird(world, startX + (i * spacing), birdY);
                    break;
                case 1:
                    bird = new YellowBird(world, startX + (i * spacing), birdY);
                    break;
                case 2:
                    bird = new BlackBird(world, startX + (i * spacing), birdY);
                    break;
            }
            if (bird != null) {
                birds.add(bird);
            }
        }
    }

    private void createWoodenLevel() {
        addBlock(new WoodBlock(world, 500f, GROUND_LEVEL + 25f, 200f, 50f));
        // Other blocks and pigs...
    }

    private void createIceLevel() {
        addBlock(new GlassBlock(world, 500f, GROUND_LEVEL + 25f, 250f, 50f));
        // Other blocks and pigs...
    }

    private void createStoneLevel() {
        addBlock(new StoneBlock(world, 500f, GROUND_LEVEL + 25f, 300f, 50f));
        // Other blocks and pigs...
    }

    private void addBlock(GameObject block) {
        if (block != null) {
            blocks.add(block);
        }
    }

    private void addPig(Pig pig) {
        if (pig != null) {
            pigs.add(pig);
        }
    }

    private void clearLevel() {
        for(Bird bird : birds) {
            if(bird != null && bird.getBody() != null) {
                world.destroyBody(bird.getBody());
            }
        }
        for(Pig pig : pigs) {
            if(pig != null && pig.getBody() != null) {
                world.destroyBody(pig.getBody());
            }
        }
        for(GameObject block : blocks) {
            if(block != null && block.getBody() != null) {
                world.destroyBody(block.getBody());
            }
        }

        birds.clear();
        pigs.clear();
        blocks.clear();
        currentBirdIndex = 0;
    }

    public void update() {
        // Update the birds' position to make sure they stay above ground level
        for (Bird bird : birds) {
            if (bird != null) {
                bird.updatePosition();
            }
        }
    }

    public Bird getCurrentBird() {
        return currentBirdIndex < birds.size ? birds.get(currentBirdIndex) : null;
    }

    public void nextBird() {
        currentBirdIndex++;
    }

    public boolean isLevelComplete() {
        return pigs.size == 0;
    }

    public boolean isGameOver() {
        return currentBirdIndex >= birds.size && !isLevelComplete();
    }

    public Array<Bird> getBirds() { return birds; }
    public Array<Pig> getPigs() { return pigs; }
    public Array<GameObject> getBlocks() { return blocks; }

    public World getWorld() { return world; }
}
