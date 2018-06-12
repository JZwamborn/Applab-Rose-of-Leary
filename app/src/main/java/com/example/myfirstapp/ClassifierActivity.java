package com.example.myfirstapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;


public class ClassifierActivity extends AppCompatActivity {
    Button  button;
    Button finish;
    Button next;
    EditText editText;
    ImageView LO;
    ImageView RB;
    ImageView LB;
    ImageView RO;
    ImageView rose;
    TextView tx;
    ProgressBar progressBar;
    TextView feedback;
    private String goal;
    private int questionNumber = 0;
    ConversationLibrary conversationLibrary;
    ArrayList<Integer> numbers = new ArrayList<Integer>();
    private boolean partner = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classifier);
        button = (Button)findViewById(R.id.button3);
        editText = (EditText) findViewById(R.id.editText4);
        rose = (ImageView) findViewById(R.id.imageView2);
        LB = (ImageView) findViewById(R.id.imageView3);
        RO = (ImageView) findViewById(R.id.imageView4);
        RB = (ImageView) findViewById(R.id.imageView5);
        LO = (ImageView) findViewById(R.id.imageView6);
        tx = (TextView) findViewById(R.id.textView4);
        finish = (Button) findViewById(R.id.button5);
        next = (Button) findViewById(R.id.button4);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        progressBar.setProgress(0);
        feedback = (TextView) findViewById(R.id.feedback);
        conversationLibrary = new ConversationLibrary(this);
        conversationLibrary.readLines();
        conversationLibrary.readSentences();
        conversationLibrary.readGoal();

        Random randomGenerator = new Random();
        while(numbers.size() < 6){
            int random = randomGenerator.nextInt(conversationLibrary.getSentencesLength());
            if(!numbers.contains(random)){
                numbers.add(random);
            }
        }

        updateSentences();
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                editText.setText("");
            }
        });


        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent i = new Intent(ClassifierActivity.this, HomeScreen.class);
                startActivity(i);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                editText.setText("");
                updateSentences();
                RB.setVisibility(View.INVISIBLE);
                RO.setVisibility(View.INVISIBLE);
                LB.setVisibility(View.INVISIBLE);
                rose.setVisibility(View.VISIBLE);
                LO.setVisibility(View.INVISIBLE);


            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendMessage().execute(editText.getText().toString());
                editText.getText().clear();
            }
        });
    }

    private String labelToText(String label){
        if(label.equals("RO")){
            return "below and together";
        }
        else if(label.equals("RB")){
            return "above and together";
        }
        else if(label.equals("LO")){
            return "below and opposite";
        }
        else if(label.equals("LB")){
            return "above and opposite";
        }
        return "";
    }


    private void updateSentences() {
        next.setVisibility(View.INVISIBLE);
        button.setVisibility(View.VISIBLE);
        editText.setText("Type your response to the situation");
        goal = conversationLibrary.getPartnerGoal(numbers.get(questionNumber));
        Log.i("LOG_TAG ", goal);
        if(goal.equals("NONE")){
            goal = conversationLibrary.getYourGoal(numbers.get(questionNumber));
            partner = false;
            tx.setText(Html.fromHtml("<h3> " + conversationLibrary.getSentences(numbers.get(questionNumber)) + ".</h3> Your goal is to get (yourself) in the: " + "<b><font color=\"#668c74\">" + labelToText(conversationLibrary.getYourGoal(numbers.get(questionNumber))) + "</font></b> part of the rose"));
        }
        else{
            partner = true;
            tx.setText(Html.fromHtml("<h3>" + conversationLibrary.getSentences(numbers.get(questionNumber)) + ".</h3> Your goal is to get your conversation partner in the: " + "<b><font color=\"#668c74\">" + labelToText(conversationLibrary.getPartnerGoal(numbers.get(questionNumber))) + "</font></b> part of the rose"));
        }
        questionNumber++;
        Log.i("LOG_TAG", "correct pos: " + conversationLibrary.getYourGoal(numbers.get(questionNumber)));
    }

    private void updateAnswer(String reaction){
        if(reaction.equals(goal)) {
            if (questionNumber < 5) {
                feedback.setText("Great job!");
                progressBar.setProgress(questionNumber);
                next.setVisibility(View.VISIBLE);
                button.setVisibility(View.INVISIBLE);
            } else if (questionNumber == 5) {
                finish.setVisibility(View.VISIBLE);
                next.setVisibility(View.INVISIBLE);
                button.setVisibility(View.INVISIBLE);
                feedback.setText("Congratulations! You finsished the excersise");
            } else {
                finish.setVisibility(View.VISIBLE);
                next.setVisibility(View.INVISIBLE);
                button.setVisibility(View.INVISIBLE);
                feedback.setText("Congratulations! You finsished the excersise");

            }
        }
    }

    class SendMessage extends AsyncTask<String, Void, String> {
        private Exception exception;
        private String result;

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            try {
                try {
                    System.out.println("Connecting");
                    Socket socket = new Socket("213.124.174.119",8888);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    System.out.println("start loop");
                    PrintWriter outToServer = new PrintWriter(
                            new OutputStreamWriter(
                                    socket.getOutputStream()));
                    System.out.println("Send");
                    outToServer.print(params[0]);
                    outToServer.flush();
                    String line = new String();
                    //while (() {
                    while ((line = in.readLine()) != null) {
                        System.out.println("Read");
                        System.out.println(line); //here you process you line result
                        result = line;
                    }
                    System.out.println("Close stuff");
                    outToServer.close();
                    in.close();


                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                this.exception = e;
            }
            return result;
        }

        @Override
        protected void onPostExecute(String line) {
            if(line != null) {
                if(line.equals("[\'RB\']")){
                    if(partner){
                        feedback.setText(Html.fromHtml("Your partner (<font color=\"#a26100\">orange</font>) is now together and below, you (<font color=\"#396e82\">blue</font>) are now together and above"));
                        updateAnswer("RO");
                    }
                    else{
                        feedback.setText(Html.fromHtml("You (<font color=\"#396e82\">blue</font>) are now together and above, your partner (<font color=\"#a26100\">orange</font>) is now together and below"));
                        updateAnswer("RB");
                    }
                    RB.setVisibility(View.VISIBLE);
                    RO.setVisibility(View.INVISIBLE);
                    LB.setVisibility(View.INVISIBLE);
                    rose.setVisibility(View.INVISIBLE);
                    LO.setVisibility(View.INVISIBLE);
                }
                else if(line.equals("[\'LO\']")){
                    if(partner){
                        feedback.setText(Html.fromHtml("Your partner (<font color=\"#a26100\">orange</font>) is now opposite and above, you (<font color=\"#396e82\">blue</font>) are now opposite and below"));
                        updateAnswer("LB");
                    }
                    else{
                        feedback.setText(Html.fromHtml("You (<font color=\"#396e82\">blue</font>) are now together and above, your partner (<font color=\"#a26100\">orange</font>) is now opposite and below"));
                        updateAnswer("LO");
                    }
                    RB.setVisibility(View.INVISIBLE);
                    RO.setVisibility(View.INVISIBLE);
                    LB.setVisibility(View.INVISIBLE);
                    rose.setVisibility(View.INVISIBLE);
                    LO.setVisibility(View.VISIBLE);
                }
                else if(line.equals("[\'RO\']")){
                    if(partner){
                        feedback.setText(Html.fromHtml("Your partner (<font color=\"#ff7f27\">orange</font>) is now together and above, you (<font color=\"#00a2e8\">blue</font>) are now together and below"));
                        updateAnswer("RB");
                    }
                    else{
                        feedback.setText(Html.fromHtml("You (<font color=\"#00a2e8\">blue</font>) are now together and below, your partner (<font color=\"#ff7f27\">orange</font>) is now together and above"));
                        updateAnswer("RO");
                    }
                    RB.setVisibility(View.INVISIBLE);
                    RO.setVisibility(View.VISIBLE);
                    LB.setVisibility(View.INVISIBLE);
                    rose.setVisibility(View.INVISIBLE);
                    LO.setVisibility(View.INVISIBLE);
                }
                else if (line.equals("[\'LB\']")){
                    if(partner){
                        feedback.setText(Html.fromHtml("Your partner (<font color=\"#a26100\">orange</font>) is now opposite and below, you (<font color=\"#396e82\">blue</font>) are now opposite and above"));
                        updateAnswer("LO");
                    }
                    else{
                        feedback.setText(Html.fromHtml("You (<font color=\"#396e82\">blue</font>) are now opposite and above, your partner (<font color=\"#a26100\">orange</font>) is now together and below"));
                        updateAnswer("LB");
                    }
                    RB.setVisibility(View.INVISIBLE);
                    RO.setVisibility(View.INVISIBLE);
                    LB.setVisibility(View.VISIBLE);
                    rose.setVisibility(View.INVISIBLE);
                    LO.setVisibility(View.INVISIBLE);
                }
            }
            else{
                editText.setText("No connection to server");
            }
        }
    }
}
