package com.getcodly.codly;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class tree_html_improved extends AppCompatActivity {

    RelativeLayout skill1;
    RelativeLayout skill2;
    RelativeLayout skill3;
    RelativeLayout skill4;
    RelativeLayout skill5;

    RelativeLayout LessonStart1;
    RelativeLayout LessonStart2;
    RelativeLayout LessonStart3;
    RelativeLayout LessonStart4;
    RelativeLayout LessonStart5;

    RelativeLayout switchCourseBtn;
    View pageCover;
    RelativeLayout topSheet;

    boolean isTopSheetVisible;

    public static ArrayList<String> idShare;
    public static ArrayList<String> namesShare;

    public static String loadAgain = "";
    public static String[] practiceID;
    public static String LessonType;
    HashMap<String, String> date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tree_improved);
        mainScreen.lessonWr = 0;
        skill1 = findViewById(R.id.skill_1);
        skill2 = findViewById(R.id.skill_2);
        skill3 = findViewById(R.id.skill_3);
        skill4 = findViewById(R.id.skill_4);
        skill5 = findViewById(R.id.skill_5);

        pageCover = findViewById(R.id.pageCover);
        topSheet = findViewById(R.id.topSheet);
        switchCourseBtn = findViewById(R.id.button8);

        LessonStart1 = findViewById(R.id.skill1picture);
        LessonStart2 = findViewById(R.id.skill2picture);
        LessonStart3 = findViewById(R.id.skill3picture);
        LessonStart4 = findViewById(R.id.skill4picture);
        LessonStart5 = findViewById(R.id.skill5picture);


        checkIfGreen();

        isTopSheetVisible = false;

        switchCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isTopSheetVisible){
                    pageCover.setVisibility(View.VISIBLE);
                    topSheet.setVisibility(View.VISIBLE);
                    topSheet.setTranslationY(-600);
                    topSheet.animate().translationY(0).setDuration(300);
                    isTopSheetVisible = true;
                } else{
                    topSheet.animate().translationY(-600).setDuration(300);
                    pageCover.setVisibility(View.GONE);
                    topSheet.setVisibility(View.GONE);
                    isTopSheetVisible = false;
                }
            }
        });

        pageCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageCover.setVisibility(View.GONE);
                topSheet.setVisibility(View.GONE);
                isTopSheetVisible = false;
            }
        });

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
                startLesson(Arrays.asList("2-3-2~עיצוב טקסט", "2-3-1~יצירת האתר הראשון"), Arrays.asList("2-2-1"));
            } //savta
        });
        skill4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLesson(Arrays.asList("2-4-2~תמונות", "2-4-1~תגיות מתקדמות"), Arrays.asList("2-3-2"));
            }
        });
        skill5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLesson(Arrays.asList("2-5-1~כפתורים"), Arrays.asList("2-4-2"));
            }
        });
    }

    void checkIfGreen(){
        if (ifLfinished(Arrays.asList("2-1-2", "2-1-1"))){
            LessonStart1.setBackgroundResource(R.drawable.skill2);
        }
        if (ifHfinished(Arrays.asList("2-1-2", "2-1-1"), Arrays.asList(""))){
            LessonStart1.setBackgroundResource(R.drawable.lesson_circle_lvl0);
        }
        if (ifLfinished(Arrays.asList("2-2-1"))){
            LessonStart2.setBackgroundResource(R.drawable.skill2);
        }
        if (ifHfinished(Arrays.asList("2-2-1"), Arrays.asList("2-1-2"))){
            LessonStart2.setBackgroundResource(R.drawable.lesson_circle_lvl0);
        }
        if (ifLfinished(Arrays.asList("2-3-1", "2-3-2"))){
            LessonStart3.setBackgroundResource(R.drawable.skill2);
        }
        if (ifHfinished(Arrays.asList("2-3-1", "2-3-2"), Arrays.asList("2-2-1"))){
            LessonStart3.setBackgroundResource(R.drawable.lesson_circle_lvl0);
        }
        if (ifLfinished(Arrays.asList("2-4-1", "2-4-2"))){
            LessonStart4.setBackgroundResource(R.drawable.skill2);
        }
        if (ifHfinished(Arrays.asList("2-4-1", "2-4-2"), Arrays.asList("2-3-2"))){
            LessonStart4.setBackgroundResource(R.drawable.lesson_circle_lvl0);
        }
        if (ifLfinished(Arrays.asList("2-5-1"))){
            LessonStart5.setBackgroundResource(R.drawable.skill2);
        }
        if (ifHfinished(Arrays.asList("2-5-1"), Arrays.asList("2-4-2"))){
            LessonStart5.setBackgroundResource(R.drawable.lesson_circle_lvl0);
        }
    }
    void startLesson(List<String> id, List<String> id_alt) {
        if (ifLfinished(id_alt)) {
            String old_progress = mainScreen.progress;
            tree.idShare = new ArrayList();
            tree.namesShare = new ArrayList();
            mainScreen.LessonType = "";
            for (String i : id) {
                tree.idShare.add(i.split("~")[0]);
                tree.namesShare.add(i.split("~")[1]);
            }
            for (String d : id) {
                List<String> str_old_progress = Arrays.asList(old_progress.split(","));
                Log.d(str_old_progress.toString(), String.valueOf(str_old_progress.contains(d.split("~")[0])));
                if (!str_old_progress.toString().contains(d.split("~")[0])) {
                    MainActivity.id = d.split("~")[0];
                    MainActivity.name = d.split("~")[1];
                }
            }
            startActivity(new Intent(tree_html_improved.this, selectLesson.class));
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
        startActivity(new Intent(tree_html_improved.this, mainScreen.class));
    }
}