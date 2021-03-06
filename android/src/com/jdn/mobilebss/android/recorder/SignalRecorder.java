package com.jdn.mobilebss.android.recorder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder.AudioSource;
import android.util.Log;
import com.badlogic.gdx.Gdx;
import com.jdn.mobilebss.controller.PlayerCompletionListener;
import com.jdn.mobilebss.facade.ISignalRecorder;
import com.jdn.mobilebss.util.EncodingUtil;
import com.jdn.mobilebss.util.FileUtil;

public class SignalRecorder implements ISignalRecorder {

    private static final int[] SAMPLE_RATES = new int[] { 44100 };
    private static final short[] AUDIO_ENCODINGS = new short[] { AudioFormat.ENCODING_PCM_16BIT };
    private static final short[] CHANNELS = new short[] { AudioFormat.CHANNEL_IN_STEREO };
    private static final String OUT_PATH = "output/";

    private MediaPlayer player;
    private FileInputStream signalInputStream;
    private AudioRecord recorder;
    private Thread recordingThread = null;
    private boolean isRecording = false;
    private int bufferSize;
    private long rate;
    private int channels;
    private byte[] signal = {(byte)0xff, (byte)0xff};

    public SignalRecorder() {
        recorder = findAudioRecord();
    }

    @Override
    public boolean isRecording() {
        return isRecording;
    }

    @Override
    public byte[] getSignal() {
        return signal;
    }

    @Override
    public long getRate() {
        return rate;
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
                        readSignal();
                        writeAudioDataToFile(OUT_PATH, signal);
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
    public void readSignal() {
        byte[] signalBuffer = new byte[bufferSize];
        ByteArrayOutputStream signalStream = new ByteArrayOutputStream(bufferSize);
        while (isRecording) {
            recorder.read(signalBuffer, 0, bufferSize);
            Log.d("Audio Buffer", "Writing bytes to stream: " + signalBuffer.toString());
            signalStream.write(signalBuffer, 0, bufferSize);
            signal = signalStream.toByteArray();
        }
    }

    @Override
    public void writeAudioDataToFile(String outPath, byte[] signal)
            throws IOException {
        String dirPath = Gdx.app.getFiles().getLocalStoragePath() + outPath;
        File dir = new File(dirPath);
        String rawName = "rawOutput";
        String wavName = "test.wav";
        File rawFile = FileUtil.overwriteFile(dir, rawName);
        File wavFile = new File(dir, wavName);

        FileOutputStream os = null;
        os = new FileOutputStream(rawFile);
        Log.d("Audio Output", "Writing raw signal to file: " + signal.toString());
        os.write(signal);
        os.close();
        Log.d("Audio Output", "Wrote raw signal to " + rawFile.getAbsolutePath());

        Log.d("Audio Output WAV", "Converting to WAV");
        EncodingUtil.convertToWav(rawFile, wavFile, (int)rate, (short)channels);//UUID.randomUUID().toString());
        initPlayer(wavFile);
    }

    @Override
    public void playSignal() {
        if (player != null) {
            player.start();
        }
    }

    @Override
    public void pauseSignal() {
        if (player != null) {
            player.pause();
        }
    }

    @Override
    public void stopSignal()
            throws IllegalStateException, IOException {
        if (player != null) {
            player.seekTo(0);
            player.pause();
        }
    }

    private void initPlayer(File file) {
        if (player == null) {
            player = new MediaPlayer();
        }
        else {
            player.reset();
        }
        try {
            signalInputStream = new FileInputStream(file);
        }
        catch(FileNotFoundException e) {
            Log.d("Stream Load", "Unable to locate file:");
            e.printStackTrace();
        }
        try {
            player.setDataSource(signalInputStream.getFD());
            player.prepare();
        }
        catch(IOException e) {
            Log.d("MediaPlayer", "Unable to prepare MediaPlayer:");
            e.printStackTrace();
        }
    }

    public void addPlayerCompletionListener(PlayerCompletionListener listener) {
        final PlayerCompletionListener finalListener = listener;
        player.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                finalListener.complete();
            }
        });
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