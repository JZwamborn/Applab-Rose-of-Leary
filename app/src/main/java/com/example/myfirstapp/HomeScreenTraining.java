package com.example.myfirstapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by frank on 13-5-2018.
 */

public class HomeScreenTraining extends AppCompatActivity {

    ImageButton button_info = null;
    ImageButton button_personal = null;
    ImageButton button_progress = null;
    ImageButton button_training = null;
    TextView textView = null;
    TextView textView2 = null;
    TextView textView3 = null;
    boolean classifier = false;
    Button train = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen_training);
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textViewClassify);
        textView3 = (TextView) findViewById(R.id.textView12);
        train = (Button) findViewById(R.id.training_button);
        button_info = (ImageButton)findViewById(R.id.Button_Dashboard_info);
        button_personal = (ImageButton)findViewById(R.id.Button_Dashboard_personal);
        button_progress = (ImageButton)findViewById(R.id.Button_Dashboard_progress);
        button_training = (ImageButton)findViewById(R.id.Button_Dashboard_training);
        //set listeners main buttons training
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classifier = true;
                textView3.setTextColor(Color.parseColor("#808080"));
                textView2.setTextColor(Color.parseColor("#6ACCCB"));
                train.setVisibility(View.VISIBLE);
            }
        });

        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classifier = false;
                textView2.setTextColor(Color.parseColor("#808080"));
                textView3.setTextColor(Color.parseColor("#6ACCCB"));
                train.setVisibility(View.VISIBLE);
            }
        });

        train.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (classifier) {
                    Intent i = new Intent(HomeScreenTraining.this, CustomViewActivity.class);
                    startActivity(i);
                }
                else{
                    Intent i = new Intent(HomeScreenTraining.this, ClassifierActivity.class);
                    startActivity(i);
                }
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

