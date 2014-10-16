package com.jdn.mobilebss.view;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jdn.mobilebss.facade.ISignalRecorder;

public class SignalRenderer {
    private static SignalRenderer renderer;
    public static SignalRenderer getInstance() {
        if (renderer == null) {
            renderer = new SignalRenderer();
        }
        return renderer;
    }

    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private ISignalRecorder recorder;
    private long start;

    private SignalRenderer() {}

    public ISignalRecorder getRecorder() {
        return recorder;
    }

    public void setRecorder(ISignalRecorder recorder) {
        this.recorder = recorder;
    }

    public void begin() {
        start = System.currentTimeMillis();
    }

    public void resize(int width, int height) {
        
    }

    public void render(float delta, GL20 gl) {
        long time = System.currentTimeMillis() - start;
    }
}
