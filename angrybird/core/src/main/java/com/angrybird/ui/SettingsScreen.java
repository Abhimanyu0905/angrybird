package com.angrybird.ui;

import com.angrybird.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SettingsScreen implements Screen {
    private final Main game;
    private final Stage stage;
    private final Texture backgroundTexture;
    private final Texture backTexture;
    private final Texture volumeTexture;
    private final BitmapFont font;

    public SettingsScreen(Main game) {
        this.game = game;

        // Load textures
        this.backgroundTexture = new Texture("settings_bg.png"); // Background for settings screen
        this.backTexture = new Texture("back.png");  // Back button texture
        this.volumeTexture = new Texture("volume.png"); // Volume button texture

        // Initialize font
        this.font = new BitmapFont(); // Use default font

        // Initialize the stage
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Create UI elements
        createUI();
    }

    private void createUI() {
        // Create "Volume" button
        ImageButton volumeButton = new ImageButton(new TextureRegionDrawable(volumeTexture));
        volumeButton.setSize(100, 50); // Button size
        volumeButton.setPosition(Gdx.graphics.getWidth() / 2f - 50, Gdx.graphics.getHeight() / 2f + 50); // Position it below the screen center
        volumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Implement volume change logic (currently a placeholder)
                System.out.println("Volume button clicked");
            }
        });
        stage.addActor(volumeButton);

        // Create Back button
        ImageButton backButton = new ImageButton(new TextureRegionDrawable(backTexture));
        backButton.setSize(80, 50); // Button size
        backButton.setPosition(20, Gdx.graphics.getHeight() - 70); // Position it at the top left of the screen
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Go back to the main menu
                game.setScreenWithDispose(new MenuScreen(game));
            }
        });
        stage.addActor(backButton);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        game.batch.begin();

        // Draw background
        game.batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Draw text: Settings title
        font.getData().setScale(2); // Make the text larger
        font.draw(game.batch, "Settings", Gdx.graphics.getWidth() / 2f - 50, Gdx.graphics.getHeight() - 100);

        game.batch.end();

        // Render buttons on the stage
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
        // Dispose resources
        stage.dispose();
        backgroundTexture.dispose();
        backTexture.dispose();
        volumeTexture.dispose();
        font.dispose();
    }
}
