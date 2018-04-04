////////////////////////////////////////////////////////////////////////////////
//
//  MidiDriver - An Android Midi Driver.
//
//  Copyright (C) 2013	Bill Farmer
//
//  This program is free software; you can redistribute it and/or modify
//  it under the terms of the GNU General Public License as published by
//  the Free Software Foundation; either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU General Public License for more details.
//
//  You should have received a copy of the GNU General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.
//
//  Licensed under the Apache License, Version 2.0 (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.
//
//  Bill Farmer	 william j farmer [at] yahoo [dot] co [dot] uk.
//
///////////////////////////////////////////////////////////////////////////////

package org.billthefarmer.miditest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
    implements View.OnTouchListener, View.OnClickListener
{
    private TextView text;
    private Quiz currentQuiz = GenerateQuiz.generate3partMajorOnePartTooHighQuiz();
    private int answer = -1;
    private int quizCounter = 1;
    private int score = 0;
    protected MidiPlayer midiPlayer;
    private int totalAmountOfQuizes = 10;
    public static final String SCORE = "org.billthefarmer.miditest.SCORE";
    public static final String TOTALAMOUNTOFQUIZES = "org.billthefarmer.miditest.TOTALAMOUNTOFQUIZES";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        midiPlayer = new MidiPlayer();


        // Set on touch listener
        View v = findViewById(R.id.c);
            if (v != null)
                v.setOnTouchListener(this);
        Button checkButton = (Button) findViewById(R.id.check_button);
        if (checkButton != null)
            checkButton.setOnClickListener(this);
        checkButton.setText("Check");
        }



    // On resume
    @Override
    protected void onResume(){
	    super.onResume();
        midiPlayer.onResume();
    }

    // On pause
    @Override
    protected void onPause(){
        super.onPause();
        midiPlayer.onPause();
    }

    // On touch

    @Override
    public boolean onTouch(View v, MotionEvent event){
	int action = event.getAction();
	int id = v.getId();

	switch (action){
	    // Down

	case MotionEvent.ACTION_DOWN:
	    switch (id){
	    case R.id.c:
            midiPlayer.playChord(currentQuiz.getChord(), currentQuiz.getTuneList());
		break;

	    default:
		return false;
	    }

	    v.performClick();
	    break;

	    // Up

	case MotionEvent.ACTION_UP:
	    switch (id){
	        case R.id.c:
                midiPlayer.stopChord(currentQuiz.getChord());
                break;

	        default:
                return false;
	    }
	    break;

        default:
            return false;
    }

	return false;
    }



    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.voice1:
                if (checked)
                    answer = 0;
                    break;
            case R.id.voice2:
                if (checked)
                    answer = 1;
                    break;
            case R.id.voice3:
                if (checked)
                    answer = 2;
                    break;
        }
    }


    @Override
    public void onClick(View view) {
        Button but = (Button) findViewById(R.id.check_button);
        RadioButton r1 =(RadioButton) findViewById(R.id.voice1);
        RadioButton r2 =(RadioButton) findViewById(R.id.voice2);
        RadioButton r3 =(RadioButton) findViewById(R.id.voice3);
        TextView counter = (TextView) findViewById(R.id.textView2);
        if(answer == -1)
            return;
        if(but.getText() == "Check") {
            switch (currentQuiz.getAnswer()) {
                default:
                    break;
                case 0:
                    r1.setTextColor(Color.GREEN);
                    r2.setTextColor(Color.RED);
                    r3.setTextColor(Color.RED);
                    break;
                case 1:
                    r1.setTextColor(Color.RED);
                    r2.setTextColor(Color.GREEN);
                    r3.setTextColor(Color.RED);
                    break;
                case 2:
                    r1.setTextColor(Color.RED);
                    r2.setTextColor(Color.RED);
                    r3.setTextColor(Color.GREEN);
                    break;
            }
            if (answer == currentQuiz.getAnswer()) {
                but.setText("Good Job!");
                score++;
                but.setBackgroundColor(Color.GREEN);
            } else {
                but.setText("Nope!");
                but.setBackgroundColor(Color.RED);
            }

        }
        else{
            r1.setTextColor(Color.BLACK);
            r2.setTextColor(Color.BLACK);
            r3.setTextColor(Color.BLACK);

            but.setBackgroundResource(android.R.drawable.btn_default);
            but.setText("Check");
            r1.setChecked(false);
            r2.setChecked(false);
            r3.setChecked(false);
            currentQuiz = GenerateQuiz.generate3partMajorOnePartTooHighQuiz();
            quizCounter++;
            answer = -1;

            counter.setText(quizCounter + "/" + totalAmountOfQuizes);

        }
        if(quizCounter >= totalAmountOfQuizes){
            Intent intent = new Intent(this, ResultsPage.class);
            intent.putExtra(SCORE, score);
            intent.putExtra(TOTALAMOUNTOFQUIZES, totalAmountOfQuizes);
            score = 0;
            quizCounter = 1;
            counter.setText(quizCounter + "/" + totalAmountOfQuizes);
            startActivity(intent);
        }
    }
    }

