package com.example.coduolingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;

public class ExplainationQS extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explaination_q_s);
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
