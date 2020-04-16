package com.example.coduolingo;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {


    public static String id = "57999";
    public static String name = "TRY";
    CountDownTimer mcountdown;
    TextView w;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        w = (TextView) findViewById(R.id.textView);
        ActivityCompat.requestPermissions((Activity) MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        ActivityCompat.requestPermissions((Activity) MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.activity_main);
        final AlertDialog dialog = builder.create();
        dialog.setMessage("טוען שיעור: " + name);
        DownloadReadlessons.downloadlesson(id, MainActivity.this);
        mcountdown = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long l) {
                dialog.show();
                Log.d("Loading", "Loading");
            }

            @Override
            public void onFinish() {
                dialog.dismiss();
                startActivity(new Intent(MainActivity.this, LessonActivity.class));
            }
        }.start();

    }
}


