package com.getcodly.codly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;

public class CompW extends AppCompatActivity {
    long firstTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comp_w);
        firstTime = System.currentTimeMillis();
        Handler handler = new Handler();
        int delay = 1000; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){
                //do something
                long secondTime  = System.currentTimeMillis();
                if (secondTime - firstTime > 20000){
                    startActivity(new Intent(CompW.this, mainScreen.class));
                }
                handler.postDelayed(this, delay);
            }
        }, delay);
        FirebaseDatabase database_start = FirebaseDatabase.getInstance();
        DatabaseReference myRef_start = database_start.getReference("Users").child(ReadWrite.read(CompW.this.getFilesDir() + File.separator + "user")).child("start_comp");
        myRef_start.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is uploaded
                if (!dataSnapshot.getValue().toString().equals("")){
                    String sel = dataSnapshot.getValue().toString();
                    myRef_start.setValue("");
                    startComp(sel);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    void startComp(String id) {
        tree.LessonType = "comp";
        MainActivity.id = id;
        MainActivity.name = "comp";
        startActivity(new Intent(CompW.this, MainActivity.class));
    }
}
