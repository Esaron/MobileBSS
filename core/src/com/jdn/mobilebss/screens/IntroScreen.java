package com.jdn.mobilebss.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jdn.mobilebss.MobileBss;


public class IntroScreen implements Screen {
    private SpriteBatch batch;
    private Texture img;

    @Override
    public void render(float delta) {
        GL20 gl = Gdx.graphics.getGL20();
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
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

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

}
