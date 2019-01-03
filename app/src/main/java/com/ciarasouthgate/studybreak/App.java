package com.ciarasouthgate.studybreak;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class App extends Application {
    public static final String CHANNEL_1_ID = "startBreakAlert";
    public static final String CHANNEL_2_ID = "endBreakAlert";

    public void onCreate() {
        super.onCreate();

        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Break start/end alert",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("Start/end of break");

            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_2_ID,
                    "Break end alert",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel2.setDescription("End of break");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }
    }
}
