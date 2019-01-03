package com.ciarasouthgate.studybreak;

import android.app.Notification;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static com.ciarasouthgate.studybreak.App.CHANNEL_1_ID;

public class DisplayTimer extends AppCompatActivity {
    private NotificationManagerCompat notificationManager;
    private Intent serviceIntent;
    private static final int MILLI_IN_MINUTE = 60000;
    private static final int SIX_HOURS = 6 * 60 * 60000;
    private long startTime;

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

        session = getIntent().getParcelableExtra("session");
        startTime = getIntent().getLongExtra("startTime", SIX_HOURS);
        notificationManager = NotificationManagerCompat.from(this);

        System.out.println(startTime);

        countdownStudy(startTime, session.getInterruptions());

        /* These next few lines are for the Service, if we get it running. */
        Intent serviceIntent = new Intent(this, TimerService.class);
        serviceIntent.putExtra("session", session);

        this.startService(serviceIntent);
        /* End of Service code */
    }

    public void countdownStudy(long runningTime, final Interruption[] tasks) {
        new CountDownTimer(runningTime, MILLI_IN_MINUTE) {
            public void onTick(long millisUntilFinished) {
                for (Interruption task : tasks) {
                    long remainingTime = millisUntilFinished % task.getInterval();
                    String timeString = Long.toString(remainingTime / MILLI_IN_MINUTE) + " ";
                    switch (task.getName()) {
                        case ("Water"):
                            System.out.println("water remainder: " + remainingTime);
                            waterTime.setText(timeString);
                            break;
                        case ("Stretch"):
                            System.out.println("stretch remainder: " + remainingTime);
                            stretchTime.setText(timeString);
                            break;
                        case("Food"):
                            System.out.println("food remainder: " + remainingTime);
                            foodTime.setText(timeString);
                            break;
                        case("Other"):
                            System.out.println("other remainder: " + remainingTime);
                            otherTime.setText(timeString);
                            break;
                        default:
                            break;
                    }
                    if (remainingTime <= MILLI_IN_MINUTE) {
                        alert(task.getName());
                        System.out.println(millisUntilFinished);
                        startInterruption(task, millisUntilFinished);
                        cancel();
                    }
                }
            }

            public void onFinish() {
                startActivity(new Intent(DisplayTimer.this, Goodbye.class));
            }
        }.start();
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
