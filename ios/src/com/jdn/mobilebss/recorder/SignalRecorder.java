package com.jdn.mobilebss.recorder;

import java.io.ByteArrayOutputStream;
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
    public ByteArrayOutputStream readSignal() {
        // TODO Auto-generated method stub
        return null;
    }
}