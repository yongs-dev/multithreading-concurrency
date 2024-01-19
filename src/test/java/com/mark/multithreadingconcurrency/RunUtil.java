package com.mark.multithreadingconcurrency;

public class RunUtil {

    public static void noStopTest() {
        try {
            // test 스레드 종료를 막기 위함
            Thread.sleep(12 * 1000 * 60 * 60);
        } catch (Exception ignored) {}
    }
}
