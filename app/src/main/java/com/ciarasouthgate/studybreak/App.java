package com.ciarasouthgate.studybreak;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class App extends Application {
    public static final String CHANNEL_1_ID = "alert";
    public static final String CHANNEL_2_ID = "foregroundService";

    public void onCreate() {
        super.onCreate();

        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel alertChannel = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Alert",
                    NotificationManager.IMPORTANCE_HIGH
            );
            alertChannel.setDescription("Alerts for task change.");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(alertChannel);

            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_2_ID,
                    "Timer running",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            manager.createNotificationChannel(serviceChannel);
        }
    }
}
