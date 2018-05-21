package com.example.myfirstapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by frank on 13-5-2018.
 */

public class HomeScreenTraining extends AppCompatActivity {

    Button button_info = null;
    Button button_personal = null;
    Button button_progress = null;
    Button button_training = null;
    TextView textView = null;
    TextView textView2 = null;
    Button train = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen_training);
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textViewClassify);
        train = (Button) findViewById(R.id.training_button);
        button_info = (Button)findViewById(R.id.Button_Dashboard_info);
        button_personal = (Button)findViewById(R.id.Button_Dashboard_personal);
        button_progress = (Button)findViewById(R.id.Button_Dashboard_progress);
        button_training = (Button)findViewById(R.id.Button_Dashboard_training);
        //set listeners main buttons training
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView2.setTextColor(Color.parseColor("#6ACCCB"));
                train.setVisibility(View.VISIBLE);
            }
        });

        train.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(HomeScreenTraining.this, CustomViewActivity.class);
                startActivity(i);
            }

        });
        //Create listeners also on_creatre
        button_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreenTraining.this, HomeScreenInfo.class));
            }
        });
        button_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreenTraining.this, HomeScreenPersonal.class));
            }
        });
        button_progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreenTraining.this, HomeScreenProgress.class));
            }
        });
        button_training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreenTraining.this, HomeScreenTraining.class));
            }
        });
    }
}

