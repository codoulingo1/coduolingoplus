package com.getcodly.codly;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    public static String id;
    public static String name;
    CountDownTimer mcountdown;
    TextView w;
    int i = 0;
    ProgressBar loading;
    int progress;
    int counter = 0;
    TextView loadingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        w = (TextView) findViewById(R.id.textView);
        loading = (ProgressBar) findViewById(R.id.loadingProgress);
        loadingTextView = (TextView) findViewById(R.id.loadingText);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.activity_main);
        final AlertDialog dialog = builder.create();
        dialog.setMessage("טוען שיעור: " + name);
        for (int i = 1; i<20; i++){
            try {
                Target target = new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                };
                Picasso.with(MainActivity.this).load(DownloadReadlessons.readImage(id, name, String.valueOf(i), MainActivity.this)).resizeDimen(R.dimen.image_size, R.dimen.image_size).into(target);
                Log.d("imageUrl", DownloadReadlessons.readImage(id, name, String.valueOf(i), MainActivity.this));
            }
            catch (Exception e) {
                break;
            }
        }

        if(mainScreen.LessonType.equals("practice")){
            DownloadReadlessons.downloadPractice(tree.practiceID, 8, MainActivity.this, new DownloadReadlessons.MyCallback() {
                @Override
                public void onCallback(String value) {
                    Log.d("MainActivity", value);
                    Wait(1);
                    startActivity(new Intent(MainActivity.this, LessonActivity.class));
                }
            });
        }else if (mainScreen.LessonType.equals("comp")){
            Log.d("MainActivity", id + name);
            DownloadReadlessons.downloadcomp(id, MainActivity.this, new DownloadReadlessons.MyCallback() {
                @Override
                public void onCallback(String value) {
                    Log.d("MainActivity", value+"a");
                    Log.d("MainActivity", id);
                    DownloadReadlessons.downloadlesson(id, MainActivity.this, new DownloadReadlessons.MyCallback() {
                        @Override
                        public void onCallback(String value) {
                            Log.d("MainActivity", value);

                            startActivity(new Intent(MainActivity.this, LessonActivity.class));
                        }
                    });
                }
            });
        }
        else {
            DownloadReadlessons.downloadlesson(id, MainActivity.this, new DownloadReadlessons.MyCallback() {
                @Override
                public void onCallback(String value) {
                    Log.d("MainActivity", value);

                    startActivity(new Intent(MainActivity.this, LessonActivity.class));
                }
            });
        }
    } //savta

    void progressTime(){
        final Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run()
            {
                counter++;
                loading.setProgress(counter);

                if(counter == 30)
                    t.cancel();
            }
        };

        t.schedule(tt,0,30);
    }

    @Override
    public void onBackPressed() {
        return;
    }

    void Wait(int seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


