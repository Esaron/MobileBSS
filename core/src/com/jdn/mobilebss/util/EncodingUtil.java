package com.jdn.mobilebss.util;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class EncodingUtil {

    public static void convertToWav(File fileToConvert, File destinationFile, int rate, short channels) {
        try {
            int bitsPerSample = 16;
            int bytesPerSample = bitsPerSample/8;
            int blockAlign = channels * bytesPerSample;
            int byteRate = rate * blockAlign;

            byte[] clipData = getBytesFromFile(fileToConvert);

            int subChunk1Size = 16;
            int dataSize = clipData.length;
            int chunkSize = 36 + dataSize;

            OutputStream os;
            os = new FileOutputStream(destinationFile);
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream outFile = new DataOutputStream(bos);

            outFile.writeBytes("RIFF");                                     // 00 - RIFF
            outFile.write(intToByteArray(chunkSize), 0, 4);                 // 04 - how big is the rest of this file?
            outFile.writeBytes("WAVE");                                     // 08 - WAVE
            outFile.writeBytes("fmt ");                                     // 12 - fmt 
            outFile.write(intToByteArray(subChunk1Size), 0, 4);             // 16 - size of this chunk
            outFile.write(shortToByteArray((short)1), 0, 2);                // 20 - what is the audio format? 1 for PCM = Pulse Code Modulation
            outFile.write(shortToByteArray(channels), 0, 2);                // 22 - mono or stereo? 1 or 2?  (or 5 or ???)
            outFile.write(intToByteArray(rate), 0, 4);                      // 24 - samples per second (numbers per second)
            outFile.write(intToByteArray(byteRate), 0, 4);                  // 28 - bytes per second
            outFile.write(shortToByteArray((short)blockAlign), 0, 2);       // 32 - # of bytes in one sample, for all channels
            outFile.write(shortToByteArray((short)bitsPerSample), 0, 2);    // 34 - how many bits in a sample(number)?  usually 16 or 24
            outFile.writeBytes("data");                                     // 36 - data
            outFile.write(intToByteArray(dataSize), 0, 4);                  // 40 - how big is this data chunk
            outFile.write(clipData);                                        // 44 - the actual data itself - just a long string of numbers

            outFile.flush();
            outFile.close();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] intToByteArray(int i) {
        return reverseBytes(ByteBuffer.allocate(4).putInt(i).array());
    }

    public static byte[] shortToByteArray(short s) {
        return reverseBytes(ByteBuffer.allocate(2).putShort(s).array());
    }

    public static byte[] reverseBytes(byte[] bytes) {
        int len = bytes.length;
        byte[] reverseBytes = new byte[len];
        for (int i = 0; i < len; i++) {
            reverseBytes[i] = bytes[len - i - 1];
        }
        return reverseBytes;
    }

    public static byte[] getBytesFromFile(File file) 
            throws IOException {
        byte[] buffer = new byte[(int) file.length()];
        InputStream ios = null;
        try {
            ios = new FileInputStream(file);
            if (ios.read(buffer) == -1) {
                throw new IOException("EOF reached while trying to read the whole file");
            }
        }
        finally { 
            if (ios != null) {
                ios.close();
            }
        }

        return buffer;
    }
}
