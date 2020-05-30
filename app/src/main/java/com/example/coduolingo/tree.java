package com.example.coduolingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class tree extends AppCompatActivity {

    //FirebaseAuth mAuth;
    Button skill1;
    Button skill2;
    Button skill3;
    Button profile;
    CountDownTimer mcountdown;
    public static String[] practiceID;
    public static String LessonType;
    HashMap <String, String> date;
    TextView streak;
    Button toHTML;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree);
        //mAuth = FirebaseAuth.getInstance();
        skill1 = (Button) findViewById(R.id.skill1);
        skill2 = (Button) findViewById(R.id.skill2);
        skill3 = (Button) findViewById(R.id.skill3);
        profile = (Button) findViewById(R.id.profile);
        streak = (TextView) findViewById(R.id.streak);
        toHTML = (Button) findViewById(R.id.toHTML);
        File dirName = new File(Environment.getExternalStorageDirectory() + "/" + "id" + "/");
        try {
            FileUtils.deleteDirectory(dirName);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("malbona", "malbona");
        }
        LessonActivity.j = 1;
        date = DownloadReadlessons.get_last_lesson(ReadWrite.read(this.getFilesDir()+File.separator+ "user"));
        mcountdown = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {
                //dialog.show();
                //Log.d("Loading", "Loading");
            }

            @Override
            public void onFinish() {
                int year = Integer.parseInt(date.get("year"));
                int month = Integer.parseInt(date.get("month"));
                int day = Integer.parseInt(date.get("date"));
                Calendar calendar = Calendar.getInstance();

                // Move calendar to yesterday
                calendar.add(Calendar.DATE, -1);

                // Get current date of calendar which point to the yesterday now
                int yesterday = calendar.get(Calendar.DATE);
                Calendar calendar2 = Calendar.getInstance();
                int today = calendar2.get(Calendar.DATE);
                if(year==calendar.get(Calendar.YEAR)-1900){
                    Log.d("1", "1");
                    if(month==calendar.get(Calendar.MONTH)){
                        Log.d("2", "2");
                        if(day==yesterday){
                            Log.d("3", "3");
                            streak.setText(String.valueOf(Integer.parseInt(date.get("streak"))));
                        }
                        else if(day==today){
                            Log.d("3", "3");
                            streak.setText(String.valueOf(date.get("streak")));
                        }
                        else{
                            Log.d("3", "3");
                            streak.setText("0");
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("Users").child(ReadWrite.read(tree.this.getFilesDir()+File.separator + "user"));
                            myRef.child("streak").setValue(0);
                        }
                    }
                }
            }
        }.start();
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(tree.this, profile_Activity.class));
            }
        });
        skill1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLesson(Arrays.asList("57983"), "Math");
            } //savta
        });
        skill2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPractice(new String[]{"1", "57983"});
            }
        });
        toHTML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(tree.this, iframe2.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    void startLesson(List<String> id, String name){
        String old_progress = String.valueOf(date.get("cProgress"));
        LessonType = "";
        for(String d : id){
            List<String> str_old_progress = Arrays.asList(old_progress.split(""));
            if(!str_old_progress.contains(d)) {
                MainActivity.id = d;
                MainActivity.name = name;

                startActivity(new Intent(tree.this, MainActivity.class));
                overridePendingTransition(0,0);
            }
        }
    }
    void startPractice(String[] id){
        LessonType = "practice";
        MainActivity.id = "prac";
        MainActivity.name = "";
        practiceID = id;
        startActivity(new Intent(tree.this, MainActivity.class));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(tree.this, mainScreen.class));
    }

    /*@Override
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
    }*/
}