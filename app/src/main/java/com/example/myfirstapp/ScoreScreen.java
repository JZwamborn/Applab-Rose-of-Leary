package com.example.myfirstapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Franka on 7-5-2018.
 */

public class ScoreScreen extends AppCompatActivity{

    TextView textView = null;
    TextView textView2 = null;
    TextView textView3 = null;
    TextView highScore = null;

    Button home = null;
    String score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_screen);
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView6);
        textView3 = (TextView) findViewById(R.id.textView1);
        highScore = (TextView) findViewById(R.id.highscore_label);
        home = (Button) findViewById(R.id.button);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            score = extras.getString("score");
        }
        else{
            score = "0";
        }

        textView3.setText(score);

        SharedPreferences settings = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        int high_score = settings.getInt("HIGH_SCORE", 0);

        if (Integer.parseInt(score) > high_score) {
            highScore.setText("High Score: " + score);

            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("HIGH_SCORE", Integer.parseInt(score));
            editor.commit();
        }
        else{
            highScore.setText("High Score: " + high_score);
        }


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ScoreScreen.this, HomeScreen.class);
                startActivity(i);
            }
        });

    }
}
