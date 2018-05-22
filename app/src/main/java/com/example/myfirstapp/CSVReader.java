package com.example.myfirstapp;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Franka on 18-4-2018.
 */

public class CSVReader {
    InputStream inputStream;

    public CSVReader(InputStream inputStream){
        this.inputStream = inputStream;
    }

    public String[][] read(){
        LinkedList<String[]> rows = new LinkedList<String[]>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String dataRow;
            while ((dataRow = reader.readLine()) != null){
                rows.addLast(dataRow.split("\\|"));
            }
        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: "+e);
            }
        }
        String[][] csvMatrix = rows.toArray(new String[rows.size()][]);
        return csvMatrix;
    }
}
