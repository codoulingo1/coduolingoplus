package com.example.coduolingo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class freedumQs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freedum_qs);
        freedumQs();
    }

    public void freedumQs() {
        Button opt1 = findViewById(R.id.Opt1);
        Button opt2 = findViewById(R.id.Opt2);
        Button opt3 = findViewById(R.id.Opt3);
        Button opt4 = findViewById(R.id.Opt4);
        HashMap<String, String> freedum_hashmap = MainActivity.shared_hashmap;
        int qs_num = MainActivity.j;
        TextView fr = (TextView)findViewById(R.id.freedumQuestion);
        fr.setText(freedum_hashmap.get("qs"));
        String[] optAns = freedum_hashmap.get("Content").split(",");
        Log.d("aviam tuam", optAns[0]);
        opt1.setText(optAns[0]);
        opt2.setText(optAns[1]);
        try {
            opt3.setText(optAns[2]);
        }
        catch (Exception e) {

        }

        opt4.setText(optAns[3]);
    }
}
