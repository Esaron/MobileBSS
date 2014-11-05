package com.jdn.mobilebss.desktop.textures;

import java.io.File;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class Packer {

    private static final String INPUT_DIR = "../textures/";
    private static final String OUTPUT_DIR = "../android/assets/textures/";
    private static final String PACK_FILE_NAME = "textures.pack";

    public static void main (String[] args) throws Exception {
        File packFile = new File(OUTPUT_DIR + PACK_FILE_NAME);
        File textureFile = new File(OUTPUT_DIR + "textures.png");
        if (packFile.exists()) {
            packFile.delete();
        }
        if (textureFile.exists()) {
            textureFile.delete();
        }
        TexturePacker.process(INPUT_DIR, OUTPUT_DIR, PACK_FILE_NAME);
    }
}