package com.jdn.mobilebss.recorder;

import java.io.IOException;
import com.badlogic.gdx.files.FileHandle;
import com.jdn.mobilebss.facade.ISignalRecorder;


public class SignalRecorder implements ISignalRecorder {

    @Override
    public boolean isRecording() {
        return false;
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
    public byte[] getSignal() {
        // TODO Auto-generated method stub
        return null;
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
}