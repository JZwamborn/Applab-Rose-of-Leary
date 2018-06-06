package com.example.myfirstapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    Button  button = null;
    EditText editText = null;
    ImageView LO = null;
    ImageView RB = null;
    ImageView LB = null;
    ImageView RO = null;
    ImageView rose = null;
    TextView tx = null;
    private String goal;
    private int questionNumber = 0;
    ConversationLibrary conversationLibrary;
    ArrayList<Integer> numbers = new ArrayList<Integer>();

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
        conversationLibrary = new ConversationLibrary(this);
        conversationLibrary.readLines();
        conversationLibrary.readSentences();
        conversationLibrary.readGoal();

        Random randomGenerator = new Random();
        while(numbers.size() < 5){
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

        final String name = editText.getText().toString();
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
            return "below and against";
        }
        else if(label.equals("LB")){
            return "above and against";
        }
        return "";
    }

    private void updateSentences() {
        editText.setText("Type your response to the situation");
        goal = labelToText(conversationLibrary.getPartnerGoal(numbers.get(questionNumber)));
        tx.setText(Html.fromHtml("<b> The situation: </b>" + conversationLibrary.getSentences(numbers.get(questionNumber)) + "<br>Your goal is to get your conversation partner in the: " + "<b> <br>" + labelToText(conversationLibrary.getPartnerGoal(numbers.get(questionNumber))) + "</b> part of the rose </br></br>"));
        questionNumber++;
        Log.i("LOG_TAG", "correct pos: " + conversationLibrary.getYourGoal(numbers.get(questionNumber)));
    }

    private void updateAnswer(String reaction){
        if(reaction.equals(goal)) {
            if (questionNumber < 5) {
                updateSentences();
            } else if (questionNumber == 5) {
                Intent i = new Intent(ClassifierActivity.this, ScoreScreen.class);
                startActivity(i);
            } else {
                Intent i = new Intent(ClassifierActivity.this, ScoreScreen.class);
                startActivity(i);
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
            if(line.equals("[\'RB\']")){
                updateAnswer("RB");
                RB.setVisibility(View.VISIBLE);
                RO.setVisibility(View.INVISIBLE);
                LB.setVisibility(View.INVISIBLE);
                rose.setVisibility(View.INVISIBLE);
                LO.setVisibility(View.INVISIBLE);
            }
            else if(line.equals("[\'LO\']")){
                updateAnswer("LO");
                RB.setVisibility(View.INVISIBLE);
                RO.setVisibility(View.INVISIBLE);
                LB.setVisibility(View.INVISIBLE);
                rose.setVisibility(View.INVISIBLE);
                LO.setVisibility(View.VISIBLE);
            }
            else if(line.equals("[\'RO\']")){
                updateAnswer("RO");
                RB.setVisibility(View.INVISIBLE);
                RO.setVisibility(View.VISIBLE);
                LB.setVisibility(View.INVISIBLE);
                rose.setVisibility(View.INVISIBLE);
                LO.setVisibility(View.INVISIBLE);
            }
            else if (line.equals("[\'LB\']")){
                updateAnswer("LB");
                RB.setVisibility(View.INVISIBLE);
                RO.setVisibility(View.INVISIBLE);
                LB.setVisibility(View.VISIBLE);
                rose.setVisibility(View.INVISIBLE);
                LO.setVisibility(View.INVISIBLE);
            }
        }
    }
}