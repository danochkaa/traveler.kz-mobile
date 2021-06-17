package com.example.trav;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SingleEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_event);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null){

            int id = extras.getInt("id");

            //extract all the event information using the event id

        }
    }
}
