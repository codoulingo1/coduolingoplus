package com.example.coduolingo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Debug;
import android.widget.Button;

public class NonFreedum extends AppCompatActivity {

    Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_freedum);
        btn2 = (Button) findViewById(R.id.button2);
        getLength();
    }

    void getLength(){
        //String btnText;
        //btnText = btn2.getText().toString();
        //btnText.length();

        btn2.length();
        Debug
    }

}
