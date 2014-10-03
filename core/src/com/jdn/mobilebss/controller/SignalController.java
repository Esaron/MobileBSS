package com.jdn.mobilebss.controller;

import com.jdn.mobilebss.facade.ISignalRecorder;

public class SignalController {

    private ISignalRecorder recorder;

    private boolean isRecording = false;

    public SignalController(ISignalRecorder recorder) {
        this.recorder = recorder;
    }

    public boolean isRecording() {
        return isRecording;
    }

    public void setIsRecording(boolean isRecording) {
        this.isRecording = isRecording;
        recorder.record(this.isRecording);
        if (!isRecording) {
            // We stopped recording. Show a dialog that lets us play back, save and perform BSS, or cancel.
        }
    }

    public void toggleRecording() {
        setIsRecording(!isRecording);
    }

    public void update(float delta) {

    }
}
