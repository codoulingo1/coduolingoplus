package com.example.coduolingo;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class LessonActivity extends AppCompatActivity {

    public static HashMap<String, String> shared_hashmap;
    TextView qs;
    Button submit;
    Boolean isRight;
    public static String shared_id;
    public static String shared_name;
    public static int j;
    public static int is_back;
    float maxJ;
    ProgressBar pb;
    public static int pr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_lesson);
        pb = (ProgressBar) findViewById(R.id.progressBar2);
        String id = MainActivity.id;
        String name = MainActivity.name;
        for (int i = 1; i<20; i++){
            try {
                loadquestion(id, name, String.valueOf(i));
            }
            catch (Exception e) {
                maxJ = i-1;
                break;
            }
        }
        if (j>1 & j<maxJ+1){
            try {
                //pb.setMax(100); // 100 maximum value for the progress value
                lessonCreator(id, name, j);
            }
            catch(Exception e) {

            }
        }
        else if (j<=1) {
            lessonCreator(id, name, 1);
        }
        else {
            qs = (TextView) findViewById(R.id.textView4);
            int b = 100 * (j);
            pr = Math.round(b/maxJ);
            pr = Math.round(b/maxJ);
            for (int numTodel=1; numTodel<=maxJ; numTodel++){
                File dirName = new File(Environment.getExternalStorageDirectory() + "/" + "id" + "/");
                boolean a = false;
                Log.d("bona", "bona");

                try {
                    FileUtils.deleteDirectory(dirName);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("malbona", "malbona");
                }
                Log.d(String.valueOf(a), String.valueOf(a));
                //Log.d(String.valueOf(file.exists()), String.valueOf(deleted));

            }
            pb.setProgress(pr);
            pb.setProgress(pr);
            qs.setText("כל הכבוד!");

        }
    }

    public HashMap<String, String> loadquestion(String id, String name, String qs_num) {
        HashMap<String, String> hashMap = DownloadReadlessons.readqs(id, name, qs_num); // read qs by ID + name + question number
        return hashMap;

    }

    public void lessonCreator(final String ID, final String name, final int i){
        final HashMap<String, String> hashMap = loadquestion(ID, name, String.valueOf(i));
        j = i;
        shared_id = ID;
        shared_name = name;
        shared_hashmap = hashMap;
        progress();
        if (hashMap.get("type").equals("freedum")){
            startActivity(new Intent(LessonActivity.this, freedumQs.class));
        }
        else if(hashMap.get("type").equals("explain")){
            startActivity(new Intent(LessonActivity.this, ExplainationQS.class));
        }
        else if(hashMap.get("type").equals("nonfreetext")){
            startActivity(new Intent(LessonActivity.this, NonFreedum.class));
        }
        else if(hashMap.get("type").equals("freetext")){
            startActivity(new Intent(LessonActivity.this, FreeText.class));

        }
        else{
            qs.setText("finished");
        }}
    public void progress(){
        int b = 100 * (j-1);
        pr = Math.round(b/maxJ);
        pb.setProgress(pr);
    }

}