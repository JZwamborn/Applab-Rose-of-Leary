package com.example.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.androidplot.ui.HorizontalPositioning;
import com.androidplot.ui.VerticalPositioning;
import com.androidplot.util.PixelUtils;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.StepMode;
import com.androidplot.xy.XValueMarker;
import com.androidplot.xy.XYLegendWidget;
import java.text.DecimalFormat;
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
        int score1 = settings.getInt("SCORE1", 0);
        int score2 = settings.getInt("SCORE2", 0);
        int score3 = settings.getInt("SCORE3", 0);
        int score4 = settings.getInt("SCORE4", 0);
        int score5 = settings.getInt("SCORE5", 0);

        final Number[] domainLabels = {1, 2, 3, 4, 5};
        Number[] intscores = {score1, score2, score3, score4, score5};
        Number[] scores = {0,1,2,3,10};

        //Adding a White Background setting Borders etc..
        plot.setBorderStyle(XYPlot.BorderStyle.NONE, null, null);
        plot.setPlotMargins(0, 0, 0, 0);
        plot.setPlotPadding(0, 0, 0, 0);
        plot.setBackgroundColor(Color.WHITE);

        //Domain
        plot.setDomainStep(StepMode.INCREMENT_BY_VAL, 1);
        // get rid of the decimal place on the display:
        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.LEFT).setFormat(new DecimalFormat("#"));


        // Reduce the number of Domain labels
        //multitouchPlot.setTicksPerDomainLabel(3);

        //Range
        plot.setRangeStep(StepMode.INCREMENT_BY_VAL, 2);
        // get rid of the decimal place on the display:
        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.LEFT).setFormat(new DecimalFormat("#"));

        plot.setRangeBoundaries(0, 10, BoundaryMode.GROW);
        plot.setDomainBoundaries(0, 5, BoundaryMode.GROW);

        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(intscores), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "");

        XYSeries series3 = new SimpleXYSeries(
                Arrays.asList(scores), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "");

        plot.getLegend().setVisible(false);
        plot.getDomainTitle().position(0, HorizontalPositioning.ABSOLUTE_FROM_CENTER, 0, VerticalPositioning.RELATIVE_TO_BOTTOM);
        //LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.parseColor("#6ACCCB"), null, Color.parseColor("#808080"), null);

        LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.parseColor("#668c74"), Color.parseColor("#668c74"), null, null);
        series1Format.getVertexPaint().setStrokeWidth(PixelUtils.dpToPix(11));
        //PointLabelFormatter  plf = new PointLabelFormatter();
        //plf.getTextPaint().setTextSize(40);
        //plf.getTextPaint().setColor(Color.BLACK);
        Paint paint = series1Format.getLinePaint();
        paint.setStrokeWidth(10);
        series1Format.setLinePaint(paint);
        //series1Format.setPointLabelFormatter(plf);

        series1Format.setInterpolationParams(
                new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));

        plot.addSeries(series1, series1Format);

        XYSeries series2b = new SimpleXYSeries(
                Arrays.asList(intscores), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "");

        LineAndPointFormatter series2bFormat = new LineAndPointFormatter(null, Color.parseColor("#dcdcdc"), null, null);
        LineAndPointFormatter series3Format = new LineAndPointFormatter(null, null, null, null);
        // this adjusts the size of the inner circle:
        series2bFormat.getVertexPaint().setStrokeWidth(PixelUtils.dpToPix(7));


        // make sure this line comes AFTER the addSeries call for the copied series:
        plot.addSeries(series2b, series2bFormat);
        plot.addSeries(series3, series3Format);

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

