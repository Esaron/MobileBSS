package com.jdn.mobilebss.util;

import java.io.File;
import java.io.IOException;

public class FileUtil {
    public static File overwriteFile(File dir, String name)
            throws IOException {
        File file = new File(dir, name);
        return overwriteFile(file);
    }

    public static File overwriteFile(File file)
            throws IOException {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (file.exists()) {
            if (!file.delete()) {
                throw new IOException("Unable to delete output file");
            }
        }
        if (!file.createNewFile()) {
            throw new IOException("Unable to create output file");
        }
        return file;
    }
}
