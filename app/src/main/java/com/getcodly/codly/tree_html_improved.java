package com.getcodly.codly;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    RelativeLayout skill6;

    RelativeLayout LessonStart1;
    RelativeLayout LessonStart2;
    RelativeLayout LessonStart3;
    RelativeLayout LessonStart4;
    RelativeLayout LessonStart5;
    RelativeLayout LessonStart6;

    TextView f1;
    TextView f2;
    TextView f3;
    TextView f4;
    TextView f5;
    TextView f6;
    RelativeLayout switchCourseBtn;
    View pageCover;
    RelativeLayout topSheet;

    RelativeLayout pythonCourse;

    RelativeLayout switchToPy;

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
        switchToPy = findViewById(R.id.coursePython);
        skill1 = findViewById(R.id.skill_1);
        skill2 = findViewById(R.id.skill_2);
        skill3 = findViewById(R.id.skill_3);
        skill4 = findViewById(R.id.skill_4);
        skill5 = findViewById(R.id.skill_5);
        skill6 = findViewById(R.id.skill_6);


        pageCover = findViewById(R.id.pageCover);
        topSheet = findViewById(R.id.topSheet);
        switchCourseBtn = findViewById(R.id.button8);

        pythonCourse = findViewById(R.id.coursePython);

        LessonStart1 = findViewById(R.id.skill1picture);
        LessonStart2 = findViewById(R.id.skill2picture);
        LessonStart3 = findViewById(R.id.skill3picture);
        LessonStart4 = findViewById(R.id.skill4picture);
        LessonStart5 = findViewById(R.id.skill5picture);
        LessonStart6 = findViewById(R.id.skill6picture);

        f1 = (TextView) findViewById(R.id.f1);
        f2 = (TextView) findViewById(R.id.f2);
        f3 = (TextView) findViewById(R.id.f3);
        f4 = (TextView) findViewById(R.id.f4);
        f5 = (TextView) findViewById(R.id.f5);
        f6 = (TextView) findViewById(R.id.f6);

        checkIfGreen();
        isTopSheetVisible = false;

        f1.setText(s(Arrays.asList("2-1-1", "2-1-2")));
        f2.setText(s(Arrays.asList("2-2-1")));
        f3.setText(s(Arrays.asList("2-3-1", "2-3-2")));
        f4.setText(s(Arrays.asList("2-4-1", "2-4-2")));
        f5.setText(s(Arrays.asList("2-5-1")));
        f6.setText(s(Arrays.asList("2-6-1", "2-6-2")));

        switchToPy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(tree_html_improved.this, tree.class));
            }
        });

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
        skill6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLesson(Arrays.asList("2-6-1~מבוא לקוד CSS"), Arrays.asList("2-5-1"));
            }
        });

    }

    void checkIfGreen(){
        if (ifLfinished(Arrays.asList("2-1-2", "2-1-1"))){
            LessonStart1.setBackgroundResource(R.drawable.skill2);
        }
        if (ifHfinished(Arrays.asList("2-1-2", "2-1-1"), Arrays.asList(""))){
            LessonStart1.setBackgroundResource(R.drawable.skill);
        }
        if (ifLfinished(Arrays.asList("2-2-1"))){
            LessonStart2.setBackgroundResource(R.drawable.skill2);
        }
        if (ifHfinished(Arrays.asList("2-2-1"), Arrays.asList("2-1-2"))){
            LessonStart2.setBackgroundResource(R.drawable.skill);
        }
        if (ifLfinished(Arrays.asList("2-3-1", "2-3-2"))){
            LessonStart3.setBackgroundResource(R.drawable.skill2);
        }
        if (ifHfinished(Arrays.asList("2-3-1", "2-3-2"), Arrays.asList("2-2-1"))){
            LessonStart3.setBackgroundResource(R.drawable.skill);
        }
        if (ifLfinished(Arrays.asList("2-4-1", "2-4-2"))){
            LessonStart4.setBackgroundResource(R.drawable.skill2);
        }
        if (ifHfinished(Arrays.asList("2-4-1", "2-4-2"), Arrays.asList("2-3-2"))){
            LessonStart4.setBackgroundResource(R.drawable.skill);
        }
        if (ifLfinished(Arrays.asList("2-5-1"))){
            LessonStart5.setBackgroundResource(R.drawable.skill2);
        }
        if (ifHfinished(Arrays.asList("2-5-1"), Arrays.asList("2-4-2"))){
            LessonStart5.setBackgroundResource(R.drawable.skill);
        }
        if (ifLfinished(Arrays.asList("2-6-1"))){
            LessonStart6.setBackgroundResource(R.drawable.skill2);
        }
        if (ifHfinished(Arrays.asList("2-6-1"), Arrays.asList("2-4-2"))){
            LessonStart6.setBackgroundResource(R.drawable.skill);
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
    String s(List<String> id){
        int e = 0;
        for (String i : id){
            if (mainScreen.progress.contains(i)) {
                e = e + 1;
            }
        }
        return String.valueOf(e) + "/" + id.size();
    }
    public void onBackPressed() {
        startActivity(new Intent(tree_html_improved.this, mainScreen.class));
    }
}