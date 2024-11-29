package com.angrybird.ui;

import com.angrybird.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PauseScreen implements Screen {
    private final Main game;
    private final Stage stage;
    private final Texture backgroundTexture;
    private final Texture resumeTexture;
    private final Texture restartTexture;
    private final Texture menuTexture;
    private final String currentLevel;
    private final int levelNumber;

    public PauseScreen(Main game, String currentLevel) {
        this.game = game;
        this.currentLevel = currentLevel;
        // Extract level number from the level name (e.g., "Level 1" -> 1)
        this.levelNumber = Integer.parseInt(currentLevel.split(" ")[1]);

        // Load all required textures
        this.backgroundTexture = new Texture("pause_bg.png");
        this.resumeTexture = new Texture("resume.png");
        this.restartTexture = new Texture("retry.png");
        this.menuTexture = new Texture("menubutton.png");

        // Initialize the stage
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Add buttons to the stage
        createButtons();
    }

    private void createButtons() {
        float buttonWidth = 120;
        float buttonHeight = 70;

        // Resume Button
        ImageButton resumeButton = createButton(resumeTexture, -150, buttonWidth, buttonHeight, () -> {
            game.setScreenWithDispose(new GameScreen(game, currentLevel, levelNumber)); // Now passing 3 arguments
        });

        // Restart Button
        ImageButton restartButton = createButton(restartTexture, 0, buttonWidth, buttonHeight, () -> {
            game.setScreenWithDispose(new GameScreen(game, currentLevel, levelNumber)); // Now passing 3 arguments
        });

        // Menu Button
        ImageButton menuButton = createButton(menuTexture, 150, buttonWidth, buttonHeight, () -> {
            game.setScreenWithDispose(new MenuScreen(game));
        });

        stage.addActor(resumeButton);
        stage.addActor(restartButton);
        stage.addActor(menuButton);
    }

    private ImageButton createButton(Texture texture, float xOffset, float width, float height, Runnable onClickAction) {
        ImageButton button = new ImageButton(new TextureRegionDrawable(texture));
        button.setSize(width, height);
        button.setPosition(
            Gdx.graphics.getWidth() / 2f + xOffset - button.getWidth() / 2,
            Gdx.graphics.getHeight() / 2f - button.getHeight() / 2
        );
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onClickAction.run();
            }
        });
        return button;
    }

    @Override
    public void render(float delta) {
        game.batch.begin();
        game.batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
        resumeTexture.dispose();
        restartTexture.dispose();
        menuTexture.dispose();
    }

    @Override public void show() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
