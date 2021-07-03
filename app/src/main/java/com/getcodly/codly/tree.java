package com.getcodly.codly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
    RelativeLayout skill2;
    RelativeLayout skill3;
    RelativeLayout skill4;
    RelativeLayout skill5;
    RelativeLayout skill6;
    RelativeLayout skill7;
    RelativeLayout skill8;
    RelativeLayout skill9;
    RelativeLayout skill10;
    TextView f1;
    TextView f2;
    TextView f3;
    TextView f4;
    TextView f5;
    TextView f6;
    TextView f7;
    TextView f8;
    TextView f9;
    TextView f10;
    RelativeLayout LessonStart1;
    RelativeLayout LessonStart2;
    RelativeLayout LessonStart3;
    RelativeLayout LessonStart4;
    RelativeLayout LessonStart5;
    RelativeLayout LessonStart6;
    RelativeLayout LessonStart7;
    RelativeLayout LessonStart8;
    RelativeLayout LessonStart9;
    RelativeLayout LessonStart10;

    RelativeLayout switchToHTML;

    RelativeLayout topSheet;

    boolean isTopSheetVisible;

    RelativeLayout switchCourseBtn;

    View pageCover;

    public static ArrayList<String> idShare;
    public static ArrayList<String> namesShare;
    CountDownTimer mcountdown;
    public static String loadAgain = "";
    public static String[] practiceID;
    HashMap <String, String> date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tree_py_improved);
        //mAuth = FirebaseAuth.getInstance();
        mainScreen.lessonWr = 0;
        switchToHTML = findViewById(R.id.courseHTML);
        skill1 = (RelativeLayout) findViewById(R.id.skill_1);
        skill2 = (RelativeLayout) findViewById(R.id.skill_2);
        skill3 = (RelativeLayout) findViewById(R.id.skill_3);
        skill4 = (RelativeLayout) findViewById(R.id.skill_4);
        skill5 = (RelativeLayout) findViewById(R.id.skill_5);
        skill6 = (RelativeLayout) findViewById(R.id.skill_6);
        skill7 = (RelativeLayout) findViewById(R.id.skill_7);
        skill8 = (RelativeLayout) findViewById(R.id.skill_8);
        skill9 = (RelativeLayout) findViewById(R.id.skill_9);
        skill10 = (RelativeLayout) findViewById(R.id.skill_10);
        f1 = (TextView) findViewById(R.id.f1);
        f2 = (TextView) findViewById(R.id.f2);
        f3 = (TextView) findViewById(R.id.f3);
        f4 = (TextView) findViewById(R.id.f4);
        f5 = (TextView) findViewById(R.id.f5);
        f6 = (TextView) findViewById(R.id.f6);
        f7 = (TextView) findViewById(R.id.f7);
        f8 = (TextView) findViewById(R.id.f8);
        f9 = (TextView) findViewById(R.id.f9);
        f10 = (TextView) findViewById(R.id.f10);
        LessonStart1 = findViewById(R.id.skill1picture);
        LessonStart2 = findViewById(R.id.skill2picture);
        LessonStart3 = findViewById(R.id.skill3picture);
        LessonStart4 = findViewById(R.id.skill4picture);
        LessonStart5 = findViewById(R.id.skill5picture);
        LessonStart6 = findViewById(R.id.skill6picture);
        LessonStart7 = findViewById(R.id.skill7picture);
        LessonStart8 = findViewById(R.id.skill8picture);
        LessonStart9 = findViewById(R.id.skill9picture);
        LessonStart10 = findViewById(R.id.skill10picture);

        topSheet = findViewById(R.id.topSheet);
        switchCourseBtn = findViewById(R.id.button8);
        pageCover = findViewById(R.id.pageCover);

        switchCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isTopSheetVisible){
                    pageCover.setVisibility(View.VISIBLE);
                    topSheet.setVisibility(View.VISIBLE);
                    topSheet.setTranslationY(-600);
                    topSheet.animate().translationY(0).setDuration(300);
                    isTopSheetVisible = true;
                } else{
                    topSheet.animate().translationY(-600).setDuration(300);
                    pageCover.setVisibility(View.GONE);
                    topSheet.setVisibility(View.GONE);
                    isTopSheetVisible = false;
                }
            }
        });

        pageCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageCover.setVisibility(View.GONE);
                topSheet.setVisibility(View.GONE);
                isTopSheetVisible = false;
            }
        });

        File dirName = new File(Environment.getExternalStorageDirectory() + "/" + "id" + "/");
        try {
            FileUtils.deleteDirectory(dirName);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("malbona", "malbona");
        }
        LessonActivity.j = 1;
        mainScreen.w = "py";
        date = DownloadReadlessons.get_last_lesson(ReadWrite.read(this.getFilesDir()+File.separator+ "user"));
        f1.setText(s(Arrays.asList("1-1-1", "1-1-2")));
        f2.setText(s(Arrays.asList("1-2-1", "1-2-2", "1-2-4", "1-2-5", "1-2-3")));
        f3.setText(s(Arrays.asList("1-3-1", "1-3-2")));
        f4.setText(s(Arrays.asList("1-5-1", "1-4-2", "1-5-2", "1-5-3")));
        f5.setText(s(Arrays.asList("1-4-3", "1-5-4")));
        f6.setText(s(Arrays.asList("1-7-1", "1-4-1")));
        if (ifLfinished(Arrays.asList("1-1-1", "1-1-2"))){
            LessonStart1.setBackgroundResource(R.drawable.skill2);
        }
        if (ifHfinished(Arrays.asList("1-1-1", "1-1-2"), Arrays.asList(""))){
            LessonStart1.setBackgroundResource(R.drawable.skill);
        }
        if (ifLfinished(Arrays.asList("1-2-1", "1-2-2", "1-2-6", "1-2-3"))){
            LessonStart2.setBackgroundResource(R.drawable.skill2);
        }
        if (ifHfinished(Arrays.asList("1-2-1", "1-2-2", "1-2-4", "1-2-5", "1-2-6", "1-2-3"), Arrays.asList("1-1-2"))){
            LessonStart2.setBackgroundResource(R.drawable.skill);
        }
        if (ifLfinished(Arrays.asList("1-3-3", "1-3-1", "1-3-2"))){
            LessonStart3.setBackgroundResource(R.drawable.skill2);
        }
        if (ifHfinished(Arrays.asList("1-3-3", "1-3-1", "1-3-2"), Arrays.asList("1-2-3"))){
            LessonStart3.setBackgroundResource(R.drawable.skill);
        }
        if (ifLfinished(Arrays.asList("1-5-1", "1-4-2", "1-5-2", "1-5-3"))){
            LessonStart4.setBackgroundResource(R.drawable.skill2);
        }
        if (ifHfinished(Arrays.asList("1-5-1", "1-4-2", "1-5-2", "1-5-3"), Arrays.asList("1-3-2"))){
            LessonStart4.setBackgroundResource(R.drawable.skill);
        }
        if (ifLfinished(Arrays.asList("1-4-3", "1-5-4"))){
            LessonStart5.setBackgroundResource(R.drawable.skill2);
        }
        if (ifHfinished(Arrays.asList("1-4-3", "1-5-4"), Arrays.asList("1-5-3"))){
            LessonStart5.setBackgroundResource(R.drawable.skill);
        }
        if (ifLfinished(Arrays.asList("1-7-1"))){
            LessonStart6.setBackgroundResource(R.drawable.skill2);
        }
        if (ifHfinished(Arrays.asList("1-7-1", "1-4-1"), Arrays.asList("1-5-4"))){
            LessonStart6.setBackgroundResource(R.drawable.skill);
        }
        if (ifLfinished(Arrays.asList("1-7-3"))){
            LessonStart7.setBackgroundResource(R.drawable.skill2);
        }
        if (ifHfinished(Arrays.asList("1-7-2", "1-7-3"), Arrays.asList("1-7-1"))){
            LessonStart7.setBackgroundResource(R.drawable.skill);
        }
        if (ifLfinished(Arrays.asList("1-8-2"))){
            LessonStart10.setBackgroundResource(R.drawable.skill2);
        }
        if (ifHfinished(Arrays.asList("1-8-1", "1-8-2"), Arrays.asList("1-7-3"))){
            LessonStart10.setBackgroundResource(R.drawable.skill);
        }
        switchToHTML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(tree.this, tree_html_improved.class));//avino
            }
        });
        skill1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainScreen.inLessonName = "מבוא לפייתון";
                startLesson(Arrays.asList("1-1-2~הקוד הראשון שלי", "1-1-1~מבוא לפייתון"), Arrays.asList(""));
            } //savta
        });
        skill2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainScreen.inLessonName = "משתנים";
                startLesson(Arrays.asList("1-2-3~תכנות אינטראקטיבי", "1-2-6~שינוי סוג משתנה", "1-2-5~הפקודה type","1-2-4~תרגול משתנים" ,"1-2-2~טקסטים ומספרים", "1-2-1~מבוא למשתנים"), Arrays.asList("1-1-2"));
            } //savta
        });
        skill3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainScreen.inLessonName = "תנאים";
                startLesson(Arrays.asList("1-3-2~הפקודות else וelif", "1-3-1~תנאים", "1-3-3~משתנה בוליאני"), Arrays.asList("1-2-3"));
            } //savta
        });
        skill4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainScreen.inLessonName = "לולאות";
                startLesson(Arrays.asList("1-5-3~הפקודה range" ,"1-5-2~לולאות for" ,"1-4-2~רשימות", "1-5-1~לולאת while"), Arrays.asList("1-3-2"));
            } //savta
        });
        skill5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainScreen.inLessonName = "מילונים";
                startLesson(Arrays.asList("1-5-4~יצירת צ'אט בוט" ,"1-4-3~מילונים"), Arrays.asList("1-3-2"));
            } //savta
        });
        skill6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainScreen.inLessonName = "פונקציות וחריגים";
                startLesson(Arrays.asList("1-4-1~שגיאות וחריגים", "1-7-1~פונקציות"), Arrays.asList("1-5-4"));
            } //savta
        });
        skill7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainScreen.inLessonName = "פונקציות מתקדמות";
                startLesson(Arrays.asList("1-7-3~הפקודה return", "1-7-2~פונקציות עם פרמטרים"), Arrays.asList("1-7-1"));
            } //savta
        });
        skill8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainScreen.inLessonName = "בניית מחשבון בסיסי";
                startLesson(Arrays.asList("1-9-2~מחשבון בסיסי 2", "1-9-1~מחשבון בסיסי 1"), Arrays.asList("1-2-3"));
            } //savta
        });
        skill9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainScreen.inLessonName = "בניית מחשבון";
                startLesson(Arrays.asList("1-9-4~מחשבון 2", "1-9-3~מחשבון 1"), Arrays.asList("1-3-2"));
            } //savta
        });
        skill10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainScreen.inLessonName = "מחלקות ואובייקטים";
                startLesson(Arrays.asList("1-8-2~מחלקות", "1-8-1~אובייקטים ומחלקות"), Arrays.asList("1-7-3"));
            } //savta
        });

}

    void startLesson(List<String> id, List<String> id_alt) {
        if (ifLfinished(id_alt)) {
            String old_progress = mainScreen.progress;
            idShare = new ArrayList();
            namesShare = new ArrayList();
            mainScreen.LessonType = "";
            for (String i : id) {
                idShare.add(i.split("~")[0]);
                namesShare.add(i.split("~")[1]);
            }
            for (String d : id) {
                List<String> str_old_progress = Arrays.asList(old_progress.split(","));
                Log.d(str_old_progress.toString(), String.valueOf(str_old_progress.contains(d.split("~")[0])));
                if (!str_old_progress.toString().contains(d.split("~")[0])) {
                    MainActivity.id = d.split("~")[0];
                    MainActivity.name = d.split("~")[1];
                }
            }
            startActivity(new Intent(tree.this, selectLesson.class));
            overridePendingTransition(0, 0);
        }
    }
    boolean ifLfinished(List<String> id){
        boolean ret = true;
        for (String i : id){
            Log.d("testyTestyTest", mainScreen.progress);
            Log.d("testy2", i);
            if (!mainScreen.progress.contains(i)) {
                ret = false;
                break;
            }
        }
        Log.d(String.valueOf(ret), String.valueOf(ret));
        return ret;
    }
    boolean ifHfinished(List<String> id, List<String> old_id){
        boolean ret = true;
        for (String i : id){
            if (mainScreen.progress.contains(i)) {
                ret = false;
                break;
            }
        }
        if (ifLfinished(old_id)){
            ret = false;
        }
        return ret;
    }
    String s(List<String> id){
        int e = 0;
        for (String i : id){
            if (mainScreen.progress.contains(i)) {
                e = e + 1;
            }
        }
        return String.valueOf(e) + "/" + id.size();
    }
    void startPractice(String[] id) {
        mainScreen.LessonType = "practice";
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