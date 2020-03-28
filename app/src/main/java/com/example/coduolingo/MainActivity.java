package com.example.coduolingo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String lesson = DownloadReadlessons.downloadlesson("57933", MainActivity.this);
        Log.d("gojo", "h");
        HashMap<String, String> hashMap = DownloadReadlessons.readqs("57933", "Math", "1", MainActivity.this);
        qs = (TextView)findViewById(R.id.textView);
        qs.setText(hashMap.get("qs"));
        Log.d("gojo", hashMap.get("qs"));
    }}
