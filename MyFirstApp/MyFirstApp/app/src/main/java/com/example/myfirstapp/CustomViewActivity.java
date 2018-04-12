package com.example.myfirstapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.myfirstapp.DrawBallView;

public class CustomViewActivity extends AppCompatActivity {

    Button  button = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);

        // Get the root Linearlayout object.
        final LinearLayout rootLayout = (LinearLayout)findViewById(R.id.idDrawBallView);

        // Create the DrawBallView custom view object.
        final DrawBallView drawBallView = new DrawBallView(this);

        //set min width and height.
        drawBallView.setMinimumWidth(500);
        drawBallView.setMinimumHeight(800);
        button = (Button)findViewById(R.id.button);
        // Create a ontouch listener object.

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomViewActivity.this, MainActivity.class));
            }
        });

        View.OnTouchListener onTouchListener = new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                // Set drawBallView currX and currY value to user finger x y ordinate value..
                drawBallView.setCurrX(motionEvent.getX());
                drawBallView.setCurrY(motionEvent.getY());

                // Set ball color to blue.
                drawBallView.setBallColor(Color.parseColor("#6ACCCB"));

                // Notify drawBallView to redraw. This will invoke DrawBallView's onDraw() method.
                drawBallView.invalidate();

                // Return true means this listener has complete process this event successfully.
                return true;
            }

        };

            // Register onTouchListener object to drawBallView.
        drawBallView.setOnTouchListener(onTouchListener);

            // Add drawBallView object in root LinearLayout object.
        rootLayout.addView(drawBallView);
        }
    }
