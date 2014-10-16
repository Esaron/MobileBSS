package com.jdn.mobilebss.desktop.recorder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import com.badlogic.gdx.files.FileHandle;
import com.jdn.mobilebss.facade.ISignalRecorder;


public class SignalRecorder implements ISignalRecorder {

    @Override
    public boolean isRecording() {
        return false;
    }

    @Override
    public byte[] getSignal() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void record(boolean isRecording) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void readSignal() {
        // TODO Auto-generated method stub
    }

    @Override
    public void writeAudioDataToFile(String filePath, byte[] signal)
            throws IOException {
        // TODO Auto-generated method stub
    }

    @Override
    public void playSignal() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void pauseSignal() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void stopSignal()
            throws IllegalStateException, IOException {
        // TODO Auto-generated method stub
        
    }

//    private Mixer
}