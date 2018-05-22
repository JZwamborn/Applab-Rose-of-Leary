package com.example.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    Button  button = null;
    TextView textView = null;
    TextView textView2 = null;
    EditText editText = null;
    EditText editText2 = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        button = (Button)findViewById(R.id.button3);
        textView = (TextView) findViewById(R.id.textView4);
        textView2 = (TextView) findViewById(R.id.textView2);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText = (EditText) findViewById(R.id.editText4);
        final String name = editText.getText().toString();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD
                startActivity(new Intent(MainActivity.this, HomeScreen.class));
=======
                startActivity(new Intent(MainActivity.this, ClassifierActivity.class));
>>>>>>> e66c34ea53a35b4950617c4c4bdf53662307de4c
            }
        });
    }
}