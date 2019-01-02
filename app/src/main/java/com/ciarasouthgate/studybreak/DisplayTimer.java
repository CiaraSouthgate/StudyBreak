package com.ciarasouthgate.studybreak;

import android.app.Notification;
import android.content.Intent;
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

        serviceIntent = new Intent(getApplicationContext(), RunInBackground.class);
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
        //TODO outState.putLong("timeLeft", INSERT NAME OF TIME VARIABLE);

    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //TODO INSERT NAME OF TIME VARIABLE = savedInstanceState.getLong("timeLeft");
    }
}
