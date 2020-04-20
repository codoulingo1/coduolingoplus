package com.example.coduolingo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ExplainationQS extends AppCompatActivity {
    public static ImageView ExpImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explaination_q_s);
        ExpImage = (ImageView) findViewById(R.id.imageView);
        String url = LessonActivity.shared_hashmap.get("Image"); //paste here google drive picture shareable link but change "open?" to "uc?"
        if(!url.equals("none")){
            Picasso.with(this).load(url).resizeDimen(R.dimen.image_size, R.dimen.image_size).placeholder(R.drawable.goj).into(ExpImage);
        }
        Log.d("url", url);
        explanationQs();

    }


    public void explanationQs() {
        Button continueBtn;
        continueBtn = (Button) findViewById(R.id.continueBtn);
        HashMap<String, String> explanation_hashmap = LessonActivity.shared_hashmap;
        TextView fr = (TextView)findViewById(R.id.Explanation);
        fr.setText(explanation_hashmap.get("qs"));
        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setProgress(LessonActivity.pr);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LessonActivity.j++;
                startActivity(new Intent(ExplainationQS.this, LessonActivity.class));
            }
        });
    }
}
