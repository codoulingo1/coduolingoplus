package com.example.coduolingo;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FreeText extends AppCompatActivity {
    TextView qs;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_text);
        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setProgress(MainActivity.pr);
        submit = (Button) findViewById(R.id.button);
        qs = (TextView) findViewById(R.id.textView);
        Log.d("finished", "freetext");
        qs.setText(MainActivity.shared_hashmap.get("qs"));
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inp = (EditText) findViewById(R.id.inp);
                String ans = inp.getText().toString();
                if (ans.equals(MainActivity.shared_hashmap.get("Answer"))) {
                    qs.setText("Guten");
                    try {
                        MainActivity.j++;
                        startActivity(new Intent(FreeText.this, MainActivity.class));
                    }
                    catch (Exception e){
                        Log.d("Error", "No files");
                    }
                }
                else{

                }
            }
        });
    }
}
