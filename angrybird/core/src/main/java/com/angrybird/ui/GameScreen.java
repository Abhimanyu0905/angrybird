package com.angrybird.ui;

import com.angrybird.Main;
import com.angrybird.core.LevelManager;
import com.angrybird.entities.Bird;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;

public class GameScreen implements Screen {
    private final Main game;
    private final String levelName;
    private final BitmapFont font;
    private final Texture gameBackground;
    private final Texture pauseTexture;
    private final Texture slingshotTexture;
    private final Stage stage;

    // Physics related
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private LevelManager levelManager;
    private Bird currentBird;
    private boolean isDragging;
    private Vector2 slingshotPosition;
    private Slingshot slingshot;
    private final float PIXELS_TO_METERS = 100f;

    public GameScreen(Main game, String levelName, int levelNumber) {
        this.game = game;
        this.levelName = levelName;
        this.isDragging = false;

        // Initialize resources
        this.font = new BitmapFont();
        this.gameBackground = new Texture("game_bg.png");
        this.pauseTexture = new Texture("pause.png");
        this.slingshotTexture = new Texture("slingshot.png");

        // Initialize physics world
        this.world = new World(new Vector2(0, -9.81f), true);
        this.debugRenderer = new Box2DDebugRenderer();
        this.levelManager = new LevelManager(world);
        this.levelManager.loadLevel(levelNumber);

        // Initialize slingshot
        this.slingshotPosition = new Vector2(90, 100);
        this.slingshot = new Slingshot(slingshotTexture, slingshotPosition.x, slingshotPosition.y);

        // Initialize stage and UI
        this.stage = new Stage(new ScreenViewport());
        setupInputProcessor();
        setupUI();
    }

    private void setupInputProcessor() {
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector2 worldPoint = screenToWorld(screenX, screenY);

                if (slingshot.isWithinRange(worldPoint.x, worldPoint.y)) {
                    currentBird = levelManager.getCurrentBird();
                    if (currentBird != null && !currentBird.isLaunched()) {
                        isDragging = true;
                        currentBird.getBody().setTransform(worldPoint.x, worldPoint.y, 0);
                        currentBird.getBody().setAwake(false);
                        return true;
                    }
                }
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                if (isDragging && currentBird != null) {
                    Vector2 worldPoint = screenToWorld(screenX, screenY);
                    slingshot.setPullPosition(worldPoint.x, worldPoint.y);
                    currentBird.getBody().setTransform(worldPoint.x, worldPoint.y, 0);
                    currentBird.getBody().setAwake(false);
                    return true;
                }
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (isDragging && currentBird != null) {
                    Vector2 releasePoint = screenToWorld(screenX, screenY);
                    Vector2 velocity = slingshot.getLaunchVelocity(releasePoint);

                    currentBird.getBody().setAwake(true);
                    currentBird.launch(velocity);

                    slingshot.setPullPosition(slingshot.getAnchor().x, slingshot.getAnchor().y);
                    isDragging = false;
                    levelManager.nextBird();
                    return true;
                }
                return false;
            }
        });

        Gdx.input.setInputProcessor(multiplexer);
    }

    private void setupUI() {
        ImageButton pauseButton = new ImageButton(new TextureRegionDrawable(pauseTexture));
        pauseButton.setSize(50, 50);
        pauseButton.setPosition(Gdx.graphics.getWidth() - 60, Gdx.graphics.getHeight() - 60);
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreenWithDispose(new PauseScreen(game, levelName));
            }
        });
        stage.addActor(pauseButton);
    }

    private Vector2 screenToWorld(int screenX, int screenY) {
        Vector3 worldCoords = stage.getCamera().unproject(new Vector3(screenX, screenY, 0));
        return new Vector2(worldCoords.x / PIXELS_TO_METERS, worldCoords.y / PIXELS_TO_METERS);
    }

    @Override
    public void render(float delta) {
        // Update physics
        world.step(1/60f, 6, 2);
        levelManager.update();

        // Clear screen
        Gdx.gl.glClearColor(0.57f, 0.77f, 0.85f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Begin sprite batch rendering
        game.batch.begin();

        // Draw background
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float imageWidth = gameBackground.getWidth();
        float imageHeight = gameBackground.getHeight();
        float scale = Math.max(screenWidth / imageWidth, screenHeight / imageHeight);
        float x = (screenWidth - imageWidth * scale) / 2;
        float y = (screenHeight - imageHeight * scale) / 2;
        game.batch.draw(gameBackground, x, y, imageWidth * scale, imageHeight * scale);

        // Render game objects
        levelManager.render(game.batch);

        // Render slingshot
        slingshot.render(game.batch);

        game.batch.end();

        // Render slingshot band if dragging
        if (isDragging && currentBird != null) {
            slingshot.renderSling(game.batch);
        }

        game.batch.begin();
        // Draw level name
        font.draw(game.batch, levelName, 10, Gdx.graphics.getHeight() - 10);

        // Draw game state messages
        if (levelManager.isLevelComplete()) {
            font.draw(game.batch, "Level Complete!", screenWidth/2 - 50, screenHeight/2);
        } else if (levelManager.isGameOver()) {
            font.draw(game.batch, "Game Over!", screenWidth/2 - 40, screenHeight/2);
        }
        game.batch.end();

        // Debug physics rendering
        debugRenderer.render(world, stage.getCamera().combined.cpy().scl(PIXELS_TO_METERS));

        // Draw UI
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        font.dispose();
        gameBackground.dispose();
        pauseTexture.dispose();
        slingshotTexture.dispose();
        stage.dispose();
        world.dispose();
        debugRenderer.dispose();
        slingshot.dispose();
    }

    @Override public void show() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
