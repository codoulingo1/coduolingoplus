package com.example.coduolingo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class freedumQs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freedum_qs);
    }

    public void freedumQs(int NumOfQs, int ID, int Qs_Num) {
        TextView Question = findViewById(R.id.freedumQuestion);
        Button opt1 = findViewById(R.id.Opt1);
        Button opt2 = findViewById(R.id.Opt2);
        Button opt3 = findViewById(R.id.Opt3);
        Button opt4 = findViewById(R.id.Opt4);
        HashMap<String, String> freedum_hashmap = MainActivity.shared_hashmap;
        int qs_num = MainActivity.j;
        TextView fr = (TextView)findViewById(R.id.freedumQuestion);
        fr.setText(freedum_hashmap.get("qs"));
        Button b1 = (Button)findViewById(R.id.Opt1);
        String buttonText1 = b1.getText().toString();
        Button b2 = (Button)findViewById(R.id.Opt2);
        String buttonText2 = b2.getText().toString();
        Button b3 = (Button)findViewById(R.id.Opt3);
        String buttonText3 = b3.getText().toString();
        Button b4 = (Button)findViewById(R.id.Opt4);
        String buttonText4 = b3.getText().toString();

    }
}
