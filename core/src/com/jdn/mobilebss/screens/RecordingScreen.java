package com.jdn.mobilebss.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.jdn.mobilebss.MobileBss;
import com.jdn.mobilebss.controller.AudioController;
import com.jdn.mobilebss.view.ButtonRenderer;
import com.jdn.mobilebss.view.SignalRenderer;

public class RecordingScreen implements Screen {
    private InputMultiplexer plexer = new InputMultiplexer();
    private ButtonRenderer buttonRenderer;
    private AudioController audioController;
    private SignalRenderer signalRenderer;

    public RecordingScreen() {}

    @Override
    public void render(float delta) {
        GL20 gl = Gdx.graphics.getGL20();
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        audioController.update(delta);
        buttonRenderer.render(delta, gl);
        signalRenderer.render(delta, gl);
    }

    @Override
    public void resize(int width, int height) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glDepthFunc(GL20.GL_LESS);
        MobileBss.WIDTH = width;
        MobileBss.HEIGHT = height;
        buttonRenderer.resize(width, height);
        signalRenderer.resize(width, height);
    }

    @Override
    public void show() {
        signalRenderer = SignalRenderer.getInstance();
        buttonRenderer = ButtonRenderer.getInstance();
        plexer.addProcessor(buttonRenderer.getStage());
        Gdx.input.setInputProcessor(plexer);
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    public AudioController getAudioController() {
        return audioController;
    }

    public void setAudioController(AudioController controller) {
        this.audioController = controller;
    }
}