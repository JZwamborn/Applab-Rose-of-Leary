package com.example.myfirstapp;
import android.support.v7.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myfirstapp.ColorTool;
import com.example.myfirstapp.QuestionLibrary;
import com.example.myfirstapp.R;
import com.example.myfirstapp.ScoreScreen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class CustomViewActivity extends AppCompatActivity implements View.OnTouchListener
{

    Button button;
    ImageView im;
    ImageView click;
    TextView tx;
    TextView answer;
    ImageView lbGreen;
    ImageView lbRed;
    ImageView rbGreen;
    ImageView rbRed;
    ImageView loGreen;
    ImageView loRed;
    ImageView roGreen;
    ImageView roRed;

    private QuestionLibrary questionLibrary = new QuestionLibrary(this);
    private int questionNumber = 0;
    private int score;

    ArrayList<Integer> numbers = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        score = 0;
        questionLibrary.readLines();
        questionLibrary.readQuestions();
        questionLibrary.readPositions();
        questionLibrary.readPosition4();

        // Create the DrawBallView custom view object.
        button = (Button) findViewById(R.id.button);
        tx = (TextView) findViewById(R.id.textView4);
        im = (ImageView) findViewById(R.id.imageView5);
        lbGreen = (ImageView) findViewById(R.id.imageView1);
        lbRed = (ImageView) findViewById(R.id.imageView2);

        loGreen = (ImageView) findViewById(R.id.imageView3);
        loRed = (ImageView) findViewById(R.id.imageView4);

        rbGreen = (ImageView) findViewById(R.id.imageView10);
        rbRed = (ImageView) findViewById(R.id.imageView9);

        roGreen = (ImageView) findViewById(R.id.imageView6);
        roRed = (ImageView) findViewById(R.id.imageView8);

        answer = (TextView) findViewById(R.id.textView5);
        click = (ImageView) findViewById(R.id.imageView7);

        im.setClickable(true);
        im.setOnTouchListener(this);

        Random randomGenerator = new Random();
        while(numbers.size() < 10){
            int random = randomGenerator.nextInt(questionLibrary.getQuestionLength());
            //randomNumberList[i] = getRandomNumberInRange(0, );
            if(!numbers.contains(random)){
                numbers.add(random);
            }
        }

        updateQuestions();

        // Create a ontouch listener object.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionNumber < 9) {
                    updateQuestions();
                    updateAnswer2();
                    button.setVisibility(View.INVISIBLE);
                    im.setVisibility(View.VISIBLE);
                    im.setClickable(true);
                    lbGreen.setVisibility(View.INVISIBLE);
                    lbRed.setVisibility(View.INVISIBLE);
                    loGreen.setVisibility(View.INVISIBLE);
                    loRed.setVisibility(View.INVISIBLE);
                    rbGreen.setVisibility(View.INVISIBLE);
                    rbRed.setVisibility(View.INVISIBLE);
                    roGreen.setVisibility(View.INVISIBLE);
                    roRed.setVisibility(View.INVISIBLE);
                }
                else if (questionNumber == 10){
                    Intent i = new Intent(CustomViewActivity.this, ScoreScreen.class);
                    Log.i("tag", String.valueOf(score));
                    i.putExtra("score", String.valueOf(score));
                    startActivity(i);

                }
                else{
                    Intent i = new Intent(CustomViewActivity.this, ScoreScreen.class);
                    Log.i("tag", String.valueOf(score));
                    i.putExtra("score", String.valueOf(score));
                    startActivity(i);
                }
            }
        });
    }


    public boolean onTouch (View v, MotionEvent ev) {
        boolean handledHere = false;

        final int action = ev.getAction();

        final int evX = (int) ev.getX();
        final int evY = (int) ev.getY();
        int nextImage = -1;			// resource id of the next image to display


        // When the action is Down, see if we should show the "pressed" image for the default image.
        // We do this when the default image is showing. That condition is detectable by looking at the
        // tag of the view. If it is null or contains the resource number of the default image, display the pressed image.
        Integer tagNum = (Integer) im.getTag ();

        // Now that we know the current resource being displayed we can handle the DOWN and UP events.

        switch (action) {

            case MotionEvent.ACTION_UP :
                // On the UP, we do the click action.
                // The hidden image (image_areas) has three different hotspots on it.
                // The colors are red, blue, and yellow.
                // Use image_areas to determine which region the user touched.
                int touchColor = getHotspotColor (R.drawable.clickablerose, evX, evY);

                // Compare the touchColor to the expected values. Switch to a different image, depending on what color was touched.
                // Note that we use a Color Tool object to test whether the observed color is close enough to the real color to
                // count as a match. We do this because colors on the screen do not match the map exactly because of scaling and
                // varying pixel density.
                ColorTool ct = new ColorTool ();
                int tolerance = 60;
                nextImage = R.drawable.roos;

                if (ct.closeMatch (Color.RED, touchColor, tolerance)){
                    processClick("Red");
                }
                else if (ct.closeMatch (Color.BLUE, touchColor, tolerance)) {
                    processClick("Blue");

                }
                else if (ct.closeMatch (Color.YELLOW, touchColor, tolerance)){
                    processClick("Yellow");
                }
                else if (ct.closeMatch (Color.GREEN, touchColor, tolerance)){
                    processClick("Green");
                }

                handledHere = true;
                break;

            default:
                handledHere = false;
        }

        if (handledHere) {
            if (nextImage > 0) {
                im.setImageResource(nextImage);
                im.setTag(nextImage);
            }
        }
        return handledHere;
    }


    private String setTextColor(String color){
        String positionOnRose;
        if(color.equals("Yellow")){
            positionOnRose = "Together and below";
        }
        else if(color.equals("Red")){
            positionOnRose = "Together and above";
        }
        else if(color.equals("Blue")){
            positionOnRose = "Opposed and above";
        }
        else{
            positionOnRose = "Opposed and below";
        }
        return positionOnRose;
    }

    private String setTextPos(String position){
        String posText;
        if(position.equals("RO")){
            posText = "together and below";
        }
        else if(position.equals("RB")){
            posText = "together and above";
        }
        else if(position.equals("LB")){
            posText = "opposed and above";
        }
        else{
            posText = "opposed and below";
        }

        return posText;
    }

    private void processClick(String color){
        button.setVisibility(View.VISIBLE);
        im.setClickable(false);
        String pos = questionLibrary.getPosition(numbers.get(questionNumber -1));
        String positionOnRose = setTextColor(color);
        String posText = setTextPos(pos);

        if(color.equals("Red")){
            if(pos.equals("RB")){
                score += 1;
                updateAnswer1(true, positionOnRose, posText);
                im.setVisibility(View.INVISIBLE);
                rbGreen.setVisibility(View.VISIBLE);
            }
            else{
                updateAnswer1(false, positionOnRose, posText);
                im.setVisibility(View.INVISIBLE);
                rbRed.setVisibility(View.VISIBLE);

            }
        }
        else if(color.equals("Blue")){
            if(pos.equals("LB")){
                score += 1;
                updateAnswer1(true, positionOnRose, posText);
                im.setVisibility(View.INVISIBLE);
                lbGreen.setVisibility(View.VISIBLE);

            }
            else{
                updateAnswer1(false, positionOnRose, posText);
                im.setVisibility(View.INVISIBLE);
                lbRed.setVisibility(View.VISIBLE);
            }
        }
        else if(color.equals("Yellow")){
            if(pos.equals("RO")){
                score += 1;
                updateAnswer1(true, positionOnRose, posText);
                im.setVisibility(View.INVISIBLE);
                roGreen.setVisibility(View.VISIBLE);
            }
            else{
                updateAnswer1(false, positionOnRose, posText);
                im.setVisibility(View.INVISIBLE);
                roRed.setVisibility(View.VISIBLE);
            }
        }
        else{
            if(pos.equals("LO")){
                score += 1;
                updateAnswer1(true, positionOnRose, posText);
                im.setVisibility(View.INVISIBLE);
                loGreen.setVisibility(View.VISIBLE);
            }
            else{
                updateAnswer1(false, positionOnRose, posText);
                im.setVisibility(View.INVISIBLE);
                loRed.setVisibility(View.VISIBLE);
            }
        }
    }

    private void updateAnswer1(boolean correct, String pressed, String correct_position) {
        if (correct){
            answer.setText(Html.fromHtml(pressed  + " is "+ "<font color=\"#00cc00\">" + "correct!" + "</font><br>"
                    + "Your score is: " + score));
        }
        else{
            answer.setText(Html.fromHtml(pressed  + " is "+ "<font color=red>" + "incorrect," + "</font><br>"
                    + "the correct place was: " + "<b>" + correct_position + "</b>"));
        }

    }

    private void updateAnswer2() {
        answer.setText("");
    }

    private void updateQuestions() {
        tx.setText(questionLibrary.getQuestion(numbers.get(questionNumber)));
        questionNumber++;
        Log.i("LOG_TAG", "correct pos: " + questionLibrary.getPosition(numbers.get(questionNumber)));
    }

    public int getHotspotColor (int hotspotId, int x, int y) {
        ImageView img = (ImageView) findViewById (R.id.imageView7);
        if (img == null) {
            Log.d ("ImageAreasActivity", "Hot spot image not found");
            return 0;
        } else {
            img.setDrawingCacheEnabled(true);
            Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache());
            if (hotspots == null) {
                Log.d ("ImageAreasActivity", "Hot spot bitmap was not created");
                return 0;
            } else {
                img.setDrawingCacheEnabled(false);
                return hotspots.getPixel(x, y);
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

    }
}
