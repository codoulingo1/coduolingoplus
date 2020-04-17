package com.example.coduolingo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.HashMap;

public class freedumQs extends AppCompatActivity {

    ImageButton continueBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freedum_qs);
        freedumQs();
        continueBtn = (ImageButton) findViewById(R.id.continueBtn1);
        //continueBtn.setBackgroundColor(Color.TRANSPARENT);
    }

    public void freedumQs() {
        final Button opt1 = findViewById(R.id.Opt1);
        final Button opt2 = findViewById(R.id.Opt2);
        final Button opt3 = findViewById(R.id.Opt3);
        final Button opt4 = findViewById(R.id.Opt4);
        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setProgress(LessonActivity.pr);
        final HashMap<String, String> freedum_hashmap = LessonActivity.shared_hashmap;
        TextView fr = (TextView)findViewById(R.id.freedumQuestion);
        fr.setText(freedum_hashmap.get("qs"));
        String[] optAns = freedum_hashmap.get("Content").split(",");
        final String freedum_id = LessonActivity.shared_id;
        final String freedum_name = LessonActivity.shared_name;
        opt1.setText(optAns[0]);
        opt2.setText(optAns[1]);
        try {
            opt3.setText(optAns[2]);
        }
        catch (Exception e) {
            opt3.setVisibility(View.INVISIBLE);
        }
        try {
            opt4.setText(optAns[3]);
        }
        catch (Exception e) {
            opt4.setVisibility(View.INVISIBLE);
        }
        opt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (opt1.getText().toString().equals(freedum_hashmap.get("Answer"))){
                    LessonActivity.j++;
                    startActivity(new Intent(freedumQs.this, LessonActivity.class));
                }
                //when play is clicked show stop button and hide play button

            }
        });opt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when play is clicked show stop button and hide play button
                if (opt2.getText().toString().equals(freedum_hashmap.get("Answer"))){
                    startActivity(new Intent(freedumQs.this, LessonActivity.class));
                }

            }
        });opt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (opt3.getText().toString().equals(freedum_hashmap.get("Answer"))){
                    startActivity(new Intent(freedumQs.this, LessonActivity.class));
                }

            }
        });opt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (opt4.getText().toString().equals(freedum_hashmap.get("Answer"))){
                    startActivity(new Intent(freedumQs.this, LessonActivity.class));
                }

            }
        });

    }
}
