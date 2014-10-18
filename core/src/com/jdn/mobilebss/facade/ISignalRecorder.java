package com.jdn.mobilebss.facade;

import java.io.IOException;
import com.jdn.mobilebss.controller.PlayerCompletionListener;

public interface ISignalRecorder {
    public boolean isRecording();
    public byte[] getSignal();
    public long getRate();
    public void record(boolean isRecording);
    public void readSignal();
    public void writeAudioDataToFile(String filePath, byte[] signal)
            throws IOException;
    public void playSignal();
    public void pauseSignal();
    public void stopSignal()
            throws IllegalStateException, IOException;
    public void addPlayerCompletionListener(PlayerCompletionListener playerCompletionListener);
}