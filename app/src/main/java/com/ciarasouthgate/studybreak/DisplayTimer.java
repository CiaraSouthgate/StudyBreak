package com.ciarasouthgate.studybreak;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.ciarasouthgate.studybreak.App.CHANNEL_1_ID;

public class DisplayTimer extends AppCompatActivity {
    private NotificationManagerCompat notificationManager;
    private Intent serviceIntent;
    private static final int MILLI_IN_MINUTE = 60000;
    private static final int MILLI_IN_SECOND = 1000;
    private static final int SIX_HOURS = 6 * 60 * 60000;
    private long startTime;
    private boolean pauseState = false;

    TextView waterTime;
    TextView stretchTime;
    TextView foodTime;
    TextView otherTime;

    StudySession session;

    private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String keyID = intent.getStringExtra("name");
            String timeString = intent.getStringExtra("time");

            updateText(keyID, timeString);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_timer);

        waterTime = findViewById(R.id.water_time);
        stretchTime = findViewById(R.id.stretch_time);
        foodTime = findViewById(R.id.food_time);
        otherTime = findViewById(R.id.other_time);

        session = getIntent().getParcelableExtra("session");
        startTime = getIntent().getLongExtra("startTime", SIX_HOURS);
        notificationManager = NotificationManagerCompat.from(this);

        /* These next few lines are for the Service, if we get it running. */
        Intent serviceIntent = new Intent(this, TimerService.class);
        serviceIntent.putExtra("session", this.session);
        startService(serviceIntent);
        /* End of Service code */
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(br, new IntentFilter(TimerService.DISPLAY_TIMER_BR));
        System.out.println("resume");
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(br);
    }

    @Override
    public void onStop() {
        try {
            unregisterReceiver(br);
        } catch (Exception e) {
            System.out.println("Already unregistered");
        }

        super.onStop();
    }

    public void updateText(String taskname, String timeRemaining) {
        switch (taskname) {
            case ("water"):
                waterTime.setText(timeRemaining);
                break;
            case ("stretch"):
                stretchTime.setText(timeRemaining);
                break;
            case("food"):
                foodTime.setText(timeRemaining);
                break;
            case("other"):
                otherTime.setText(timeRemaining);
                break;
            default:
                break;
        }

        long timeLong = Long.parseLong(timeRemaining.trim());
        if (timeLong == 0) {

            Interruption[] interruptions = session.getInterruptions();

            for (Interruption interruption : interruptions) {
                if (interruption.getName().toLowerCase().equals(taskname.toLowerCase())) {
                    alert(interruption.getName());
                    startInterruption(interruption, timeLong);
                }
            }
        }
    }

    public void pauseButton(View view) {
        Button pauseButton = findViewById(R.id.pauseButton);
        if (this.pauseState) {
            pauseButton.setText("Pause");
            this.pauseState = false;
        } else {
            pauseButton.setText("Unpause");
            this.pauseState = true;
        }
    }

    public void stopTimer(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        Intent serviceIntent = new Intent(this, TimerService.class);
        stopService(serviceIntent);
    }

    private void startInterruption(Interruption task, long timeLeft) {
        Intent intent = new Intent(DisplayTimer.this, DisplayInterruption.class);
        intent.putExtra("task", task);
        intent.putExtra("session", session);
        intent.putExtra("timeLeft", timeLeft);
        startActivity(intent);
    }

    public void alert(String taskName) {
        String title = "Time to take a break!";
        String alertMessage;
        switch (taskName) {
            case ("Water"):
                alertMessage = "Have a drink of water.";
                break;
            case ("Stretch"):
                alertMessage = "Get up and have a stretch.";
                break;
            case ("Food"):
                alertMessage = "Have something to eat.";
                break;
            default:
                alertMessage = "Take a few minutes to relax.";
                break;
        }

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_alarm)
                .setContentTitle(title)
                .setContentText(alertMessage)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .build();

        notificationManager.notify(1,notification);
    }
}
