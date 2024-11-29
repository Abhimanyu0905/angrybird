package com.angrybird.core;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class AssetLoader {
    public static AssetManager manager;

    public static void load() {
        manager = new AssetManager();
        manager.load("menu_bg.png", Texture.class);
        manager.load("game_bg.png", Texture.class);
        manager.load("settings_bg.png", Texture.class);
        manager.load("pause_bg.png", Texture.class);
        manager.load("play.png", Texture.class);
        manager.load("exit.png", Texture.class);
        manager.load("settings.png", Texture.class);
        manager.load("retry.png", Texture.class);
        manager.load("resume.png", Texture.class);
        manager.load("menubutton.png", Texture.class);
        manager.load("redbird.png", Texture.class);
        manager.load("volume.png", Texture.class);
        manager.load("redbird.png", Texture.class);
        manager.load("blackbird.png", Texture.class);
        manager.load("yellowbird.png", Texture.class);
        manager.load("woodenblock1.png", Texture.class);
        manager.load("woodenblock2.png", Texture.class);
        manager.load("iceblock1.png", Texture.class);
        manager.load("iceblock2.png", Texture.class);
        manager.load("stoneblock1.png", Texture.class);
        manager.load("stoneblock2.png", Texture.class);
        manager.load("pig.png", Texture.class);
        // Load other assets as needed
    }

    public static Texture getTexture(String fileName) {
        return manager.get(fileName, Texture.class);
    }

    public static void dispose() {
        manager.dispose();
    }
}
