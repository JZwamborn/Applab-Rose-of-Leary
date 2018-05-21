package com.example.myfirstapp;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Dashboard_standard extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //on startup set to progress
        setContentView(R.layout.activity_dashboard_standard);
        Button info_button = (Button) findViewById(R.id.Button_Dashboard_info);
        info_button.setOnClickListener(this); // calling onClick() method
        Button personal_button = (Button) findViewById(R.id.Button_Dashboard_personal);
        info_button.setOnClickListener(this); // calling onClick() method
        Button progress_button = (Button) findViewById(R.id.Button_Dashboard_progress);
        info_button.setOnClickListener(this); // calling onClick() method
        Button training_button = (Button) findViewById(R.id.Button_Dashboard_training);
        info_button.setOnClickListener(this); // calling onClick() method

        //create fragments
        Fragment f1 = new ProgressPage();
        Fragment f2 = new InfoPage();
    }

    //onclick buttons - swap fragment view?
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.Button_Dashboard_info:
                // load fragment with right view.
                setContentView(findViewById(R.id.info_fragment));
                break;

            case R.id.Button_Dashboard_personal:
                // do your code
                break;

            case R.id.Button_Dashboard_progress:
                // do your code
                break;
            case R.id.Button_Dashboard_training:
                // do your code
                break;

            default:
                break;
        }
    }

//    setContentView(R.layout.news_articles);
}
