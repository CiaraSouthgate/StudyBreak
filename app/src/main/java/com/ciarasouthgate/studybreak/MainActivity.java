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

                EditText waterInterval = findViewById(R.id.water_interval);
                EditText stretchInterval = findViewById(R.id.stretch_interval);
                EditText foodInterval = findViewById(R.id.food_interval);
                EditText otherInterval = findViewById(R.id.other_interval);

                EditText waterDuration = findViewById(R.id.water_duration);
                EditText stretchDuration = findViewById(R.id.stretch_duration);
                EditText foodDuration = findViewById(R.id.food_duration);
                EditText otherDuration = findViewById(R.id.other_duration);

                long waterILong = Long.parseLong(waterInterval.getText().toString()) * MILLI_IN_MINUTE;
                long stretchILong = Long.parseLong(stretchInterval.getText().toString()) * MILLI_IN_MINUTE;
                long foodILong = Long.parseLong(foodInterval.getText().toString()) * MILLI_IN_MINUTE;
                long otherILong = Long.parseLong(otherInterval.getText().toString()) * MILLI_IN_MINUTE;

                long waterDLong = Long.parseLong(waterDuration.getText().toString()) * MILLI_IN_MINUTE;
                long stretchDLong = Long.parseLong(stretchDuration.getText().toString()) * MILLI_IN_MINUTE;
                long foodDLong = Long.parseLong(foodDuration.getText().toString()) * MILLI_IN_MINUTE;
                long otherDLong = Long.parseLong(otherDuration.getText().toString()) * MILLI_IN_MINUTE;

                Interruption water = new Interruption("Water", waterILong, waterDLong);
                Interruption stretch = new Interruption("Stretch", stretchILong, stretchDLong);
                Interruption food = new Interruption("Food", foodILong, foodDLong);
                Interruption other = new Interruption("Other", otherILong, otherDLong);

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