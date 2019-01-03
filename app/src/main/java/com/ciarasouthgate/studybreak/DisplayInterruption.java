package com.ciarasouthgate.studybreak;

import android.app.Notification;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

import static com.ciarasouthgate.studybreak.App.CHANNEL_1_ID;
import static com.ciarasouthgate.studybreak.App.CHANNEL_2_ID;

public class DisplayInterruption extends AppCompatActivity {
    private NotificationManagerCompat notificationManager;
    private static final int MILLI_IN_MINUTE = 60000;
    private static final int MILLI_IN_SECOND = 1000;

    DecimalFormat f = new DecimalFormat("00");

    private TextView displayTime;
    private TextView breakText;

    private Interruption task;
    private StudySession session;
    public long timeLeft;

    private String timeString;
    private long min;
    private long sec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_interruption);
        notificationManager = NotificationManagerCompat.from(this);

        task = getIntent().getParcelableExtra("task");
        session = getIntent().getParcelableExtra("session");
        timeLeft = getIntent().getLongExtra("timeLeft", 21600000);

        displayTime = findViewById(R.id.interruptionTime);
        breakText = findViewById(R.id.breakText);

        breakText.setText(task.getName() + " Break");

        countdownInterruption(task.getDuration(), task);

        Button endButton = findViewById(R.id.endBreakButton);
        endButton.setVisibility(View.GONE);

    }

    public void countdownInterruption(long runningTime, Interruption task) {
        final String taskName = task.getName();

        new CountDownTimer(runningTime, MILLI_IN_SECOND) {
            public void onTick(long millisUntilFinished) {
                long secondsLeft = millisUntilFinished / MILLI_IN_SECOND;
                min = secondsLeft / 60;
                sec = secondsLeft % 60;
                String minString = f.format(min);
                String secString = f.format(sec);
                timeString = minString + ":" + secString;
                displayTime.setText(timeString);
            }

            public void onFinish() {
                alert();

                goBack();
            }
        }.start();
    }

    public void goBack() {
        Intent intent = new Intent(DisplayInterruption.this, DisplayTimer.class);
        intent.putExtra("session", session);
        intent.putExtra("startTime", timeLeft);
        startActivity(intent);
    }

    public void goBackButton(View view) {
        Intent intent = new Intent(DisplayInterruption.this, DisplayTimer.class);
        intent.putExtra("session", session);
        intent.putExtra("startTime", timeLeft);
        startActivity(intent);
    }

    public void alert() {
        String title = "Break is over!";
        String alertMessage = "Time to get back to studying.";

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_alarm)
                .setContentTitle(title)
                .setContentText(alertMessage)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .build();

        notificationManager.notify(1, notification);
    }
}
