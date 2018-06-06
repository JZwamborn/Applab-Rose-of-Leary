package com.example.myfirstapp;

import java.io.InputStream;

import android.content.Context;
import android.util.Log;

/**
 * Created by Franka on 16-4-2018.
 */

public class QuestionLibrary {

    Context ctx;
    private String[][] scoreList;
    private String[] Questions;
    private Double[][] RosePositions;
    private String[] Position;

    public QuestionLibrary (Context context) {
        ctx = context;
    }

    public String getQuestion(int a){
        String question = Questions[a];
        return question;
    }

    public String[] getQuestions(){
        return Questions;
    }

    public String[] getPositions(){
        return Position;
    }

    public Double getXPosition(int a){
        Double xpos = RosePositions[a][0];
        return xpos;
    }

    public Double getYPosition(int a){
        Double ypos = RosePositions[a][1];
        return ypos;
    }

    public String getPosition(int a){
        String position = Position[a];
        return position;
    }

    public void readLines() {
        InputStream inputStream = ctx.getResources().openRawResource(R.raw.dataless);
        CSVReader csvFile = new CSVReader(inputStream);
        scoreList = csvFile.read();
    }

    public int getQuestionLength(){
        return Questions.length;
    }

    public void readQuestions(){
        Questions = new String[scoreList.length];
        for(int i = 0; i < scoreList.length; i++) {
            Questions[i] = scoreList[i][0];
        }
    }

    public void readPositions(){
        RosePositions = new Double[scoreList.length][2];
        for(int i = 0; i < scoreList.length; i++) {
            if(scoreList[i].length == 4) {
                RosePositions[i][0] = Double.parseDouble(scoreList[i][2]);
                RosePositions[i][1] = Double.parseDouble(scoreList[i][3]);
            }
        }
    }

    public void readPosition4(){
        Position = new String[scoreList.length];
        for(int i = 0; i < scoreList.length; i++) {
            Position[i] = scoreList[i][1];
        }
    }

}
