package com.jdn.mobilebss.view;

import java.nio.ByteOrder;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.jdn.mobilebss.MobileBss;
import com.jdn.mobilebss.facade.ISignalRecorder;
import com.jdn.mobilebss.util.EncodingUtil;

public class SignalRenderer {
    private static SignalRenderer renderer;

    public static SignalRenderer getInstance() {
        if (renderer == null) {
            renderer = new SignalRenderer();
        }
        return renderer;
    }

    private int signalWindowYOffset = ButtonRenderer.buttonSize;
    private int signalWindowHeight = MobileBss.HEIGHT - signalWindowYOffset;
    private int subWindowHeight = signalWindowHeight/2;
    private int signalMax = subWindowHeight/2;
    private int signalWindowWidth = MobileBss.WIDTH;
    private int timeWindowSeconds = 5;
    private long dataPoints;
    private long betweenDataPoints;

    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private ISignalRecorder recorder;

    private SignalRenderer() {}

    public ISignalRecorder getRecorder() {
        return recorder;
    }

    public void setRecorder(ISignalRecorder recorder) {
        this.recorder = recorder;
    }

    public void begin() {
        
    }

    public void resize(int width, int height) {
        signalWindowYOffset = ButtonRenderer.buttonSize;
        signalWindowHeight = height - signalWindowYOffset;
        subWindowHeight = signalWindowHeight/2;
        signalWindowWidth = width;
    }

    public void render(float delta, GL20 gl) {
        if (recorder != null) {
            short[] signal = EncodingUtil.byteArrayToShortArray(recorder.getSignal(), ByteOrder.LITTLE_ENDIAN);
            dataPoints = Math.min(timeWindowSeconds * recorder.getRate(), signal.length);
            betweenDataPoints = (long) Math.ceil(((double)dataPoints)/signalWindowWidth);
            int i = 0;
            shapeRenderer.begin(ShapeType.Line);
            shapeRenderer.setColor(Color.GREEN);
            for (int j = 0; j < dataPoints; j += betweenDataPoints) {
                int xStart = i++;
                int signalStart = j;
                j += betweenDataPoints;
                if (j > signal.length - 1){
                    j = signal.length - 1;
                }
                shapeRenderer.line(xStart, normalizeToWindow(signal[signalStart]), i++, normalizeToWindow(signal[j]));
            }
//            for (int j = 0; j < dataPoints; j++) {
//                int xStart = i++;
//                int signalStart = j++;
//                if (j < signal.length) {
//                    shapeRenderer.line(xStart, normalizeToWindow(signal[signalStart]), i++, normalizeToWindow(signal[j]));
//                }
//            }
            shapeRenderer.end();
        }
    }

    private float normalizeToWindow(short value) {
        float result = signalWindowYOffset + subWindowHeight + signalMax + ((float)value)*signalMax/Short.MAX_VALUE;
        return result;
    }
}
