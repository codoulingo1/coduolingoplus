package com.getcodly.codly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class tree extends AppCompatActivity {

    //FirebaseAuth mAuth;
    RelativeLayout skill1;
    public static ArrayList<String> idShare;
    public static ArrayList<String> namesShare;
    CountDownTimer mcountdown;
    public static String[] practiceID;
    public static String LessonType;
    HashMap <String, String> date;
    Button toPython;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_new);
        //mAuth = FirebaseAuth.getInstance();
        skill1 = (RelativeLayout) findViewById(R.id.skill12);

        toPython = (Button) findViewById(R.id.button7);
        File dirName = new File(Environment.getExternalStorageDirectory() + "/" + "id" + "/");
        try {
            FileUtils.deleteDirectory(dirName);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("malbona", "malbona");
        }
        LessonActivity.j = 1;
        date = DownloadReadlessons.get_last_lesson(ReadWrite.read(this.getFilesDir()+File.separator+ "user"));
        skill1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLesson(Arrays.asList("1-1-1~מבוא לפייתון", "1-1-2~הקוד הראשון שלי"));
            } //savta
        });
        toPython.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(tree.this, selectProject.class)); //iframe2
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    void startLesson(List<String> id){
        String old_progress = mainScreen.progress;
        idShare = new ArrayList();
        namesShare = new ArrayList();
        LessonType = "";
        for(String i : id){
            idShare.add(i.split("~")[0]);
            namesShare.add(i.split("~")[1]);
        }
        for(String d : id){
            List<String> str_old_progress = Arrays.asList(old_progress.split(" "));
            Log.d(str_old_progress.toString(), String.valueOf(str_old_progress.contains(d.split("~")[0])));
            if(!str_old_progress.toString().contains(d.split("~")[0])) {
                MainActivity.id = d.split("~")[0];
                MainActivity.name = d.split("~")[1];
            }
        }
        startActivity(new Intent(tree.this, selectLesson.class));
        overridePendingTransition(0,0);
    }
    void startPractice(String[] id) {
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