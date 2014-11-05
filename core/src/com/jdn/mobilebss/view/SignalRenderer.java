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
    private int timeWindowSeconds = 3;
    private long dataPoints;
    private long dataPointsInTimeWindow;
    private long betweenPoints;

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
            dataPoints = signal.length;
            dataPointsInTimeWindow = timeWindowSeconds*recorder.getRate();
            betweenPoints = (long)((float)dataPointsInTimeWindow/signalWindowWidth);
            if (betweenPoints % 2 != 0) {
                betweenPoints -= 1;
            }
            int i = 0;
            shapeRenderer.begin(ShapeType.Line);
            shapeRenderer.setColor(Color.GREEN);
            for (int j = 0; j < signal.length; j += betweenPoints) {
                int xStart = i++;
                int signal1Start = j;
                int signal2Start = j+1;
                j += betweenPoints;
                if (j > dataPointsInTimeWindow || j > signal.length - 2){
                    break;
                }
                shapeRenderer.line(xStart, normalizeToWindow(signal[signal1Start]), i++, normalizeToWindow(signal[j]));
                shapeRenderer.line(xStart, normalizeToWindow(signal[signal2Start]), i++, normalizeToWindow(signal[j+1]));
            }
            shapeRenderer.end();
        }
    }

    private float normalizeToWindow(short value) {
        float result = (float)signalWindowYOffset + (float)subWindowHeight + (float)signalMax + (float)value*(float)signalMax/(float)Short.MAX_VALUE;
        return result;
    }
}
