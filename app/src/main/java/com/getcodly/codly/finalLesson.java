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
        //finishLsnBtn.setVisibility(View.INVISIBLE);

        mInterstitialAd = new InterstitialAd(this);
        //mInterstitialAd.setAdUnitId("ca-app-pub-8750577101117973/5953828477"); Real ad ID, not for testing!
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712"); //Not a real ad, just for testing purposes.
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                startActivity(new Intent(finalLesson.this , tree.class));
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
            //startActivity(new Intent(this, tree.class));
        }
    }

    void something(){
        date = DownloadReadlessons.get_last_lesson(ReadWrite.read(this.getFilesDir()+ File.separator+ "user"));
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

        mcountdown = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {
                //dialog.show();
                Log.d("Loading", "Loading");
            }

            @Override
            public void onFinish() {
                int year = Integer.parseInt(date.get("year"));
                int month = Integer.parseInt(date.get("month"));
                int day = Integer.parseInt(date.get("date"));
                int xp = (int) Double.parseDouble(date.get("xp"));
                String old_progress = String.valueOf(date.get("cProgress"));
                Calendar calendar = Calendar.getInstance();

                // Move calendar to yesterday
                calendar.add(Calendar.DATE, -1);

                // Get current date of calendar which point to the yesterday now
                int yesterday = calendar.get(Calendar.DATE);
                Calendar calendar2 = Calendar.getInstance();
                int today = calendar2.get(Calendar.DATE);
                Log.d("0", String.valueOf(calendar.get(Calendar.YEAR)));
                if (year == calendar.get(Calendar.YEAR) - 1900) {
                    Log.d("1", "1");
                    if (month == calendar.get(Calendar.MONTH)) {
                        Log.d("2", "2");
                        if (day == yesterday) {
                            Log.d("3", "3");
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("Users").child(ReadWrite.read(finalLesson.this.getFilesDir()+File.separator + "user"));
                            myRef.child("streak").setValue(String.valueOf(Integer.parseInt(date.get("streak")) + 1));
                        } else if (day == today) {
                            Log.d("3", "3");
                        } else {
                            Log.d("3", "3");
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("Users").child(ReadWrite.read(finalLesson.this.getFilesDir()+File.separator + "user"));
                            myRef.child("streak").setValue(0);
                            Log.d("error", "day");
                        }
                    }else{
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("Users").child(ReadWrite.read(finalLesson.this.getFilesDir()+File.separator + "user"));
                        myRef.child("streak").setValue(0);
                        Log.d("error", "month");
                    }
                }else{
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Users").child(ReadWrite.read(finalLesson.this.getFilesDir()+File.separator + "user"));
                    myRef.child("streak").setValue(0);
                    Log.d("error", "year");
                }
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Users");
                DatabaseReference user = myRef.child(ReadWrite.read(finalLesson.this.getFilesDir()+File.separator + "user"));
                Date currentTime = Calendar.getInstance().getTime();
                Timestamp ts = new Timestamp(currentTime.getTime());
                user.child("lastLessonD").setValue(ts);
                user.child("xp").setValue(xp + LessonActivity.shared_xp2);
                LessonActivity.shared_xp2 = LessonActivity.shared_xp.intValue();
                List<String> str_old_progress = Arrays.asList(old_progress.split(" "));
                if(!str_old_progress.contains(MainActivity.id)) {
                    //user.child("progress").setValue(old_progress + " " + MainActivity.id);
                }

                finishLsnBtn.setVisibility(View.VISIBLE);
            }

        }.start();
    }

}

