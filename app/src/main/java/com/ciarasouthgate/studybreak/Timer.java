package com.ciarasouthgate.studybreak;

import android.os.Parcel;
import android.os.Parcelable;

public class Timer implements Parcelable {
    private static long runningTime = 0;
    private static long startTime = 0;
    private static int minuteCount = 0;
    private static final int MILLI_TO_MINUTES = 60000;

    public static void countdown() {
        startTime = System.currentTimeMillis();
        while (true) {
            runningTime = System.currentTimeMillis() - startTime;
            if (runningTime / MILLI_TO_MINUTES > minuteCount) {
                minuteCount++;
                System.out.println("Number of minutes elapsed: " + minuteCount);
            }
        }
    } //ToDo -countdown or countup


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(runningTime);
        dest.writeLong(startTime);
        dest.writeInt(minuteCount);
    }

    private Timer(Parcel in) {
        runningTime = in.readLong();
        startTime = in.readLong();
        minuteCount = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Timer> CREATOR
            = new Parcelable.Creator<Timer>() {

        @Override
        public Timer createFromParcel(Parcel in) {
            return new Timer(in);
        }

        @Override
        public Timer[] newArray(int size) {
            return new Timer[size];
        }
    };
}
