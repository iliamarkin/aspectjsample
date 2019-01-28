package com.example.aspectjsample.util;

import com.example.aspectjsample.annotation.DebugTrace;

public class Utils {

    @DebugTrace
    public Utils() {
    }

    @DebugTrace
    public static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException ignored) {
        }
    }
}
