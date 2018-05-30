package com.example.myfirstapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        SharedPreferences settings = getSharedPreferences("GAME_DATA", 0);
        SharedPreferences.Editor editor = settings.edit();
        int high_score = settings.getInt("HIGH_SCORE", 0);

        if (Integer.parseInt(score) > high_score) {
            highScore.setText("High Score: " + score);

            editor.putInt("HIGH_SCORE", Integer.parseInt(score));
            editor.commit();
        }
        else{
            highScore.setText("High Score: " + high_score);
        }

        int score1 = settings.getInt("SCORE1", 0);
        int score2 = settings.getInt("SCORE2", 0);
        int score3 = settings.getInt("SCORE3", 0);
        int score4 = settings.getInt("SCORE4", 0);
        int score5 = settings.getInt("SCORE5", 0);

        int scoretemp = score2;
        score2 = score1;
        int score2temp = score3;
        score3 = scoretemp;
        scoretemp = score4;
        score4 = score2temp;
        score5 = scoretemp;
        score1 = Integer.parseInt(score);

        editor.putInt("SCORE1", score1);
        editor.commit();
        editor.putInt("SCORE2", score2);
        editor.commit();
        editor.putInt("SCORE3", score3);
        editor.commit();
        editor.putInt("SCORE4", score4);
        editor.commit();
        editor.putInt("SCORE5", score5);
        editor.commit();

        /**String scores = settings.getString("Scores", "null");
        Gson gson = new Gson();
        List<String> textList = new ArrayList<String>();
        textList.add("0");
        textList.add("0");
        textList.add("0");
        String jsonText = gson.toJson(textList);
        editor.putString("Scores", jsonText);
        editor.apply();

        String jsonText2 = settings.getString("Scores", null);
        List<String> textList2 = Arrays.asList(gson.fromJson(jsonText2, String[].class));

        textList2 = new ArrayList<String>(textList2);
        textList2.add(score);

        String jsonText3 = gson.toJson(textList2);
        editor.putString("Scores", jsonText3);
        editor.apply();
        **/
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ScoreScreen.this, HomeScreen.class);
                startActivity(i);
            }
        });

    }
}
