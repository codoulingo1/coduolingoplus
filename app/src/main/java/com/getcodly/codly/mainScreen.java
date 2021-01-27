package com.getcodly.codly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class mainScreen extends AppCompatActivity {

    HashMap<String, String> date;
    public static String streak;
    public static String streak7;
    public static String w = "c";
    public static String invName;
    public static int lessonWr = 0;
    public static String name;
    public static String userId = "";
    public static String sel;
    public static int pyXp;
    public static int htmlXp;
    public static boolean isDownload = false;
    public static String img;
    public static String friends;
    public static String progress;
    FirebaseAuth mAuth;
    public static String LessonType;
    public static TextView geldView;
    public static Fragment selectedFragment = null;
    ImageButton settingsButton;
    TextView setStreak;
    public static int Geld = 0;
    FirebaseDatabase database1;
    DatabaseReference myRef1;
    public static int user_xp;
    public static int courseProgressPython = 0;
    public static int courseProgressWeb = 0;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        courseProgressWeb = 0;
        courseProgressPython = 0;

        database1 = FirebaseDatabase.getInstance();
        myRef1 = database1.getReference("Users").child(ReadWrite.read(mainScreen.this.getFilesDir() + File.separator + "user"));

        mDatabase = FirebaseDatabase.getInstance().getReference();

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(mainScreen.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("newToken", newToken);
                myRef1.child("token").setValue(newToken); //hi

            }
        });


        FirebaseMessaging.getInstance().subscribeToTopic("pushNotifications");

        LessonActivity.j = 1;
        lessonWr = 0;
        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(mainScreen.this ));
        }

        setStreak = findViewById(R.id.setStreak);

        settingsButton = findViewById(R.id.profileSettings);
        geldView = findViewById(R.id.geldView);





        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsFragment settingsFragment = new SettingsFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerMain, settingsFragment).commit();
            }
        });
        mAuth = FirebaseAuth.getInstance();
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.navigation_shop:
                        selectedFragment = new ShopFragment();
                        break;
                    case R.id.navigation_profile:
                        selectedFragment = new profileFragment();
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerMain, selectedFragment).commit();

                return true;
            }
        });
        date = DownloadReadlessons.get_last_lesson2(ReadWrite.read(this.getFilesDir() + File.separator + "user"), new DownloadReadlessons.HashCallback() {
            @Override
            public void onCallback(HashMap<String, String> value) {

                Log.d("v", "v");
                int year = Integer.parseInt(value.get("year"));
                streak = value.get("streak");
                streak7 = value.get("7streak");
                setStreak.setText(streak);
                progress = String.valueOf(value.get("cProgress"));
                isDownload = true;
                setCourseProgressWeb();
                setProgressPython();
                name = String.valueOf(value.get("name"));
                img = String.valueOf(value.get("img"));
                friends = String.valueOf(value.get("friends"));
                user_xp = Integer.parseInt(value.get("xp"));
                pyXp =  Integer.parseInt(value.get("pyXp"));
                htmlXp =  Integer.parseInt(value.get("htmlXp"));
                try {
                    Geld = Integer.parseInt(value.get("geld"));
                } catch (Exception e){
                    myRef1.child("geld").setValue(0);
                }
                geldView.setText(String.valueOf(Geld));

                myRef1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is uploaded
                        try {
                            if (!dataSnapshot.child("comp").getValue().toString().equals("")) {
                                    myRef1.child("comp").setValue("");
                                    myRef1.child("comp_time").setValue("1");
                                    userId = dataSnapshot.child("comp").getValue().toString();
                                    DownloadReadlessons.get_last_lesson2(userId, new DownloadReadlessons.HashCallback() {
                                        @Override
                                        public void onCallback(HashMap<String, String> value) {
                                            List<String> compL = Arrays.asList("2-3-2", "2-1-2", "1-3-2", "1-2-3", "1-4-2");
                                            String progress_2 = value.get("cProgress");
                                            invName = value.get("name");
                                            int sel_num = 0;
                                            try {
                                                sel = progress.split(",")[new Random().nextInt(progress.split(",").length)].split("~")[0];
                                            }catch (Exception e){
                                                Log.d("error", e.getLocalizedMessage());
                                            }
                                            while (!Arrays.asList(progress_2.split(",|\\~")).contains(sel) | !compL.contains(sel)) {
                                                sel_num++;
                                                try {
                                                    sel = progress.split(",")[new Random().nextInt(progress.split(",").length)].split("~")[0];
                                                    Log.d(progress_2.split(",")[1], sel);
                                                }catch (Exception e){
                                                    Log.d("error", e.getLocalizedMessage());
                                                }
                                                if (sel_num > 200) {
                                                    Log.d("sel", sel);
                                                    break;
                                                }
                                            }
                                            if (sel_num <= 200) {
                                                Comp_Invite dialogBack = new Comp_Invite();
                                                Log.d("sel", sel);
                                                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                                ft.add(dialogBack, "snooze_dialog");
                                                ft.commitAllowingStateLoss();
                                            } else{
                                                FirebaseDatabase database1 = FirebaseDatabase.getInstance();
                                                DatabaseReference myRef2 = database1.getReference("Users").child(mainScreen.userId);
                                                myRef2.child("start_comp").setValue("nonShared");
                                            }
                                            //DatabaseReference myRef2 = database1.getReference("Users").child(userId);
                                            //myRef2.child("start_comp").setValue(sel);
                                            //startComp(sel);
                                        }
                                    });
                            }
                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                //int month = Integer.parseInt(date.get("month"));

                int day = Integer.parseInt(value.get("date"));
                /*
                Calendar calendar = Calendar.getInstance();

                // Move calendar to yesterday
                calendar.add(Calendar.DAY_OF_YEAR, -1);

                // Get current date of calendar which point to the yesterday now
                int yesterday = calendar.get(Calendar.DAY_OF_YEAR);
                Calendar calendar_3 = Calendar.getInstance();

                // Move calendar to yesterday
                calendar_3.add(Calendar.DAY_OF_YEAR, -2);

                // Get current date of calendar which point to the yesterday now
                int bf = calendar_3.get(Calendar.DAY_OF_YEAR);
                Calendar calendar2 = Calendar.getInstance();
                int today = calendar2.get(Calendar.DAY_OF_YEAR);
                Log.d("hello", String.valueOf(today));
                if(year==calendar2.get(Calendar.YEAR)){
                    Log.d("1", "1");
                    Log.d("2", "2");
                    if(day==yesterday){
                        Log.d("3", "3");
                        streak = String.valueOf(Integer.parseInt(value.get("streak")));
                        setStreak.setText(streak);
                    }
                    else if(day==today) {
                        Log.d("3", "3");
                        streak = String.valueOf(value.get("streak"));
                        setStreak.setText(streak);
                    }
                    else if(day==bf && value.get("streak freeze").equals("true")){
                        streak = String.valueOf(value.get("streak"));
                        setStreak.setText(streak);
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("Users").child(ReadWrite.read(mainScreen.this.getFilesDir()+File.separator + "user"));
                        myRef.child("lastLessonD").child("year").setValue(calendar.get(Calendar.YEAR));
                        myRef.child("lastLessonD").child("date").setValue(calendar.get(Calendar.DAY_OF_YEAR));
                        myRef.child("streak freeze").setValue("false");
                    }
                    else{
                        Log.d("3", "3");
                        streak = "1";
                        setStreak.setText(streak);
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("Users").child(ReadWrite.read(mainScreen.this.getFilesDir()+File.separator + "user"));
                        myRef.child("streak freeze").setValue("false");
                        myRef.child("streak").setValue(1);
                    }
                }
                else if (year == calendar2.get(Calendar.YEAR) - 1 && today == 1 && (day == 365 || day == 366)){
                    Log.d("3", "3");
                    streak = String.valueOf(Integer.parseInt(value.get("streak")));
                    setStreak.setText(streak);
                }
                else if (year == calendar2.get(Calendar.YEAR) - 1 && today == 2 && (day == 365 || day == 366) && value.get("streak freeze").equals("true")){
                    streak = String.valueOf(value.get("streak"));
                    setStreak.setText(streak);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Users").child(ReadWrite.read(mainScreen.this.getFilesDir()+File.separator + "user"));
                    myRef.child("lastLessonD").child("year").setValue(calendar.get(Calendar.YEAR));
                    myRef.child("lastLessonD").child("date").setValue(calendar.get(Calendar.DAY_OF_YEAR));
                    myRef.child("streak freeze").setValue("false");
                }
                else{
                    Log.d("3", "3");
                    streak = "1";
                    setStreak.setText(streak);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Users").child(ReadWrite.read(mainScreen.this.getFilesDir()+File.separator + "user"));
                    myRef.child("streak freeze").setValue("false");
                    myRef.child("streak").setValue(1);
                }*/
            }
        });


        FirebaseDatabase database_start = FirebaseDatabase.getInstance();
        DatabaseReference myRef_win = database_start.getReference("Users").child(ReadWrite.read(mainScreen.this.getFilesDir() + File.separator + "user")).child("comp_w");
        myRef_win.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is uploaded
                try {
                    if (dataSnapshot.getValue().toString().equals("l")){
                        myRef_win.setValue("");
                        startActivity(new Intent(mainScreen.this, mainScreen.class));
                    }
                } catch (Exception e){
                    startActivity(new Intent(mainScreen.this, Login.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerMain, new HomeFragment()).commit();


    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user){
        if(user == null){
            //not logged in
            startActivity(new Intent(mainScreen.this, Login.class));
        }
    }
    void startComp(String id) {
        LessonType = "comp";
        MainActivity.id = id;
        MainActivity.name = "comp";
        startActivity(new Intent(mainScreen.this, MainActivity.class));
    }

    void setCourseProgressWeb(){
        for(String i : progress.split(",")){
            if(i.startsWith("2")){
                courseProgressWeb++;
            }
        }

        Log.d("progressCount", String.valueOf(courseProgressWeb));
    }
    void setProgressPython(){
        for(String i : progress.split(",")){
            if(i.startsWith("1")){
                courseProgressPython++;
            }
        }
    }
}
