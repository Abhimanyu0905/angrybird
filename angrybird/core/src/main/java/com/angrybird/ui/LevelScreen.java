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

public class LevelScreen implements Screen {
    private final Main game;
    private final Stage stage;
    private final Texture levelBackground;
    private final Texture level1Texture;
    private final Texture level2Texture;
    private final Texture level3Texture;
    private final float BUTTON_SIZE = 80f;
    private final float BUTTON_Y = 200f;

    public LevelScreen(Main game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());

        // Load textures
        this.levelBackground = new Texture("level_bg.png");
        this.level1Texture = new Texture("level1.png");
        this.level2Texture = new Texture("level2.png");
        this.level3Texture = new Texture("level3.png");

        Gdx.input.setInputProcessor(stage);
        createLevelButtons();
    }

    private void createLevelButtons() {
        float screenWidth = Gdx.graphics.getWidth();
        float spacing = 100f; // Space between buttons
        float totalWidth = (BUTTON_SIZE * 3) + (spacing * 2);
        float startX = (screenWidth - totalWidth) / 2;

        // Level 1 Button (Wooden Level) - Leftmost
        ImageButton level1Button = createLevelButton(
            level1Texture,
            startX,
            BUTTON_Y,
            "Level 1",
            1
        );

        // Level 2 Button (Ice Level) - Middle
        ImageButton level2Button = createLevelButton(
            level2Texture,
            startX + BUTTON_SIZE + spacing,
            BUTTON_Y,
            "Level 2",
            2
        );

        // Level 3 Button (Stone Level) - Rightmost
        ImageButton level3Button = createLevelButton(
            level3Texture,
            startX + (BUTTON_SIZE + spacing) * 2,
            BUTTON_Y,
            "Level 3",
            3
        );

        stage.addActor(level1Button);
        stage.addActor(level2Button);
        stage.addActor(level3Button);
    }

    private ImageButton createLevelButton(Texture texture, float x, float y,
                                          final String levelName, final int levelNumber) {
        ImageButton button = new ImageButton(new TextureRegionDrawable(texture));
        button.setSize(BUTTON_SIZE, BUTTON_SIZE);
        button.setPosition(x, y);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Create new GameScreen with specific level number
                game.setScreenWithDispose(new GameScreen(game, levelName, levelNumber));
            }
        });

        return button;
    }

    @Override
    public void render(float delta) {
        // Draw background
        game.batch.begin();
        game.batch.draw(levelBackground, 0, 0,
            Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.end();

        // Draw stage (buttons)
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        levelBackground.dispose();
        level1Texture.dispose();
        level2Texture.dispose();
        level3Texture.dispose();
    }
}
