package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
/**
 * Created by frank on 13-5-2018.
 */

public class HomeScreenProgress extends AppCompatActivity {

    ImageButton button_info = null;
    ImageButton button_personal = null;
    ImageButton button_progress = null;
    ImageButton button_training = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen_progress);
        button_info = (ImageButton)findViewById(R.id.Button_Dashboard_info);
        button_personal = (ImageButton)findViewById(R.id.Button_Dashboard_personal);
        button_progress = (ImageButton)findViewById(R.id.Button_Dashboard_progress);
        button_training = (ImageButton)findViewById(R.id.Button_Dashboard_training);

        //Create listeners also on_creatre
        button_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreenProgress.this, HomeScreenInfo.class));
            }
        });
        button_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreenProgress.this, HomeScreenPersonal.class));
            }
        });
        button_progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreenProgress.this, HomeScreenProgress.class));
            }
        });
        button_training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreenProgress.this, HomeScreenTraining.class));
            }
        });
    }






}

