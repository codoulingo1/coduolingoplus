package com.getcodly.codly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.List;


public class Tree_new extends AppCompatActivity {

    ImageView lessonStart;
    RelativeLayout skill1;
    Drawable unwrappedDrawable;
    Drawable wrappedDrawable;
    public static String[] practiceID;
    public static String LessonType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_new);
        lessonStart = (ImageView) findViewById(R.id.lessonStart);
        skill1 = (RelativeLayout) findViewById(R.id.skill12);


        /*skill1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lessonStart.setImageResource(R.drawable.lesson_circle_lvl2);
            }
        });*/

        skill1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    void startLesson(List<String> id, String name){
        //String old_progress = String.valueOf(date.get("cProgress"));
        LessonType = "";
        /*for(String d : id){
            List<String> str_old_progress = Arrays.asList(old_progress.split(""));
            if(!str_old_progress.contains(d)) {
                MainActivity.id = d;
                MainActivity.name = name;
                startActivity(new Intent(Tree_new.this, MainActivity.class));
                overridePendingTransition(0,0);
            }
        }*/
    }
    void startPractice (String[] id) {
        LessonType = "practice";
        MainActivity.id = "prac";
        MainActivity.name = "";
        practiceID = id;
        startActivity(new Intent(Tree_new.this, MainActivity.class));
    }

}
