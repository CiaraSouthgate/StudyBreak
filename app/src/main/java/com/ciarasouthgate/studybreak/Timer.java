package com.ciarasouthgate.studybreak;

import android.os.CountDownTimer;
import android.os.Parcel;
import android.os.Parcelable;

public class Timer implements Parcelable {
    private static long runningTime = 0;
    private static long startTime = 0;
    private static int minuteCount = 0;
    private static final int MILLI_TO_MINUTES = 60000;
    private static final int MILLI_IN_SECOND = 1000;

    public static void countdownStudy(long runningTime, final Interruption[] tasks) {
        new CountDownTimer(runningTime, MILLI_TO_MINUTES) {
            public void onTick(long millisUntilFinished) {
                for (Interruption task : tasks) {
                    //TODO update displayed timer
                    if (millisUntilFinished % task.getDuration() == 0) {
                        long current = millisUntilFinished;
                        //TODO run interruption activity
                        countdownStudy(current, tasks);
                        cancel();
                    }
                }
            }

            public void onFinish() {
               //TODO whatever happens when you're done studying
            }
        }.start();

    }

    public static void countdownInterruption(long runningTime, Interruption task) {
        new CountDownTimer(runningTime, MILLI_IN_SECOND) {
            public void onTick(long millisUntilFinished) {
                //TODO update displayed timer
            }

            public void onFinish() {
                //TODO make notification
                return;
            }
        }.start();
    }


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
