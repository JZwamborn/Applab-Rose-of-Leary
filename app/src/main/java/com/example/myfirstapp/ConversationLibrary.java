package com.example.myfirstapp;

import android.content.Context;

import java.io.InputStream;

/**
 * Created by frank on 2-6-2018.
 */

public class ConversationLibrary {

    Context ctx;
    private String[][] conversationList;
    private String[] sentences;
    private String[] partnerGoal;
    private String[] yourGoal;

    public ConversationLibrary (Context context) {
        ctx = context;
    }

    public int getSentencesLength(){
        return sentences.length;
    }

    public String getSentences(int a){
        String sentence = sentences[a];
        return sentence;
    }

    public String getYourGoal(int a){
        String goal = yourGoal[a];
        return goal;
    }

    public String getPartnerGoal(int a){
        String goal = partnerGoal[a];
        return goal;
    }

    public void readLines() {
        InputStream inputStream = ctx.getResources().openRawResource(R.raw.conversation);
        CSVReader csvFile = new CSVReader(inputStream);
        conversationList = csvFile.read();
    }

    public void readSentences(){
        sentences = new String[conversationList.length];
        for(int i = 0; i < conversationList.length; i++) {
            sentences[i] = conversationList[i][0];
        }
    }

    public void readGoal(){
        yourGoal = new String[conversationList.length];
        partnerGoal = new String[conversationList.length];
        for(int i = 0; i < conversationList.length; i++) {
            yourGoal[i] = conversationList[i][2];
            partnerGoal[i] = conversationList[i][1];
        }
    }





}
