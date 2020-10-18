package com.getcodly.codly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class tree extends AppCompatActivity {

    //FirebaseAuth mAuth;
    RelativeLayout skill1;
    RelativeLayout skill2;
    RelativeLayout skill3;
    RelativeLayout skill4;
    RelativeLayout skill5;
    ImageView LessonStart1;
    ImageView LessonStart2;
    ImageView LessonStart3;
    ImageView LessonStart4;
    ImageView LessonStart5;
    public static ArrayList<String> idShare;
    public static ArrayList<String> namesShare;
    CountDownTimer mcountdown;
    public static String loadAgain = "";
    public static String[] practiceID;
    public static String LessonType;
    HashMap <String, String> date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_new);
        //mAuth = FirebaseAuth.getInstance();
        mainScreen.lessonWr = 0;
        skill1 = (RelativeLayout) findViewById(R.id.skill15);
        skill2 = (RelativeLayout) findViewById(R.id.skill16);
        skill3 = (RelativeLayout) findViewById(R.id.skill17);
        skill4 = (RelativeLayout) findViewById(R.id.skill18);
        skill5 = (RelativeLayout) findViewById(R.id.skill19);

        LessonStart1 = findViewById(R.id.lessonStart);
        LessonStart2 = findViewById(R.id.lessonStart1);
        LessonStart3 = findViewById(R.id.lessonStart2);
        LessonStart4 = findViewById(R.id.lessonStart3);
        LessonStart5 = findViewById(R.id.lessonStart4);

        File dirName = new File(Environment.getExternalStorageDirectory() + "/" + "id" + "/");
        try {
            FileUtils.deleteDirectory(dirName);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("malbona", "malbona");
        }
        LessonActivity.j = 1;
        mainScreen.w = "py";
        date = DownloadReadlessons.get_last_lesson(ReadWrite.read(this.getFilesDir()+File.separator+ "user"));
        if (ifLfinished(Arrays.asList("1-1-1", "1-1-2"))){
            LessonStart1.setImageResource(R.drawable.lesson_circle_lvl2);
        }
        if (ifHfinished(Arrays.asList("1-1-1", "1-1-2"), Arrays.asList(""))){
            LessonStart1.setImageResource(R.drawable.lesson_circle_lvl0);
        }
        if (ifLfinished(Arrays.asList("1-2-1", "1-2-2", "1-2-3"))){
            LessonStart2.setImageResource(R.drawable.lesson_circle_lvl2);
        }
        if (ifHfinished(Arrays.asList("1-2-1", "1-2-2", "1-2-3"), Arrays.asList("1-1-2"))){
            LessonStart2.setImageResource(R.drawable.lesson_circle_lvl0);
        }
        if (ifLfinished(Arrays.asList("1-3-1", "1-3-2"))){
            LessonStart3.setImageResource(R.drawable.lesson_circle_lvl2);
        }
        if (ifHfinished(Arrays.asList("1-3-1", "1-3-2"), Arrays.asList("1-2-3"))){
            LessonStart3.setImageResource(R.drawable.lesson_circle_lvl0);
        }
        if (ifLfinished(Arrays.asList("1-5-1"))){
            LessonStart4.setImageResource(R.drawable.lesson_circle_lvl2);
        }
        if (ifHfinished(Arrays.asList("1-5-1"), Arrays.asList("1-3-2"))){
            LessonStart4.setImageResource(R.drawable.lesson_circle_lvl0);
        }
        if (ifLfinished(Arrays.asList("1-4-1"))){
            LessonStart5.setImageResource(R.drawable.lesson_circle_lvl2);
        }
        if (ifHfinished(Arrays.asList("1-4-1"), Arrays.asList("1-3-2"))){
            LessonStart5.setImageResource(R.drawable.lesson_circle_lvl0);
        }
        skill1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLesson(Arrays.asList("1-1-2~הקוד הראשון שלי", "1-1-1~מבוא לפייתון"), Arrays.asList(""));
            } //savta
        });
        skill2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLesson(Arrays.asList("1-2-3~תכנות אינטראקטיבי", "1-2-2~טקסטים ומספרים", "1-2-1~מבוא למשתנים"), Arrays.asList("1-1-2"));
            } //savta
        });
        skill3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLesson(Arrays.asList("1-3-2~הפקודות else וelif", "1-3-1~תנאים"), Arrays.asList("1-2-3"));
            } //savta
        });
        skill4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLesson(Arrays.asList("1-5-1~לולאת while"), Arrays.asList("1-3-2"));
            } //savta
        });
        skill5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLesson(Arrays.asList("1-4-1~שגיאות וחריגים"), Arrays.asList("1-3-2"));
            } //savta
        });
    }

    void startLesson(List<String> id, List<String> id_alt) {
        if (ifLfinished(id_alt)) {
            String old_progress = mainScreen.progress;
            idShare = new ArrayList();
            namesShare = new ArrayList();
            LessonType = "";
            for (String i : id) {
                idShare.add(i.split("~")[0]);
                namesShare.add(i.split("~")[1]);
            }
            for (String d : id) {
                List<String> str_old_progress = Arrays.asList(old_progress.split(" "));
                Log.d(str_old_progress.toString(), String.valueOf(str_old_progress.contains(d.split("~")[0])));
                if (!str_old_progress.toString().contains(d.split("~")[0])) {
                    MainActivity.id = d.split("~")[0];
                    MainActivity.name = d.split("~")[1];
                }
            }
            startActivity(new Intent(tree.this, selectLesson.class));
            overridePendingTransition(0, 0);
        }
    }
    boolean ifLfinished(List<String> id){
        boolean ret = true;
        for (String i : id){
            Log.d("testyTestyTest", mainScreen.progress);
            Log.d("testy2", i);
            if (!mainScreen.progress.contains(i)) {
                ret = false;
                break;
            }
        }
        Log.d(String.valueOf(ret), String.valueOf(ret));
        return ret;
    }
    boolean ifHfinished(List<String> id, List<String> old_id){
        boolean ret = true;
        for (String i : id){
            if (mainScreen.progress.contains(i)) {
                ret = false;
                break;
            }
        }
        if (ifLfinished(old_id)){
            ret = false;
        }
        return ret;
    }
    void startPractice(String[] id) {
        LessonType = "practice";
        MainActivity.id = "prac";
        MainActivity.name = "";
        practiceID = id;
        startActivity(new Intent(tree.this, MainActivity.class));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(tree.this, mainScreen.class));
    }

    /*@Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user){
        if(user == null){
            //not logged in
            startActivity(new Intent(tree.this, Login.class));
        }
    }*/
}