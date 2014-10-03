package com.jdn.mobilebss.facade;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public interface ISignalRecorder {
    public boolean isRecording();
    public void record(boolean isRecording);
    public ByteArrayOutputStream readSignal();
    public void writeAudioDataToFile(String filePath, ByteArrayOutputStream signalStream)
            throws IOException;
}