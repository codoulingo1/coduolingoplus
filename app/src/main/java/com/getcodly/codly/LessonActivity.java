package com.getcodly.codly;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class LessonActivity extends AppCompatActivity {

    public static HashMap<String, String> shared_hashmap;
    TextView qs;
    Button submit;
    Boolean isRight;
    public static String shared_id;
    public static String shared_name;
    public static Double shared_xp = 0.0;
    public static int j;
    public static int is_back;
    String id;
    float maxJ;
    //ProgressBar pb;
    public static int pr;
    HashMap <String, String> date;
    CountDownTimer mcountdown;
    public static int shared_xp2 = 0;

    ImageButton continueBtn20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_lesson);
        //pb = (ProgressBar) findViewById(R.id.progressBar2);
        id = MainActivity.id;
        String name = MainActivity.name;
        for (int i = 1; i<20; i++){
            try {
                Log.d("hi", loadquestion(id, name, String.valueOf(i)).get("qs"));
            }
            catch (Exception e) {
                maxJ = i-1;
                break;
            }
        }
        if (j>1 & j<maxJ+1){
            try {
                //pb.setMax(100); // 100 maximum value for the progress valuea
                lessonCreator(id, name, j);
            }
            catch(Exception e) {

            }
        }
        else if (j<=1) {
            lessonCreator(id, name, 1);
        }
        else {
            Log.d("via avino", String.valueOf(maxJ));
            startActivity(new Intent(LessonActivity.this, finalLesson.class));
        }

        continueBtn20 = (ImageButton) findViewById(R.id.continueBtn2000);

        continueBtn20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LessonActivity.this, tree.class));
            }
        });
    }

    public HashMap<String, String> loadquestion(String id, String name, String qs_num) {
        Log.d("id: ", id + "id");
        HashMap<String, String> hashMap = DownloadReadlessons.readqs(id, name, qs_num, LessonActivity.this); // read qs by ID + name + question number
        return hashMap;
    }

    public void lessonCreator(final String ID, final String name, final int i){
        final HashMap<String, String> hashMap = loadquestion(ID, name, String.valueOf(i));
        Log.d(hashMap.toString(), hashMap.toString());
        j = i;
        shared_id = ID;
        shared_name = name;
        shared_hashmap = hashMap;
        progress();
        Log.d("j", String.valueOf(j));
        try {
            if (hashMap.get("type").equals("freedum")) {
                Log.d("finished", "finished1");//
                startActivity(new Intent(LessonActivity.this, freedumQs.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        else if(hashMap.get("type").equals("explain")){
            Log.d("finished", "finished2");
            if (mainScreen.LessonType.equals("comp")){
                LessonActivity.j = LessonActivity.j + 1;
                startActivity(new Intent(LessonActivity.this, LessonActivity.class));
            }else {
                startActivity(new Intent(LessonActivity.this, ExplainationQS.class));
            }
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        else if(hashMap.get("type").equals("nonfreetext") || hashMap.get("type").equals("pynonfreetext")){
            Log.d("finished", "finished3");
            startActivity(new Intent(LessonActivity.this, NonFreedum.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        else if(hashMap.get("type").equals("freetext")){
            Log.d("finished", "finished4");
            startActivity(new Intent(LessonActivity.this, FreeTextR.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        else if(hashMap.get("type").equals("freepy")){
            Log.d("finished", "finished5");
            startActivity(new Intent(LessonActivity.this, FreeTextPy.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        else if(hashMap.get("type").equals("freehtml")){
            Log.d("finished", "finished6");
            startActivity(new Intent(LessonActivity.this, FreeText.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        else if(hashMap.get("type").equals("nextQS")) {
            LessonActivity.j = LessonActivity.j + 1;
            startActivity(new Intent(LessonActivity.this, LessonActivity.class));
        }
        else if(hashMap.get("type").equals("runpy") || hashMap.get("type").equals("showpy")){
            Log.d("finished", "finished7");
            if (mainScreen.LessonType.equals("comp")){
                LessonActivity.j = LessonActivity.j + 1;
                startActivity(new Intent(LessonActivity.this, LessonActivity.class));
            }
            else {
                startActivity(new Intent(LessonActivity.this, runQs.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        }
        else if(hashMap.get("type").equals("explainhtml")){
            Log.d("finished", "finished8");
            startActivity(new Intent(LessonActivity.this, ExplainHtml.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        else if(hashMap.get("type").equals("testpy")){
            Log.d("finished", "finished8");
            startActivity(new Intent(LessonActivity.this, TestPy.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        else{
            Log.d("finished", "finished10");
        }
        } catch (Exception e){
            Toast.makeText(LessonActivity.this, "האינטרנט שלך לא יציב. מנסה שוב...", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LessonActivity.this, MainActivity.class));
        }
        }
    public void progress(){
        int b = 100 * (j-1);
        pr = Math.round(b/maxJ);
        //pb.setProgress(pr);
    }
    @Override
    public void onBackPressed() {
        DialogBack dialogBack = new DialogBack();
        dialogBack.show(getSupportFragmentManager(), "Example Dialog");
    }

}