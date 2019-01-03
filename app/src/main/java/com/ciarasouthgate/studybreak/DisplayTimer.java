package com.ciarasouthgate.studybreak;

import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    TextView waterTime;
    TextView stretchTime;
    TextView foodTime;
    TextView otherTime;

    StudySession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_timer);

        waterTime = findViewById(R.id.waterTime);
        stretchTime = findViewById(R.id.stretchTime);
        foodTime = findViewById(R.id.foodTime);
        otherTime = findViewById(R.id.otherTime);

//        waterTime.setText("test");
//        stretchTime.setText("test");
//        foodTime.setText("test");
//        otherTime.setText("test");

        session = getIntent().getParcelableExtra("session");
        notificationManager = NotificationManagerCompat.from(this);

        countdownStudy(SIX_HOURS, session.getInterruptions());

        /* These next few lines are for the Service, if we get it running. */
        Intent serviceIntent = new Intent(this, TimerService.class);
        //TODO replace with timer variable
        int timer = 0;
        serviceIntent.putExtra("session", this.session);

        startService(serviceIntent);
        /* End of Service code */
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
                            System.out.println("water remainder: " + remainingTime);
                            stretchTime.setText(timeString);
                            break;
                        case("food"):
                            System.out.println("water remainder: " + remainingTime);
                            foodTime.setText(timeString);
                            break;
                        case("other"):
                            System.out.println("water remainder: " + remainingTime);
                            otherTime.setText(timeString);
                            break;
                        default:
                            break;
                    }
                    if (remainingTime <= 30000) {
                        System.out.println("timer up!");
                        startInterruption(task);
                        countdownStudy(millisUntilFinished, tasks);
                        cancel();
                    }
                }
            }

            public void onFinish() {
                startActivity(new Intent(DisplayTimer.this, Goodbye.class));
            }
        }.start();
    }

    private void startInterruption(Interruption task) {
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
