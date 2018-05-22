package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
/**
 * Created by frank on 13-5-2018.
 */

public class HomeScreenProgress extends AppCompatActivity {

    Button button_info = null;
    Button button_personal = null;
    Button button_progress = null;
    Button button_training = null;
    BarChart barChart = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen_progress);
        button_info = (Button)findViewById(R.id.Button_Dashboard_info);
        button_personal = (Button)findViewById(R.id.Button_Dashboard_personal);
        button_progress = (Button)findViewById(R.id.Button_Dashboard_progress);
        button_training = (Button)findViewById(R.id.Button_Dashboard_training);

        //Create barchart
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(44f, 0));
        barEntries.add(new BarEntry(88f, 1));
        barEntries.add(new BarEntry(41f, 2));
        barEntries.add(new BarEntry(87f, 3));
        barEntries.add(new BarEntry(20f, 4));
        barEntries.add(new BarEntry(62f, 5));

        BarDataSet barDataSet = new BarDataSet(barEntries, "Dates");

        ArrayList<String> theDates = new ArrayList<>();
        theDates.add("April");
        theDates.add("May");
        theDates.add("June");
        theDates.add("July");
        theDates.add("August");
        theDates.add("September");

        BarData theData = new BarData(theDates, barDataSet);
        barChart.setData(theData);

        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
        barChart = findViewById(R.id.graph);
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


    /*fragment
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        System.out.println("Inflating menu");
        getMenuInflater().inflate(R.menu.homescreen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */




}

