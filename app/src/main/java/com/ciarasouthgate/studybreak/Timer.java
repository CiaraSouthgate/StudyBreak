package com.ciarasouthgate.studybreak;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Parcel;
import android.os.Parcelable;

public class Timer implements Parcelable {
    private long startTime;
    private static final int MILLI_TO_MINUTES = 60000;
    private static final int MILLI_IN_SECOND = 1000;

    private static void countdownStudy(long runningTime, final Interruption[] tasks) {
        new CountDownTimer(runningTime, MILLI_TO_MINUTES) {
            public void onTick(long millisUntilFinished) {
                for (Interruption task : tasks) {
                    //TODO update displayed timer
                    if (millisUntilFinished % task.getDuration() == 0) {
                        //TODO run interruption activity
                        countdownStudy(millisUntilFinished, tasks);
                        cancel();
                    }
                }
            }

            public void onFinish() {

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
            }
        }.start();
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(startTime);
    }

    private Timer(Parcel in) {
        startTime = in.readLong();
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
