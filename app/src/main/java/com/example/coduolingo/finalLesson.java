package com.example.coduolingo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class finalLesson extends AppCompatActivity {

    TextView xpView;
    String shared_xp3;
    int shared_xp4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_lesson);
        xpView = (TextView) findViewById(R.id.textView20000);
        shared_xp4 = LessonActivity.shared_xp2;
        shared_xp3 = String.valueOf(LessonActivity.shared_xp2);
        xpView.setText(shared_xp3);
    }
}
