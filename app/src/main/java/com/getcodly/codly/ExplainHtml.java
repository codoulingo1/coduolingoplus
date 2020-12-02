package com.getcodly.codly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

public class ExplainHtml extends AppCompatActivity {
    ImageButton backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain_html);
        backBtn = (ImageButton) findViewById(R.id.backBtn);
        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setProgress(LessonActivity.pr);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (LessonActivity.j > 1) {
                    LessonActivity.j = LessonActivity.j - 1;
                    LessonActivity.shared_xp = LessonActivity.shared_xp - 1;
                    startActivity(new Intent(ExplainHtml.this, LessonActivity.class));
                    overridePendingTransition(0, 0);
                }
            }
        });
    }


}
