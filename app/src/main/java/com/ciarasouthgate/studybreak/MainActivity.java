package com.ciarasouthgate.studybreak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void onAddButton(View view) {
//        PopupMenu popup = new PopupMenu(this, view);
//        MenuInflater inflater = popup.getMenuInflater();
//        inflater.inflate(R.menu.menu_example, popup.getMenu());
//        popup.show();
//        if (this.menuState) {
//            this.menuState = false;
//
//
//        } else {
//            this.menuState = true;
//
//        }
    }

    public void onStartTimer(View view) {
        // do something
    }
}