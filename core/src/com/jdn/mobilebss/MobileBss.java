package com.jdn.mobilebss;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.jdn.mobilebss.controller.AudioController;
import com.jdn.mobilebss.facade.ISignalRecorder;
import com.jdn.mobilebss.screens.IntroScreen;
import com.jdn.mobilebss.screens.RecordingScreen;

public class MobileBss extends Game {
    public static int WIDTH;
    public static int HEIGHT;

    private ISignalRecorder recorder;
    long startTime;
    boolean pastIntro;

    // Used to retrieve textures from the texture pack
    public static TextureAtlas TEXTURES;

    public MobileBss(ISignalRecorder recorder) {
        this.recorder = recorder;
    }

    @Override
    public void create() {
        TEXTURES = new TextureAtlas(Gdx.files.internal("textures/textures.pack"));
        WIDTH = Gdx.app.getGraphics().getWidth();
        HEIGHT = Gdx.app.getGraphics().getHeight();
        this.setScreen(new IntroScreen());
        startTime = System.currentTimeMillis();
    }

    @Override
    public void render() {
        if (!pastIntro && System.currentTimeMillis() - startTime > 3000) {
            pastIntro = true;
            RecordingScreen screen = new RecordingScreen();
            screen.setAudioController(new AudioController(recorder));
            this.setScreen(screen);
        }
        super.render();
    }
}
