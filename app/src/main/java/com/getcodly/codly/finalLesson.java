package com.getcodly.codly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
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
import java.util.Random;


public class finalLesson extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;
    Button finishLsnBtn;
    boolean b = false;
    HashMap<String, String> date;
    CountDownTimer mcountdown;
    TextView correctAns;
    TextView finalXp;
    DatabaseReference myRef;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_lesson);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users").child(ReadWrite.read(finalLesson.this.getFilesDir() + File.separator + "user"));

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
        finalXp = findViewById(R.id.finalXP);
        correctAns = findViewById(R.id.correctAnswers);
        finishLsnBtn = findViewById(R.id.finishLsnBtn);
        LessonActivity.shared_xp2 = Math.round(Float.parseFloat(String.valueOf(mainScreen.lessonWr))/Float.parseFloat(String.valueOf(LessonActivity.j - 1)) * 15);

        if (LessonActivity.shared_xp2>10){
            finalXp.setText("נקודות (XP) שהושגו: " + String.valueOf(LessonActivity.shared_xp2));
        }
        else {
            LessonActivity.shared_xp2 = 10;
            finalXp.setText("נקודות (XP) שהושגו:" + " 10");
        }

        finishLsnBtn.setAlpha(0);

        finishLsnBtn.animate().alpha(0).setDuration(1000).setInterpolator(new DecelerateInterpolator()).withEndAction(new Runnable() {
            @Override
            public void run() {
                finishLsnBtn.animate().alpha(1).setDuration(1000).setInterpolator(new AccelerateInterpolator()).start();
            }
        }).start();

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
                Log.d("hihi", mainScreen.w);
                if (mainScreen.w.equals("py")){
                    Log.d("else", "hihi");
                    startActivity(new Intent(finalLesson.this, tree.class));
                }
                else if (mainScreen.w.equals("html")){
                    Log.d("else", "hihi");
                    startActivity(new Intent(finalLesson.this, tree_html_improved.class));
                }else{
                    Log.d("else", "hihi");
                    startActivity(new Intent(finalLesson.this, mainScreen.class));
                }
                Log.d("else", "hihi");
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
        Context context = finalLesson.this;
                if (!b) {
                    b = true;
                    File dirName = new File(context.getFilesDir() + "/" + "id" + "/");
                    boolean a = false;
                    Log.d("bona", "bona");

                    try {
                        FileUtils.deleteDirectory(dirName);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("malbona", "malbona");
                    }
                    a = dirName.exists();
                    Log.d("Test if deleted", String.valueOf(a));
                    //Log.d(String.valueOf(file.exists()), String.valueOf(deleted));

                    //pb.setProgress(pr);
                    //qs.setText("כל הכבוד!");

                    int xp = mainScreen.user_xp;
                    int maxStreak = Integer.parseInt(mainScreen.maxStreak);
                    int weekXp = mainScreen.weekXp;
                    int pyXp = mainScreen.pyXp;
                    int htmlXp = mainScreen.htmlXp;
                    String old_progress = String.valueOf(mainScreen.progress);


                    //Geldprobleme
                    if (!mainScreen.hasDone) {
                        Random random = new Random();
                        int GeldToGive = random.nextInt(2) + 1;
                        correctAns.setText("מטבעות שהושגו: " + String.valueOf(GeldToGive));
                        mainScreen.Geld += GeldToGive;

                        int currentGeld = mainScreen.Geld;
                        //int newGeld = currentGeld + GeldToGive;
                        myRef.child("geld").setValue(currentGeld);

                    } else{
                        correctAns.setText("מטבעות שהושגו: ניתן להשיג מטבעות רק פעם ביום.");
                    }
                    /*
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
                                myRef.child("streak").setValue(1);
                                mainScreen.streak = "1";
                                Log.d("error", "day");
                            }
                    }
                    else if (year == calendar2.get(Calendar.YEAR) - 1 && today == 1 && day == 365 || day == 366){
                        Log.d("3", "3");
                        myRef.child("streak").setValue(String.valueOf(Integer.parseInt(mainScreen.streak) + 1));
                        mainScreen.streak = mainScreen.streak + 1;
                    }
                    else {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("Users").child(ReadWrite.read(finalLesson.this.getFilesDir() + File.separator + "user"));
                        myRef.child("streak").setValue(1);
                        mainScreen.streak = "1";
                        Log.d("error", "year");
                    }*/
                    FirebaseDatabase database = FirebaseDatabase.getInstance();//
                    DatabaseReference myRef = database.getReference("Users");
                    DatabaseReference user = myRef.child(ReadWrite.read(finalLesson.this.getFilesDir() + File.separator + "user"));
                    if (Integer.parseInt(mainScreen.streak7)>=8){
                        user.child("7streak").setValue(0);
                        int currentGeld = mainScreen.Geld;
                        int newGeld = currentGeld + 10;
                        user.child("geld").setValue(newGeld);
                        mainScreen.Geld = newGeld;
                        mainScreen.streak7 = String.valueOf(0);
                        DoubleDialog dialogBack = new DoubleDialog();
                        dialogBack.show(getSupportFragmentManager(), "Example Dialog");
                    }
                    Log.d("lessonType", mainScreen.LessonType);
                    if (mainScreen.LessonType.equals("comp")) {
                        DatabaseReference user_2 = myRef.child(mainScreen.userId);
                        Log.d("lessonType", "true");
                        user_2.child("comp_w").setValue("l");
                        int GeldToGive = 4;
                        mainScreen.Geld += GeldToGive * 2;
                        int currentGeld = mainScreen.Geld;
                        int newGeld = currentGeld +( GeldToGive * 2);
                        user.child("geld").setValue(newGeld);
                        LessonActivity.shared_xp2 = LessonActivity.shared_xp2 * 2;
                    }
                    if (!mainScreen.hasDone){
                        user.child("hasDoneLesson").setValue(true);
                        mainScreen.hasDone = true;
                        user.child("streak").setValue(String.valueOf(Integer.parseInt(mainScreen.streak) + 1));
                        mainScreen.streak = String.valueOf(Integer.parseInt(mainScreen.streak) + 1);
                        if (Integer.parseInt(mainScreen.streak7) > 0) {
                            user.child("7streak").setValue(String.valueOf(Integer.parseInt(mainScreen.streak7) + 1));
                            mainScreen.streak7 = String.valueOf(Integer.parseInt(mainScreen.streak7) + 1);
                        }
                    }
                    user.child("xp").setValue(xp + LessonActivity.shared_xp2);
                    user.child("weekXp").setValue(weekXp + LessonActivity.shared_xp2);
                    if (mainScreen.w.equals("py")){
                        user.child("pyXp").setValue(pyXp + LessonActivity.shared_xp2);
                        mainScreen.pyXp = pyXp + LessonActivity.shared_xp2;
                    }
                    else if (mainScreen.w.equals("html")){
                        user.child("htmlXp").setValue(htmlXp + LessonActivity.shared_xp2);
                        mainScreen.htmlXp = htmlXp + LessonActivity.shared_xp2;
                    }
                    if (Integer.parseInt(mainScreen.streak) > Integer.parseInt(mainScreen.maxStreak)){
                        user.child("maxStreak").setValue(mainScreen.streak);
                        mainScreen.maxStreak = mainScreen.streak;
                    }
                    List<String> str_old_progress = Arrays.asList(old_progress.split(",|\\~"));
                    if (!str_old_progress.contains(MainActivity.id)) {
                        user.child("progress").setValue(old_progress + "," + MainActivity.id + "~" + MainActivity.name);
                        mainScreen.progress = old_progress + MainActivity.id + "~" + MainActivity.name + ",";
                    }

                    finishLsnBtn.setVisibility(View.VISIBLE);
                    user.child("hasDoneLesson").setValue(true);
                    mainScreen.hasDone = true;
                }
    }

}

