package com.ciarasouthgate.studybreak;
import android.os.Parcel;
import android.os.Parcelable;

public class Interruption implements Parcelable {

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
    /** The name of interruption*/
    private static String name;
    /** The interval of interruption*/
    private static long interval;
    /** The duration of interruption*/
    private static long duration;

    public Interruption(String name, long interval, long duration) {
        this.name = name;
        this.interval = interval;
        this.duration = duration;
    }
    public static void main(String[] args) {
        startTime = System.currentTimeMillis();
        for (int i = 0; i < taskLengthTime.length; i++) {
            while (runningTime < taskLengthTime[i]) {
                runningTime = System.currentTimeMillis() - startTime - stoppedTime;
            }
            System.out.println("Break time @ " + taskLengthTime[i] + " milliseconds.");
            pausedTime = System.currentTimeMillis();
            //Time is currently paused
            //On button click, resume the program here.
            stoppedTime = System.currentTimeMillis() - pausedTime;
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(runningTime);
        dest.writeLong(startTime);
        dest.writeLong(pausedTime);
        dest.writeLong(stoppedTime);
        dest.writeIntArray(taskLengthTime);
    }

    private Interruption(Parcel in) {
        runningTime = in.readLong();
        startTime = in.readLong();
        pausedTime = in.readLong();
        stoppedTime = in.readLong();
        taskLengthTime = in.createIntArray();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Interruption> CREATOR
            = new Parcelable.Creator<Interruption>() {

        @Override
        public Interruption createFromParcel(Parcel in) {
            return new Interruption(in);
        }

        @Override
        public Interruption[] newArray(int size) {
            return new Interruption[size];
        }
    };
}
