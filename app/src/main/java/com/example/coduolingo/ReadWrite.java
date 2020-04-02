package com.example.coduolingo;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by bobyo on 17/03/2020.
 */

public class ReadWrite {public static void write(String name, String data,Context context) {
    try {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(name + ".txt", Context.MODE_PRIVATE));
        outputStreamWriter.write(data);
        outputStreamWriter.close();
        Log.d("ok", "file wrote");
    }
    catch (IOException e) {
        Log.e("Exception", "File write failed: " + e.toString());
    }


}
    public static String read(String name, Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(name +".txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("read file", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("read file", "Can not read file: " + e.toString());
        }

        return ret;
    }
}
