package com.ciarasouthgate.studybreak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

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

                long waterLong = Long.parseLong(waterTime.getText().toString());
                long stretchLong = Long.parseLong(stretchTime.getText().toString());
                long foodLong = Long.parseLong(foodTime.getText().toString());
                long otherLong = Long.parseLong(otherTime.getText().toString());

                Interruption water = new Interruption("water", waterLong, 0);
                Interruption stretch = new Interruption("stretch", stretchLong, 10);
                Interruption food = new Interruption("food", foodLong, 20);
                Interruption other = new Interruption("other", otherLong, 30);

                Interruption[] i = new Interruption[]{water, stretch, food, other};

                session = new StudySession(i);

                Intent intent = new Intent(MainActivity.this, DisplayTimer.class);
                intent.putExtra("session", session);
                startActivity(intent);
            }
        });
    }
}