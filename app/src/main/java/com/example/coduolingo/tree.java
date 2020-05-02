package com.example.coduolingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;

public class tree extends AppCompatActivity {

    FirebaseAuth mAuth;
    Button skill1;
    Button skill2;
    Button skill3;
    Button profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree);
        mAuth = FirebaseAuth.getInstance();
        skill1 = (Button) findViewById(R.id.skill1);
        skill2 = (Button) findViewById(R.id.skill2);
        skill3 = (Button) findViewById(R.id.skill3);
        profile = (Button) findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(tree.this, profile_Activity.class));
            }
        });
        skill1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLesson("57983", "Math");
            } //savta
        });
        skill2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLesson("57932", "HI");
            }
        });
    }

    void startLesson(String id, String name){
        MainActivity.id = id;
        MainActivity.name = name;

        startActivity(new Intent(tree.this, MainActivity.class));
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user){
        if(user == null){
            //not logged in
            startActivity(new Intent(tree.this, Login.class));
        }
    }
}