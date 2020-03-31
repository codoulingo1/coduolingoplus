package com.example.coduolingo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

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
    }
}
