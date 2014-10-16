package com.jdn.mobilebss.controller;

import java.io.IOException;
import java.util.List;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jdn.mobilebss.facade.ISignalRecorder;
import com.jdn.mobilebss.view.ButtonRenderer;

public class AudioController {

    private ButtonRenderer renderer = ButtonRenderer.getInstance();
    private List<ImageButton> buttons = renderer.getButtons();
    private ImageButton playButton = renderer.getPlayButton();
    private ImageButton pauseButton = renderer.getPauseButton();
    private ImageButton stopButton = renderer.getStopButton();
    private ImageButton recordButton = renderer.getRecordButton();

    private ISignalRecorder recorder;

    public enum State {
        PLAYING,
        PAUSED,
        STOPPED,
        RECORDING
    }

    private State state = State.STOPPED;

    public AudioController(ISignalRecorder platformRecorder) {
        recorder = platformRecorder;
        for (ImageButton button : buttons) {
            button.clearListeners();
        }
        stopButton.setChecked(true);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (state.equals(State.STOPPED) || state.equals(State.PAUSED)) {
                    playButton.setChecked(true);
                    stopButton.setChecked(false);
                    pauseButton.setChecked(false);
                    state = State.PLAYING;
                    recorder.playSignal();
                }
            }
        });

        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (state.equals(State.PLAYING)) {
                    pauseButton.setChecked(true);
                    playButton.setChecked(false);
                    state = State.PAUSED;
                    recorder.pauseSignal();
                }
                else if (state.equals(State.PAUSED)) {
                    pauseButton.setChecked(false);
                    playButton.setChecked(true);
                    state = State.PLAYING;
                    recorder.playSignal();
                }
            }
        });

        stopButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (state.equals(State.PLAYING) || state.equals(State.RECORDING)) {
                    stopButton.setChecked(true);
                    playButton.setChecked(false);
                    recordButton.setChecked(false);
                    state = State.STOPPED;
                    try {
                        recorder.stopSignal();
                    }
                    catch (Exception e) {
                        Gdx.app.debug("Recorder", "Unable to stop signal:");
                        e.printStackTrace();
                    }
                    recorder.record(false);
                }
            }
        });

        recordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (state.equals(State.STOPPED)) {
                    recordButton.setChecked(true);
                    stopButton.setChecked(false);
                    state = State.RECORDING;
                    recorder.record(true);
                }
                else if (state.equals(State.RECORDING)) {
                    recordButton.setChecked(false);
                    stopButton.setChecked(true);
                    state = State.STOPPED;
                    recorder.record(false);
                }
            }
        });
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void update(float delta) {

    }
}
