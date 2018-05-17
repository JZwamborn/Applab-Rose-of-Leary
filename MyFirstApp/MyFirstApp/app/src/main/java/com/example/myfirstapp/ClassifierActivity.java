package com.example.myfirstapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


public class ClassifierActivity extends AppCompatActivity {
    Button  button = null;
    TextView textView = null;
    TextView textView2 = null;
    EditText editText = null;
    EditText editText2 = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classifier);
        button = (Button)findViewById(R.id.button3);
        //textView = (TextView) findViewById(R.id.textView4);
        //textView2 = (TextView) findViewById(R.id.textView2);
        //editText2 = (EditText) findViewById(R.id.editText2);
        editText = (EditText) findViewById(R.id.editText4);
        final String name = editText.getText().toString();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SendMessage().execute(editText.getText().toString());
                editText.getText().clear();
            }
        });
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
                    Socket socket = new Socket("192.168.0.104",8888);
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
            editText.setText(line);
        }
    }
}