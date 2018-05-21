package com.example.myfirstapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by Franka on 7-5-2018.
 */

public class Home extends AppCompatActivity {

    TextView textView = null;
    TextView textView2 = null;
    Button train;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView6);
        train = (Button) findViewById(R.id.train);


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
                Intent i = new Intent(Home.this, CustomViewActivity.class);
                startActivity(i);
            }

        });

    }
}
