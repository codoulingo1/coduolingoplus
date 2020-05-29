package com.example.coduolingo;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
        DownloadReadlessons.downloadlesson(id, MainActivity.this);

        mcountdown = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long l) {
                //dialog.show();
                Log.d("Loading", "Loading");
                progressTime();
                loadingTextView.setText("טוען שיעור: " + name);
            }

            @Override
            public void onFinish() {
                dialog.dismiss();
                startActivity(new Intent(MainActivity.this, LessonActivity.class));
            }
        }.start();

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

}


