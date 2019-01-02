package com.ciarasouthgate.studybreak;

public class Timer {
    private static long runningTime = 0;
    private static long startTime = 0;
    private static int minuteCount = 0;
    private static final int MILLITOMINUTES = 60000;

    public static void main(String[] args) {
        startTime = System.currentTimeMillis();
        while (true) {
            runningTime = System.currentTimeMillis() - startTime;
            if (runningTime / MILLITOMINUTES > minuteCount) {
                minuteCount++;
                System.out.println("Number of minutes elapsed: " + minuteCount);
            }
        }
    }
}
