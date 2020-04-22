package com.example.coduolingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class tree extends AppCompatActivity {

    Button skill1;
    Button skill2;
    Button skill3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree);

        skill1 = (Button) findViewById(R.id.skill1);
        skill2 = (Button) findViewById(R.id.skill2);
        skill3 = (Button) findViewById(R.id.skill3);

        skill1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLesson("57983", "Math");
            } //savta
        });

        skill2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLesson("57932", "HI");
            }
        });
    }

    void startLesson(String id, String name){
        MainActivity.id = id;
        MainActivity.name = name;

        startActivity(new Intent(tree.this, MainActivity.class));
    }
}
