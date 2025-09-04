package com.hxgy.paymentdisplay.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import android.content.Context;
import android.os.Environment;

public class LogHelper {

    private File logFile;
    private BufferedWriter logWriter;

    public LogHelper(Context context, String fileName) {
        File logDir = new File(context.getFilesDir(), "logs");
        if (!logDir.exists()) {
            logDir.mkdirs();
        }
        logFile = new File(logDir, fileName);
        try {
            logWriter = new BufferedWriter(new FileWriter(logFile, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log(String message) {
        if (logWriter != null) {
            PrintWriter printWriter = new PrintWriter(logWriter);
            printWriter.println(message);
            printWriter.flush();
        }
    }

    public void close() {
        if (logWriter != null) {
            try {
                logWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}