package com.ciarasouthgate.studybreak;

import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static com.ciarasouthgate.studybreak.App.CHANNEL_1_ID;

public class DisplayTimer extends AppCompatActivity {
    private NotificationManagerCompat notificationManager;
    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = NotificationManagerCompat.from(this);
        Intent serviceIntent = new Intent(this, TimerService.class);
        //TODO replace with timer variable
        int timer = 0;
        serviceIntent.putExtra("timer", timer);

        startService(serviceIntent);
    }

    public void notify(View v) {

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_alarm)
//                .setContentTitle(title)
//                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .build();

        notificationManager.notify(1,notification);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //TODO replace with variable name
        long sendTimeVariable = 0;
        outState.putLong("timeLeft", sendTimeVariable);

    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //TODO replace with variable name
        long timeVariable;
        timeVariable = savedInstanceState.getLong("timeLeft");
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
