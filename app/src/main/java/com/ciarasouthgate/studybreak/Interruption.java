package com.ciarasouthgate.studybreak;

public class Interruption {

    /**How long the program has been running for while not paused. */
    private static long runningTime = 0;
    /**The start time.*/
    private static long startTime = 0;
    /**The system time at which the program is paused.*/
    private static long pausedTime = 0;
    /**The length of time at which the program is paused.*/
    private static long stoppedTime = 0;
    /**An array holding the intevals of the breaks.*/
    private static int [] taskLengthTime = {2000, 5000}; //time is in milliseconds

    public static void main(String[] args) {
        startTime = System.currentTimeMillis();
        for (int i = 0; i < taskLengthTime.length; i++) {
            while (runningTime < taskLengthTime[i]) {
                runningTime = System.currentTimeMillis() - startTime - stoppedTime;
            }
            System.out.println("Break time @ " + taskLengthTime[i] + " milliseconds.");
            pausedTime = System.currentTimeMillis();
            //Time is currently paused
            //On button click, resume the program here.a
            stoppedTime = System.currentTimeMillis() - pausedTime;
        }
    }
}
