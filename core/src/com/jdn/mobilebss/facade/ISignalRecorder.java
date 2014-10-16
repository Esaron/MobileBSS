package com.jdn.mobilebss.facade;

import java.io.IOException;

public interface ISignalRecorder {
    public boolean isRecording();
    public byte[] getSignal();
    public void record(boolean isRecording);
    public void readSignal();
    public void writeAudioDataToFile(String filePath, byte[] signal)
            throws IOException;
    public void playSignal();
    public void pauseSignal();
    public void stopSignal()
            throws IllegalStateException, IOException;
}