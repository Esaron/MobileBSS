package com.jdn.mobilebss;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.jdn.mobilebss.controller.SignalController;
import com.jdn.mobilebss.facade.ISignalRecorder;
import com.jdn.mobilebss.screens.RecordingScreen;

public class MobileBss extends Game {
    public static int WIDTH;
    public static int HEIGHT;

    private RecordingScreen screen;
    private ISignalRecorder recorder;

    public MobileBss(ISignalRecorder recorder) {
        this.recorder = recorder;
    }

    @Override
    public void create() {
        WIDTH = Gdx.app.getGraphics().getWidth();
        HEIGHT = Gdx.app.getGraphics().getHeight();
        this.screen = new RecordingScreen();
        screen.setController(new SignalController(recorder));
        this.setScreen(screen);
    }

    @Override
    public void render() {
        super.render();
    }
}
