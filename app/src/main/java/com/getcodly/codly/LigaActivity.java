package com.getcodly.codly;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class LigaActivity extends AppCompatActivity {
    HashMap<String, ArrayList<String>> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_liga);
        data = DownloadReadlessons.get_liga("117500863152973188952", new DownloadReadlessons.HashCallback2() {
            @Override
            public void onCallback(HashMap<String, ArrayList<String>> value) {
                Log.d("LigaVals", value.get("names").get(0));
            }
        });
    }
}
