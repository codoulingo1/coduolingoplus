package com.getcodly.codly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.util.HashMap;

public class LoadActivity extends AppCompatActivity {
    HashMap<String, String> date;
    private ImageView logo;
    AnimatedVectorDrawableCompat avd;
    AnimatedVectorDrawable avd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        logo = findViewById(R.id.imageView8);
        Drawable drawable = logo.getDrawable();

        if(drawable instanceof AnimatedVectorDrawableCompat){
            avd = (AnimatedVectorDrawableCompat) drawable;
            avd.start();
        } else if(drawable instanceof  AnimatedVectorDrawable){
            avd2 = (AnimatedVectorDrawable) drawable;
            avd2.start();
        }
        date = DownloadReadlessons.get_sLast_lesson2(ReadWrite.read(this.getFilesDir() + File.separator + "user"), new DownloadReadlessons.HashCallback() {
            @Override
            public void onCallback(HashMap<String, String> value) {

                Log.d("v", "v");
                mainScreen.streak = value.get("streak");
                mainScreen.maxStreak = value.get("maxStreak");
                mainScreen.streak7 = value.get("7streak");
                mainScreen.progress = String.valueOf(value.get("cProgress"));
                mainScreen.isDownload = true;
                mainScreen.name = String.valueOf(value.get("name"));
                mainScreen.img = String.valueOf(value.get("img"));
                mainScreen.friends = String.valueOf(value.get("friends"));
                mainScreen.user_xp = Integer.parseInt(value.get("xp"));
                mainScreen.pyXp = Integer.parseInt(value.get("pyXp"));
                mainScreen.imgC = Integer.parseInt(value.get("imgC"));
                mainScreen.htmlXp = Integer.parseInt(value.get("htmlXp"));
                mainScreen.Geld = Integer.parseInt(value.get("geld"));
                mainScreen.weekXp = Integer.parseInt(value.get("weekXp"));
                mainScreen.hasDone = Boolean.parseBoolean(value.get("hasDoneLesson"));
                startActivity(new Intent(LoadActivity.this, mainScreen.class));

            }
        });
    }
}
