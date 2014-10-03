package com.jdn.mobilebss.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jdn.mobilebss.MobileBss;
import com.jdn.mobilebss.desktop.recorder.SignalRecorder;

public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 800;
        config.height = 600;
        new LwjglApplication(new MobileBss(new SignalRecorder()), config);
    }
}
