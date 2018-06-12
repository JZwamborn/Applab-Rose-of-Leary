package com.example.myfirstapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    Button  button = null;
    TextView textView = null;
    TextView textView2 = null;
    EditText editText = null;
    //EditText editText2 = null; //old password
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        button = (Button)findViewById(R.id.button3);
        textView = (TextView) findViewById(R.id.textView4);
        textView2 = (TextView) findViewById(R.id.textView2);
        //editText2 = (EditText) findViewById(R.id.editText2);
        editText = (EditText) findViewById(R.id.editText4);


        SharedPreferences settings_user_ID = getSharedPreferences("settings_user_ID",0);
        final SharedPreferences.Editor userID_editor = settings_user_ID.edit();
        SharedPreferences settings = getSharedPreferences("GAME_DATA", 0);
        SharedPreferences.Editor editor = settings.edit();
        Gson gson = new Gson();
        List<String> textList = new ArrayList<String>();
        textList.add("0");
        textList.add("0");
        textList.add("0");
        String jsonText = gson.toJson(textList);
        editor.putString("Scores", jsonText);
        editor.apply();

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                editText.setText("");
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userID = editText.getText().toString();
                userID_editor.putString("UserID",userID);
                userID_editor.apply();
                startActivity(new Intent(MainActivity.this, HomeScreenInfo.class));
            }
        });
    }
}