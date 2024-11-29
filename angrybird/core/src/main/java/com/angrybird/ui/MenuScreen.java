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

public class MenuScreen implements Screen {
    private final Main game; // Reference to main game class for screen switching
    private final Stage stage;
    private final Texture background;
    private final Texture playTexture;
    private final Texture settingsTexture;
    private final Texture exitTexture;

    public MenuScreen(Main game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());

        // Load the background texture
        this.background = new Texture("menu_bg.png"); // Ensure this file exists in the assets folder

        // Load button textures
        this.playTexture = new Texture("play.png");
        this.settingsTexture = new Texture("settings.png");
        this.exitTexture = new Texture("exit.png");

        Gdx.input.setInputProcessor(stage);

        // Play Button
        ImageButton playButton = new ImageButton(new TextureRegionDrawable(playTexture));
        playButton.setSize(80, 80); // Set button size
        playButton.setPosition(Gdx.graphics.getWidth() / 2f - 40, 200); // Center horizontally
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreenWithDispose(new LevelScreen(game)); // Switch to LevelScreen
            }
        });
        stage.addActor(playButton);

        // Settings Button
        ImageButton settingsButton = new ImageButton(new TextureRegionDrawable(settingsTexture));
        settingsButton.setSize(80, 80); // Set button size
        settingsButton.setPosition(Gdx.graphics.getWidth() / 2f - 40, 120); // Position below Play button
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Transition to SettingsScreen
                game.setScreenWithDispose(new SettingsScreen(game)); // Switch to SettingsScreen
            }
        });
        stage.addActor(settingsButton);

        // Exit Button
        ImageButton exitButton = new ImageButton(new TextureRegionDrawable(exitTexture));
        exitButton.setSize(80, 80); // Set button size
        exitButton.setPosition(Gdx.graphics.getWidth() / 2f - 40, 40); // Position below Settings button
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        stage.addActor(exitButton);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        game.batch.begin();
        // Draw the background texture to fit the screen
        game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.end();

        // Draw the stage (buttons)
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        // Dispose of the stage and all textures
        stage.dispose();
        background.dispose();
        playTexture.dispose();
        settingsTexture.dispose();
        exitTexture.dispose();
    }
}
