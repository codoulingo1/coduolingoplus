package com.example.coduolingo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView qs;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String lesson = DownloadReadlessons.downloadlesson("57933", MainActivity.this); // download lesson by ID
        Log.d("gojo", "h"); // pro vi estas gojo
        final HashMap<String, String> hashMap = DownloadReadlessons.readqs("57933", "Math", "1", MainActivity.this); // read qs by ID + name + question number
        qs = (TextView)findViewById(R.id.textView);
        qs.setText(hashMap.get("qs"));
        Log.d("gojo", hashMap.get("qs"));
        submit = (Button) findViewById(R.id.button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inp = (EditText) findViewById(R.id.inp);
                String ans = inp.getText().toString();
                boolean h=hashMap.get("Answer")==ans;
                if (ans.equals(hashMap.get("Answer"))){
                    qs.setText("Guten");
                }
            }
        });


    }}
