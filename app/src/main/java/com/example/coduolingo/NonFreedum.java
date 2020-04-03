package com.example.coduolingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class NonFreedum extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_freedum);
        final Button opt1 = (Button) findViewById(R.id.button1);
        final Button opt2 = (Button) findViewById(R.id.button2);
        final Button opt3 = (Button) findViewById(R.id.button3);
        final Button opt4 = (Button) findViewById(R.id.button4);
        final Button opt5 = (Button) findViewById(R.id.button5);
        final Button opt6 = (Button) findViewById(R.id.button6);
        Button[] ops = {opt1, opt2, opt3, opt4, opt5, opt6};
        for (Button op : ops){
            if (op.getText().toString().length()==0) {
                op.setVisibility(View.INVISIBLE);
            }
            }
        final Button ans1 = (Button) findViewById(R.id.ans);
        final Button ans2 = (Button) findViewById(R.id.ans2);
        final Button ans3 = (Button) findViewById(R.id.ans3);
        final Button ans4 = (Button) findViewById(R.id.ans4);
        final Button ans5 = (Button) findViewById(R.id.ans5);
        ans1.setVisibility(View.INVISIBLE);
        ans2.setVisibility(View.INVISIBLE);
        ans3.setVisibility(View.INVISIBLE);
        ans4.setVisibility(View.INVISIBLE);
        ans5.setVisibility(View.INVISIBLE);

        opt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when play is clicked show stop button and hide play button
                choice(opt1);

            }
        });
        opt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when play is clicked show stop button and hide play button
                choice(opt2);

            }
        });
        opt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when play is clicked show stop button and hide play button
                choice(opt3);

            }
        });
        opt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when play is clicked show stop button and hide play button
                choice(opt4);

            }
        });
        opt5.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View v) {
            //when play is clicked show stop button and hide play button
            choice(opt5);

        }
    });
        opt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when play is clicked show stop button and hide play button
                choice(opt6);

            }
        });
        ans1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when play is clicked show stop button and hide play button
                back_choice(ans1);

            }
        });
        ans2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when play is clicked show stop button and hide play button
                back_choice(ans2);

            }
        });
        ans3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when play is clicked show stop button and hide play button
                back_choice(ans3);

            }
        });
        ans4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when play is clicked show stop button and hide play button
                back_choice(ans4);

            }
        });
        ans5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when play is clicked show stop button and hide play button
                back_choice(ans5);

            }
        });

    }
    public void choice(Button opt){
        final Button ans1 = (Button) findViewById(R.id.ans);
        final Button ans2 = (Button) findViewById(R.id.ans2);
        final Button ans3 = (Button) findViewById(R.id.ans3);
        final Button ans4 = (Button) findViewById(R.id.ans4);
        final Button ans5 = (Button) findViewById(R.id.ans5);
        Button[] buttons = {ans1, ans2, ans3, ans4, ans5};
        for (Button btn : buttons){
            if (btn.getText().toString().length()==0){
                if(opt.getText().toString().length()>0){
                    Log.d("eyalo", "gojo");
                    btn.setVisibility(View.VISIBLE);
                    btn.setText(opt.getText().toString());
                    opt.setText("");
                    break;
            }}
        }
    }
    public void back_choice(Button ans){
        final Button opt1 = (Button) findViewById(R.id.button1);
        final Button opt2 = (Button) findViewById(R.id.button2);
        final Button opt3 = (Button) findViewById(R.id.button3);
        final Button opt4 = (Button) findViewById(R.id.button4);
        final Button opt5 = (Button) findViewById(R.id.button5);
        final Button opt6 = (Button) findViewById(R.id.button6);
        Button[] ops = {opt1, opt2, opt3, opt4, opt5, opt6};
        for (Button op : ops){
            if (op.getText().toString().length()==0) {
                op.setVisibility(View.VISIBLE);
                op.setText(ans.getText().toString());
                ans.setText("");
                ans.setVisibility(View.INVISIBLE);
                break;
            }
        }
    }
    void getLength(){
        //String btnText;
        //btnText = btn2.getText().toString();
        //btnText.length();

    }

}
