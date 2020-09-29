package com.getcodly.codly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class finalLesson extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;
    ImageButton finishLsnBtn;
    boolean b = false;
    HashMap<String, String> date;
    CountDownTimer mcountdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_lesson);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
        finishLsnBtn = findViewById(R.id.finishLsnBtn);
        finishLsnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartAdTransfer();
            }
        });
        finishLsnBtn.setVisibility(View.INVISIBLE);

        mInterstitialAd = new InterstitialAd(this);
        //mInterstitialAd.setAdUnitId("ca-app-pub-8750577101117973/5953828477"); Real ad ID, not for testing!
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712"); //Not a real ad, just for testing purposes.
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                startActivity(new Intent(finalLesson.this , mainScreen.class));
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.d("AdError", String.valueOf(errorCode));
                Log.d("AdError", "savta");
            }
        });
        something();
    }

    public void StartAdTransfer(){
        if (mInterstitialAd.isLoaded()){
            mInterstitialAd.show();
        } else {
            //startActivity(new Intent(this, mainScreen.class));
        }
    }

    void something() {
        date = DownloadReadlessons.get_last_lesson2(ReadWrite.read(this.getFilesDir() + File.separator + "user"), new DownloadReadlessons.HashCallback() {
            @Override
            public void onCallback(HashMap<String, String> value) {
                if (!b) {
                    b = true;
                    File dirName = new File(Environment.getExternalStorageDirectory() + "/" + "id" + "/");
                    boolean a = false;
                    Log.d("bona", "bona");

                    try {
                        FileUtils.deleteDirectory(dirName);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("malbona", "malbona");
                    }
                    Log.d(String.valueOf(a), String.valueOf(a));
                    //Log.d(String.valueOf(file.exists()), String.valueOf(deleted));

                    //pb.setProgress(pr);
                    //qs.setText("כל הכבוד!");

                    int year = Integer.parseInt(value.get("year"));
                    //int month = Integer.parseInt(value.get("month"));
                    int day = Integer.parseInt(value.get("date"));
                    int xp = (int) Double.parseDouble(value.get("xp"));
                    String old_progress = String.valueOf(mainScreen.progress);
                    Calendar calendar = Calendar.getInstance();

                    // Move calendar to yesterday
                    calendar.add(Calendar.DAY_OF_YEAR, -1);

                    // Get current date of calendar which point to the yesterday now
                    int yesterday = calendar.get(Calendar.DAY_OF_YEAR);
                    Calendar calendar2 = Calendar.getInstance();
                    int today = calendar2.get(Calendar.DAY_OF_YEAR);
                    int this_year = calendar2.get(Calendar.YEAR);
                    Log.d("0", String.valueOf(calendar.get(Calendar.YEAR)));
                    if (year == calendar.get(Calendar.YEAR)) {
                        Log.d("1", "1");
                            if (day == yesterday) {
                                Log.d("3", "3");
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("Users").child(ReadWrite.read(finalLesson.this.getFilesDir() + File.separator + "user"));
                                myRef.child("streak").setValue(String.valueOf(Integer.parseInt(mainScreen.streak) + 1));
                                mainScreen.streak = mainScreen.streak + 1;
                            } else if (day == today) {
                                Log.d("3", "3");
                            } else {
                                Log.d("3", "3");
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("Users").child(ReadWrite.read(finalLesson.this.getFilesDir() + File.separator + "user"));
                                myRef.child("streak").setValue(0);
                                mainScreen.streak = "0";
                                Log.d("error", "day");
                            }
                    }
                    else if (year == calendar2.get(Calendar.YEAR) - 1 && today == 1 && day == 365 || day == 366){
                        Log.d("3", "3");
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("Users").child(ReadWrite.read(finalLesson.this.getFilesDir() + File.separator + "user"));
                        myRef.child("streak").setValue(String.valueOf(Integer.parseInt(mainScreen.streak) + 1));
                        mainScreen.streak = mainScreen.streak + 1;
                    }
                    else {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("Users").child(ReadWrite.read(finalLesson.this.getFilesDir() + File.separator + "user"));
                        myRef.child("streak").setValue(0);
                        mainScreen.streak = "0";
                        Log.d("error", "year");
                    }
                    FirebaseDatabase database = FirebaseDatabase.getInstance();//
                    DatabaseReference myRef = database.getReference("Users");
                    DatabaseReference user = myRef.child(ReadWrite.read(finalLesson.this.getFilesDir() + File.separator + "user"));
                    user.child("lastLessonD").child("date").setValue(today);
                    if (tree.LessonType.equals("comp")) {
                        DatabaseReference user_2 = myRef.child(mainScreen.userId);
                        user_2.child("comp_w").setValue("l");
                        LessonActivity.shared_xp2 = LessonActivity.shared_xp2 * 2;
                    }
                    user.child("lastLessonD").child("year").setValue(this_year);
                    user.child("xp").setValue(xp + LessonActivity.shared_xp2);
                    LessonActivity.shared_xp2 = LessonActivity.shared_xp.intValue();
                    List<String> str_old_progress = Arrays.asList(old_progress.split(" "));
                    if (!str_old_progress.contains(MainActivity.id)) {
                        user.child("progress").setValue(old_progress + " " + MainActivity.id + "~" + MainActivity.name);
                        mainScreen.progress = old_progress + " " + MainActivity.id + "~" + MainActivity.name;
                    }

                    finishLsnBtn.setVisibility(View.VISIBLE);
                }
            }
        });
    }

}

