package com.ciarasouthgate.studybreak;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import static com.ciarasouthgate.studybreak.App.CHANNEL_1_ID;

public class DisplayTimer extends AppCompatActivity {
    private NotificationManagerCompat notificationManager;
    private Intent serviceIntent;
    private static final int MILLI_IN_MINUTE = 60000;
    private static final int MILLI_IN_SECOND = 1000;
    private static final int SIX_HOURS = 6 * 60 * 60000;
    private long startTime;

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

        waterTime = findViewById(R.id.waterTime);
        stretchTime = findViewById(R.id.stretchTime);
        foodTime = findViewById(R.id.foodTime);
        otherTime = findViewById(R.id.otherTime);

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
        System.out.println(timeRemaining);

        long timeLong = Long.parseLong(timeRemaining.trim());
        if (timeLong * MILLI_IN_MINUTE <= 10000) {

            Interruption[] interruptions = session.getInterruptions();

            for (Interruption interruption : interruptions) {
                if (interruption.getName().equals(taskname)) {
                    startInterruption(interruption, timeLong);
                }
            }
        }
    }
    public void countdownStudy(long runningTime, final Interruption[] tasks) {
        new CountDownTimer(runningTime, MILLI_IN_MINUTE) {
            public void onTick(long millisUntilFinished) {
                for (Interruption task : tasks) {
                    long remainingTime = millisUntilFinished % task.getInterval();
                    String timeString;
                    if (remainingTime < MILLI_IN_MINUTE) {
                        timeString = "<1 ";
                    } else {
                        timeString = Long.toString(remainingTime / MILLI_IN_MINUTE) + " ";
                    }
                    switch (task.getName()) {
                        case ("water"):
                            System.out.println("water remainder: " + remainingTime);
                            waterTime.setText(timeString);
                            break;
                        case ("stretch"):
                            System.out.println("stretch remainder: " + remainingTime);
                            stretchTime.setText(timeString);
                            break;
                        case("food"):
                            System.out.println("food remainder: " + remainingTime);
                            foodTime.setText(timeString);
                            break;
                        case("other"):
                            System.out.println("other remainder: " + remainingTime);
                            otherTime.setText(timeString);
                            break;
                        default:
                            break;
                    }
                    if (remainingTime - MILLI_IN_MINUTE <= 10000) {
                        System.out.println("timer up!");
                        startInterruption(task, millisUntilFinished);
//                        countdownStudy(millisUntilFinished, tasks);
                        cancel();
                    }
                }
            }

            public void onFinish() {
                startActivity(new Intent(DisplayTimer.this, Goodbye.class));
            }
        }.start();
    }

    private void startInterruption(Interruption task, long leftOnTimer) {
        System.out.println("started other method");
        Intent intent = new Intent(DisplayTimer.this, DisplayInterruption.class);
        intent.putExtra("task", task);
        startActivity(intent);
    }

    public void alert() {
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_alarm)
//                .setContentTitle(title)
//                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .build();

        notificationManager.notify(1,notification);
    }
}
