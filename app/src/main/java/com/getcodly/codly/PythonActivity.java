package com.getcodly.codly;

import android.os.Bundle;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PythonActivity extends AppCompatActivity {
    String last;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_python);
        ReadWrite.write(PythonActivity.this.getFilesDir() + "/" + "a", "");
        Python.start(new AndroidPlatform(PythonActivity.this));
        new Thread(new Runnable() {
            public void run(){
                ReadWrite.write(PythonActivity.this.getFilesDir() + "/" + "pyCode", "a=0\nwhile(a<10):\n   print(a)\n   a = a + 1".replace("print", "pr"));
                Python py = Python.getInstance();
                PyObject pyFile = py.getModule("compiler");
                pyFile.callAttr("main");
            }
        }).start();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //Log.d("hi", ReadWrite.read(PythonActivity.this.getFilesDir() + "/" + "a") + "0");
                if (!ReadWrite.read(PythonActivity.this.getFilesDir() + "/" + "a").equals(last)) {
                    Log.d("hi", ReadWrite.read(PythonActivity.this.getFilesDir() + "/" + "a"));
                    last = ReadWrite.read(PythonActivity.this.getFilesDir() + "/" + "a");
                }
            }
        }, 0, 1000);
    }
}


