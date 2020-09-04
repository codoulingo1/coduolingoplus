package com.getcodly.codly;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RelativeLayout;

public class tree_html extends AppCompatActivity {

    RelativeLayout skill1;
    RelativeLayout skill2;
    RelativeLayout skill3;
    RelativeLayout skill4;
    RelativeLayout skill5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_html);

        skill1 = findViewById(R.id.skill15);
        skill2 = findViewById(R.id.skill16);
        skill3 = findViewById(R.id.skill17);
        skill4 = findViewById(R.id.skill18);
        skill5 = findViewById(R.id.skill19);

        LessonActivity.j = 1;

    }
}
