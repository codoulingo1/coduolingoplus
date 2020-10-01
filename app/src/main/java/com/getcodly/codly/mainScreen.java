package com.getcodly.codly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class mainScreen extends AppCompatActivity {

    HashMap<String, String> date;
    public static String streak;
    public static boolean b = false;
    public static String w;
    public static String invName;
    public static String name;
    public static String userId = "";
    public static String sel;
    public static String img;
    public static String friends;
    public static String progress;
    FirebaseAuth mAuth;
    public static Fragment selectedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(mainScreen.this ));
        }
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
                b = true;
                int year = Integer.parseInt(value.get("year"));
                progress = String.valueOf(value.get("cProgress"));
                name = String.valueOf(value.get("name"));
                img = String.valueOf(value.get("img"));
                friends = String.valueOf(value.get("friends"));
                FirebaseDatabase database1 = FirebaseDatabase.getInstance();
                DatabaseReference myRef1 = database1.getReference("Users").child(ReadWrite.read(mainScreen.this.getFilesDir() + File.separator + "user"));
                myRef1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is uploaded
                        try {
                            if (!dataSnapshot.child("comp").getValue().toString().equals("")) {
                                if (System.currentTimeMillis() - Long.parseLong(dataSnapshot.child("comp_time").getValue().toString()) < 15000){
                                    myRef1.child("comp").setValue("");
                                    myRef1.child("comp_time").setValue("1");
                                    userId = dataSnapshot.child("comp").getValue().toString();
                                    DownloadReadlessons.get_last_lesson2(userId, new DownloadReadlessons.HashCallback() {
                                        @Override
                                        public void onCallback(HashMap<String, String> value) {
                                            String progress_2 = value.get("cProgress");
                                            invName = value.get("name");
                                            int sel_num = 0;
                                            sel = progress.split(" ")[new Random().nextInt(progress.split(" ").length)];
                                            while (!Arrays.asList(progress_2.split(" ")).contains(sel)) {
                                                sel_num++;
                                                sel = progress.split(" ")[new Random().nextInt(progress.split(" ").length)];
                                                if (sel_num > 200) {
                                                    break;
                                                }
                                            }
                                            if (sel_num <= 200) {
                                                Comp_Invite dialogBack = new Comp_Invite();
                                                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                                ft.add(dialogBack, "snooze_dialog");
                                                ft.commitAllowingStateLoss();
                                            }
                                            //DatabaseReference myRef2 = database1.getReference("Users").child(userId);
                                            //myRef2.child("start_comp").setValue(sel);
                                            //startComp(sel);
                                        }
                                    });
                                }else{
                                    myRef1.child("comp").setValue("");
                                    myRef1.child("comp_time").setValue("1");
                                }
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
                    }
                    else if(day==today) {
                        Log.d("3", "3");
                        streak = String.valueOf(value.get("streak"));
                    }
                    else if(day==bf && value.get("streak freeze").equals("true")){
                        streak = String.valueOf(value.get("streak"));
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("Users").child(ReadWrite.read(mainScreen.this.getFilesDir()+File.separator + "user"));
                        myRef.child("lastLessonD").child("year").setValue(calendar.get(Calendar.YEAR));
                        myRef.child("lastLessonD").child("date").setValue(calendar.get(Calendar.DAY_OF_YEAR));
                        myRef.child("streak freeze").setValue("false");
                    }
                    else{
                        Log.d("3", "3");
                        streak = "1";
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("Users").child(ReadWrite.read(mainScreen.this.getFilesDir()+File.separator + "user"));
                        myRef.child("streak freeze").setValue("false");
                        myRef.child("streak").setValue(1);
                    }
                }
                else if (year == calendar2.get(Calendar.YEAR) - 1 && today == 1 && (day == 365 || day == 366)){
                    Log.d("3", "3");
                    streak = String.valueOf(Integer.parseInt(value.get("streak")));
                }
                else if (year == calendar2.get(Calendar.YEAR) - 1 && today == 2 && (day == 365 || day == 366) && value.get("streak freeze").equals("true")){
                    streak = String.valueOf(value.get("streak"));
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Users").child(ReadWrite.read(mainScreen.this.getFilesDir()+File.separator + "user"));
                    myRef.child("lastLessonD").child("year").setValue(calendar.get(Calendar.YEAR));
                    myRef.child("lastLessonD").child("date").setValue(calendar.get(Calendar.DAY_OF_YEAR));
                    myRef.child("streak freeze").setValue("false");
                }
                else{
                    Log.d("3", "3");
                    streak = "1";
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Users").child(ReadWrite.read(mainScreen.this.getFilesDir()+File.separator + "user"));
                    myRef.child("streak freeze").setValue("false");
                    myRef.child("streak").setValue(1);
                }
            }
        });

        FirebaseDatabase database_start = FirebaseDatabase.getInstance();
        DatabaseReference myRef_start = database_start.getReference("Users").child(ReadWrite.read(mainScreen.this.getFilesDir() + File.separator + "user")).child("start_comp");
        myRef_start.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    if (!dataSnapshot.getValue().toString().equals("")){
                        sel = dataSnapshot.getValue().toString();
                        myRef_start.setValue("");
                        startComp(sel);
                    }
                } catch (Exception e){

                }
                // This method is called once with the initial value and again
                // whenever data at this location is uploaded
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference myRef_win = database_start.getReference("Users").child(ReadWrite.read(mainScreen.this.getFilesDir() + File.separator + "user")).child("comp_w");
        myRef_win.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is uploaded
                if (dataSnapshot.getValue().toString().equals("l")){
                    myRef_win.setValue("");
                    startActivity(new Intent(mainScreen.this, mainScreen.class));
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
        tree.LessonType = "comp";
        MainActivity.id = id;
        MainActivity.name = "comp";
        startActivity(new Intent(mainScreen.this, MainActivity.class));
    }
}
