package com.jdn.mobilebss.android.recorder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder.AudioSource;
import android.util.Log;
import com.badlogic.gdx.Gdx;
import com.jdn.mobilebss.facade.ISignalRecorder;
import com.jdn.mobilebss.util.EncodingUtil;
import com.jdn.mobilebss.util.FileUtil;


public class SignalRecorder implements ISignalRecorder {

    private static final int[] SAMPLE_RATES = new int[] { 44100 };
    private static final short[] AUDIO_ENCODINGS = new short[] { AudioFormat.ENCODING_PCM_16BIT };
    private static final short[] CHANNELS = new short[] { AudioFormat.CHANNEL_IN_STEREO };
    private static final String OUT_PATH = "output/";

    private AudioRecord recorder;
    private Thread recordingThread = null;
    private boolean isRecording = false;
    private int bufferSize;
    private long rate;
    private int channels;

    @Override
    public boolean isRecording() {
        return isRecording;
    }

    @Override
    public void record(boolean isRecording) {
        if (isRecording) {
            recorder = findAudioRecord();
            recorder.startRecording();
            this.isRecording = true;
            recordingThread = new Thread(new Runnable() {
                public void run() {
                    try {
                        writeAudioDataToFile(OUT_PATH, readSignal());
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }, "AudioRecorder Thread");
            recordingThread.start();
        }
        else {
            if (recorder != null) {
                this.isRecording = false;
                recorder.stop();
                recorder.release();
                recorder = null;
                recordingThread = null;
            }
        }
    }

    @Override
    public ByteArrayOutputStream readSignal() {
        byte[] signalBuffer = new byte[bufferSize];
        ByteArrayOutputStream signalStream = new ByteArrayOutputStream(bufferSize);
        while (isRecording) {
            recorder.read(signalBuffer, 0, bufferSize);
            Log.d("Audio Buffer", "Writing bytes to stream: " + signalBuffer.toString());
            signalStream.write(signalBuffer, 0, bufferSize);
        }
        return signalStream;
    }

    @Override
    public void writeAudioDataToFile(String filePath, ByteArrayOutputStream signalStream)
            throws IOException {
        File dir = new File(Gdx.app.getFiles().getLocalStoragePath() + filePath);
        FileOutputStream os = null;

        File file = FileUtil.overwriteFile(dir, "rawOutput");
        os = new FileOutputStream(file);
        byte[] signalBytes = signalStream.toByteArray();
        Log.d("Audio Output", "Writing raw signal to file: " + signalBytes.toString());
        os.write(signalBytes);
        os.close();
        Log.d("Audio Output", "Wrote raw signal to " + file.getAbsolutePath());
        Log.d("Audio Output WAV", "Converting to WAV");
        EncodingUtil.convertToWav(file, "test", (int)rate, (short)channels);//UUID.randomUUID().toString());
    }

    private AudioRecord findAudioRecord() {
        for (int rate : SAMPLE_RATES) {
            for (short audioFormat : AUDIO_ENCODINGS) {
                for (short channelConfig : CHANNELS) {
                    try {
                        Log.d("Sampling Rate Check", "Attempting rate " + rate + "Hz, bits: " + audioFormat + ", channel: "
                                + channelConfig);
                        bufferSize = AudioRecord.getMinBufferSize(rate, channelConfig, audioFormat);

                        if (bufferSize != AudioRecord.ERROR_BAD_VALUE) {
                            // check if we can instantiate and have a success
                            AudioRecord recorder = new AudioRecord(AudioSource.DEFAULT, rate, channelConfig, audioFormat, bufferSize);

                            if (recorder.getState() == AudioRecord.STATE_INITIALIZED)
                                this.rate = recorder.getSampleRate();
                                this.channels = recorder.getChannelCount();
                                return recorder;
                        }
                    }
                    catch (Exception e) {
                        Log.e("Bad Sampling Rate", rate + "Hz: ", e);
                    }
                }
            }
        }
        return null;
    }
}