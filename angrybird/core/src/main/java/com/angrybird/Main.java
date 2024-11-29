package com.angrybird;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.angrybird.core.AssetLoader;
import com.angrybird.ui.MenuScreen;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class Main extends Game {
    public SpriteBatch batch;

    @Override
    public void create() {
        // Call AssetLoader to load all assets at the start
        try {
            AssetLoader.load();  // Preload all assets for the game
        } catch (GdxRuntimeException e) {
            System.err.println("Error loading assets: " + e.getMessage());
            e.printStackTrace();
        }

        batch = new SpriteBatch();

        // Display loading screen while assets are loading asynchronously
        setScreen(new LoadingScreen(this));  // Use a loading screen to wait for assets to finish loading
    }

    public void setScreenWithDispose(Screen newScreen) {
        // Dispose current screen resources when switching screens
        if (getScreen() != null) getScreen().dispose();
        setScreen(newScreen);  // Switch to the new screen
    }

    @Override
    public void render() {
        super.render();  // Render the active screen
    }

    @Override
    public void dispose() {
        // Dispose the last screen and all assets
        if (getScreen() != null) getScreen().dispose();
        batch.dispose();  // Dispose the SpriteBatch
        AssetLoader.dispose();  // Dispose all loaded assets
    }

    // A simple LoadingScreen to show loading progress and transition after assets are loaded
    public class LoadingScreen implements Screen {
        private final Main game;

        public LoadingScreen(Main game) {
            this.game = game;
        }

        @Override
        public void render(float delta) {
            if (AssetLoader.manager.update()) {
                // All assets are loaded, switch to MenuScreen
                game.setScreen(new MenuScreen(game));
            } else {
                // Show loading progress (could be shown on a loading bar)
                float progress = AssetLoader.manager.getProgress();
                System.out.println("Loading: " + progress * 100 + "%");
            }
        }

        @Override
        public void show() {}

        @Override
        public void resize(int width, int height) {}

        @Override
        public void hide() {}

        @Override
        public void pause() {}

        @Override
        public void resume() {}

        @Override
        public void dispose() {}
    }
}
