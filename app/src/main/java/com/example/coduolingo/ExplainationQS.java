package com.example.coduolingo;

import androidx.appcompat.app.AppCompatActivity;

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
    ImageView ExpImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explaination_q_s);
        ExpImage = (ImageView) findViewById(R.id.imageView);
        String url = "https://drive.google.com/uc?id=1fqsUIwrvW1QY-xckfaE2izIwxd9iOy7J"; //paste here google drive picture shareable link but change "open?" to "uc?"
        Picasso.with(this).load(url).resizeDimen(R.dimen.image_size, R.dimen.image_size).into(ExpImage);
        explanationQs();
    }


    public void explanationQs() {
        Button continueBtn;
        continueBtn = (Button) findViewById(R.id.continueBtn);
        HashMap<String, String> explanation_hashmap = MainActivity.shared_hashmap;
        TextView fr = (TextView)findViewById(R.id.Explanation);
        fr.setText(explanation_hashmap.get("qs")); //Savta shel nadav asoia megojim
        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setProgress(MainActivity.pr);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.j++;
                startActivity(new Intent(ExplainationQS.this, MainActivity.class));
            }
        });
    }
}
