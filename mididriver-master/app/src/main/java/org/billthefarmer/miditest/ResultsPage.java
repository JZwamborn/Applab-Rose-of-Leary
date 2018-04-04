package org.billthefarmer.miditest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_page);
        Intent intent = getIntent();
        int score = intent.getIntExtra(MainActivity.SCORE, 0);
        int totalAmountOfQuizes = intent.getIntExtra(MainActivity.TOTALAMOUNTOFQUIZES, 15);
        TextView scoreField = (TextView) findViewById(R.id.scoreField);
        scoreField.setText(score + "/" + totalAmountOfQuizes);
    }
}
