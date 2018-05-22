package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by frank on 13-5-2018.
 */

public class HomeScreenInfo extends AppCompatActivity {

    Button button_info = null;
    Button button_personal = null;
    Button button_progress = null;
    Button button_training = null;
    TextView information = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen_info);
        information = (TextView)findViewById(R.id.textView11);
        information.setMovementMethod(new ScrollingMovementMethod());
        button_info = (Button)findViewById(R.id.Button_Dashboard_info);
        button_personal = (Button)findViewById(R.id.Button_Dashboard_personal);
        button_progress = (Button)findViewById(R.id.Button_Dashboard_progress);
        button_training = (Button)findViewById(R.id.Button_Dashboard_training);
        //Create listeners also on_creatre
        button_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreenInfo.this, HomeScreenInfo.class));
            }
        });
        button_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreenInfo.this, HomeScreenPersonal.class));
            }
        });
        button_progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreenInfo.this, HomeScreenProgress.class));
            }
        });
        button_training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreenInfo.this, HomeScreenTraining.class));
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

