package com.example.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.gson.Gson;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by frank on 13-5-2018.
 */

public class HomeScreenProgress extends AppCompatActivity {

    ImageButton button_info = null;
    ImageButton button_personal = null;
    ImageButton button_progress = null;
    ImageButton button_training = null;
    //List<Integer> scores;
    private XYPlot plot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen_progress);
        plot = (XYPlot) findViewById(R.id.plot);
        button_info = (ImageButton)findViewById(R.id.Button_Dashboard_info);
        button_personal = (ImageButton)findViewById(R.id.Button_Dashboard_personal);
        button_progress = (ImageButton)findViewById(R.id.Button_Dashboard_progress);
        button_training = (ImageButton)findViewById(R.id.Button_Dashboard_training);

        SharedPreferences settings = getSharedPreferences("GAME_DATA", 0);
        Gson gson = new Gson();
        String jsonText = settings.getString("Scores", "null");
        String[] scores = gson.fromJson(jsonText, String[].class);
        final Number[] domainLabels = new Number[scores.length];
        Number[] intscores = new Number[scores.length];

        int i=0;
        for (String str : scores){
            Log.d("message, ", str);
            intscores[i] = Integer.parseInt(str);
            domainLabels[i] = i;
            i++;
        }

        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(intscores), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Scores");

        LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.RED, Color.GREEN, Color.BLUE, null);

        series1Format.setInterpolationParams(
                new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));

        plot.addSeries(series1, series1Format);

        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new Format() {
            @Override
            public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
                int i = Math.round(((Number) obj).floatValue());
                return toAppendTo.append(domainLabels[i]);
            }
            @Override
            public Object parseObject(String source, ParsePosition pos) {
                return null;
            }
        });



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

