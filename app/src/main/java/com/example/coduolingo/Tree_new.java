package com.example.coduolingo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Tree_new extends AppCompatActivity {

    ImageView lessonStart;
    RelativeLayout skill1;
    Drawable unwrappedDrawable;
    Drawable wrappedDrawable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_new);
        lessonStart = (ImageView) findViewById(R.id.lessonStart);
        skill1 = (RelativeLayout) findViewById(R.id.skill12);


        skill1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lessonStart.setImageResource(R.drawable.lesson_circle_lvl2);
            }
        });

    }
}
