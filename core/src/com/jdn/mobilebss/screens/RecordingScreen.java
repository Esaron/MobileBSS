package com.jdn.mobilebss.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jdn.mobilebss.MobileBss;
import com.jdn.mobilebss.controller.SignalController;
import com.jdn.mobilebss.input.CursorInputProcessor;
import com.jdn.mobilebss.view.SignalRenderer;

public class RecordingScreen implements Screen {
    private SpriteBatch batch;
    private Texture img;
    private InputMultiplexer plexer = new InputMultiplexer();
    private SignalRenderer renderer;
    private SignalController controller;

    public RecordingScreen() {}

    @Override
    public void render(float delta) {
        GL20 gl = Gdx.graphics.getGL20();
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        controller.update(delta);
        renderer.render(delta, gl);
        batch.begin();
        batch.draw(img, MobileBss.WIDTH/2 - img.getWidth()/2, MobileBss.HEIGHT/2 - img.getHeight()/2);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glDepthFunc(GL20.GL_LESS);
        MobileBss.WIDTH = width;
        MobileBss.HEIGHT = height;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        img = new Texture("CWRU.jpg");
        renderer = SignalRenderer.getInstance();
        plexer.addProcessor(new CursorInputProcessor(controller));
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

    public SignalController getController() {
        return controller;
    }

    public void setController(SignalController controller) {
        this.controller = controller;
    }
}