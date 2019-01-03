package com.ciarasouthgate.studybreak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static final int MILLI_IN_MINUTE = 60000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configureStartButton();
    }

    public void configureStartButton() {
        // Adds onclick listener to start_button
        // Changes activity to DisplayTimer
        Button startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StudySession session;

                int breakNo = 4;

                EditText waterTime = findViewById(R.id.water_time);
                EditText stretchTime = findViewById(R.id.stretch_time);
                EditText foodTime = findViewById(R.id.food_time);
                EditText otherTime = findViewById(R.id.other_time);

                long waterLong = Long.parseLong(waterTime.getText().toString()) * MILLI_IN_MINUTE;
                long stretchLong = Long.parseLong(stretchTime.getText().toString()) * MILLI_IN_MINUTE;
                long foodLong = Long.parseLong(foodTime.getText().toString()) * MILLI_IN_MINUTE;
                long otherLong = Long.parseLong(otherTime.getText().toString()) * MILLI_IN_MINUTE;

                Interruption water = new Interruption("Water", waterLong, 1 * MILLI_IN_MINUTE);
                Interruption stretch = new Interruption("Stretch", stretchLong, 10 * MILLI_IN_MINUTE);
                Interruption food = new Interruption("Food", foodLong, 20 * MILLI_IN_MINUTE);
                Interruption other = new Interruption("Other", otherLong, 30 * MILLI_IN_MINUTE);

                Interruption[] i = new Interruption[]{water, stretch, food, other};

                session = new StudySession(i);

                Intent intent = new Intent(MainActivity.this, DisplayTimer.class);
                intent.putExtra("session", session);
                intent.putExtra("startTime", 21600000L);
                startActivity(intent);
            }
        });
    }
}