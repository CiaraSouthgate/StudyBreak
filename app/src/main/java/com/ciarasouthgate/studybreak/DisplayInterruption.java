package com.ciarasouthgate.studybreak;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DisplayInterruption extends AppCompatActivity {
    private static final int MILLI_IN_MINUTE = 60000;
    private static final int MILLI_IN_SECOND = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_interruption);
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
}
