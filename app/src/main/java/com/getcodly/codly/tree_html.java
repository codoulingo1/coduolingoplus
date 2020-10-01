package com.getcodly.codly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class tree_html extends AppCompatActivity {

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

    public static String loadAgain = "";
    public static String[] practiceID;
    public static String LessonType;
    HashMap<String, String> date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_html);

        skill1 = findViewById(R.id.skill15);
        skill2 = findViewById(R.id.skill16);
        skill3 = findViewById(R.id.skill17);
        skill4 = findViewById(R.id.skill18);

        LessonStart1 = findViewById(R.id.lessonStartHtml1);
        LessonStart2 = findViewById(R.id.lessonStartHtml2);
        LessonStart3 = findViewById(R.id.lessonStartHtml3);
        LessonStart4 = findViewById(R.id.lessonStartHtml4);


        checkIfGreen();

        LessonActivity.j = 1;
        mainScreen.w = "html";
        skill1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLesson(Arrays.asList("2-1-2~יצירת פסקאות", "2-1-1~מבוא לפיתוח אתרים"), Arrays.asList(""));
            } //savta
        });
        skill2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLesson(Arrays.asList("2-2-1~מבנה של אתר"), Arrays.asList("2-1-2"));
            } //savta
        });
        skill3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLesson(Arrays.asList("2-3-2~יצירת האתר הראשון 2", "2-3-1~יצירת האתר הראשון"), Arrays.asList("2-2-1"));
            } //savta
        });
        skill4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLesson(Arrays.asList("2-4-1~תגיות מתקדמות"), Arrays.asList("2-3-2"));
            }
        });
    }

    void checkIfGreen(){
        if (ifLfinished(Arrays.asList("2-1-2", "2-1-1"))){
            LessonStart1.setImageResource(R.drawable.lesson_circle_lvl2);
        }
        if (ifHfinished(Arrays.asList("2-1-2", "2-1-1"), Arrays.asList(""))){
            LessonStart1.setImageResource(R.drawable.lesson_circle_lvl0);
        }
        if (ifLfinished(Arrays.asList("2-2-1"))){
            LessonStart2.setImageResource(R.drawable.lesson_circle_lvl2);
        }
        if (ifHfinished(Arrays.asList("2-2-1"), Arrays.asList("2-1-2"))){
            LessonStart2.setImageResource(R.drawable.lesson_circle_lvl0);
        }
        if (ifLfinished(Arrays.asList("2-3-1", "2-3-2"))){
            LessonStart3.setImageResource(R.drawable.lesson_circle_lvl2);
        }
        if (ifHfinished(Arrays.asList("2-3-1", "2-3-2"), Arrays.asList("2-2-1"))){
            LessonStart3.setImageResource(R.drawable.lesson_circle_lvl0);
        }
        if (ifLfinished(Arrays.asList("2-4-1"))){
            LessonStart4.setImageResource(R.drawable.lesson_circle_lvl2);
        }
        if (ifHfinished(Arrays.asList("2-4-1"), Arrays.asList("2-3-2"))){
            LessonStart4.setImageResource(R.drawable.lesson_circle_lvl0);
        }
    }
    void startLesson(List<String> id, List<String> id_alt) {
        if (ifLfinished(id_alt)) {
            String old_progress = mainScreen.progress;
            tree.idShare = new ArrayList();
            tree.namesShare = new ArrayList();
            tree.LessonType = "";
            for (String i : id) {
                tree.idShare.add(i.split("~")[0]);
                tree.namesShare.add(i.split("~")[1]);
            }
            for (String d : id) {
                List<String> str_old_progress = Arrays.asList(old_progress.split(" "));
                Log.d(str_old_progress.toString(), String.valueOf(str_old_progress.contains(d.split("~")[0])));
                if (!str_old_progress.toString().contains(d.split("~")[0])) {
                    MainActivity.id = d.split("~")[0];
                    MainActivity.name = d.split("~")[1];
                }
            }
            startActivity(new Intent(tree_html.this, selectLesson.class));
            overridePendingTransition(0, 0);
        }
    }
    boolean ifLfinished(List<String> id){
        boolean ret = true;
        for (String i : id){
            if (!mainScreen.progress.contains(i)) {
                ret = false;
            }
        }
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
    public void onBackPressed() {
        startActivity(new Intent(tree_html.this, mainScreen.class));
    }
}