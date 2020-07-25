package com.getcodly.codly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;

public class mainScreen extends AppCompatActivity {

    HashMap<String, String> date;
    public static String streak;
    public static String name;
    public static String img;
    public static String friends;
    public static String progress;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        mAuth = FirebaseAuth.getInstance();
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

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
                progress = String.valueOf(value.get("cProgress"));
                name = String.valueOf(value.get("name"));
                img = String.valueOf(value.get("img"));
                friends = String.valueOf(value.get("friends"));
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
                        streak = "0";
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("Users").child(ReadWrite.read(mainScreen.this.getFilesDir()+File.separator + "user"));
                        myRef.child("streak freeze").setValue("false");
                        myRef.child("streak").setValue(0);
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
                    streak = "0";
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Users").child(ReadWrite.read(mainScreen.this.getFilesDir()+File.separator + "user"));
                    myRef.child("streak freeze").setValue("false");
                    myRef.child("streak").setValue(0);
                }
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



}
