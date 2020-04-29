package com.example.coduolingo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.HashMap;

public class freedumQs extends AppCompatActivity {

    ImageButton continueBtn;
    int isCorrect = 0; //0 = empty, 1 = false, 2 = correct

    Button opt1;
    Button opt2;
    Button opt3;
    Button opt4;

    int isClicked1 = 0;
    int isClicked2 = 0;
    int isClicked3 = 0;
    int isClicked4 = 0;


    ProgressBar pb;
    TextView l;
    Button buttonl;
    HashMap<String, String> freedum_hashmap;
    RelativeLayout popup;
    RelativeLayout popupWrong;
    ImageButton continueBtn4;
    ImageButton continueBtn6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freedum_qs);
        continueBtn = (ImageButton) findViewById(R.id.continueBtn3);
        popupWrong = (RelativeLayout) findViewById(R.id.popup2);
        //continueBtn.setBackgroundColor(Color.TRANSPARENT);
        popup = (RelativeLayout) findViewById(R.id.Popup1);
        opt1 = (Button) findViewById(R.id.Opt1);
        opt2 = (Button) findViewById(R.id.Opt2);
        opt3 = (Button) findViewById(R.id.Opt3);
        opt4 = (Button) findViewById(R.id.Opt4);
        continueBtn4 = (ImageButton) findViewById(R.id.continueBtn4);
        continueBtn6 = (ImageButton) findViewById(R.id.continueBtn6);
        pb = (ProgressBar) findViewById(R.id.progressBar);
        //l = (TextView) findViewById(R.id.l2);
        //buttonl = (Button) findViewById(R.id.buttonl);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCorrect == 2){
                    showCorrect();
                }else if (isCorrect == 1){
                    showWrong();
                } else if(isCorrect == 0){

                }
            }
        });
        freedumQs();
    }

    public void freedumQs() {
        freedum_hashmap = LessonActivity.shared_hashmap;
        TextView fr = (TextView)findViewById(R.id.freedumQuestion);
        fr.setText(freedum_hashmap.get("qs"));
        //buttonl.setVisibility(View.INVISIBLE);
        pb.setProgress(LessonActivity.pr);
        String[] optAns = freedum_hashmap.get("Content").split(",");
        final String freedum_id = LessonActivity.shared_id;
        final String freedum_name = LessonActivity.shared_name;
        opt1.setText(optAns[0]);
        opt2.setText(optAns[1]);
        try {
            opt3.setText(optAns[2]);
        }
        catch (Exception e) {
            opt3.setVisibility(View.INVISIBLE);
        }
        try {
            opt4.setText(optAns[3]);
        }
        catch (Exception e) {
            opt4.setVisibility(View.INVISIBLE);
        }
        opt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isClicked1 != 1){
                    isClicked1 = 1;
                    opt1.setScaleX(1.1f);
                    opt1.setScaleY(1.1f);

                    if (opt1.getText().toString().equals(freedum_hashmap.get("Answer"))){
                        isCorrect = 2;
                    }
                    else {
                        isCorrect = 1;
                    }
                }
                else
                {
                    isCorrect = 0;
                    isClicked1 = 0;
                    opt1.setScaleX(1);
                    opt1.setScaleY(1);
                }

            }
        });

        opt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isClicked2 != 1){
                    isClicked2 = 1;
                    opt2.setScaleX(1.1f);
                    opt2.setScaleY(1.1f);

                    if (opt2.getText().toString().equals(freedum_hashmap.get("Answer"))){
                        isCorrect = 2;
                    }
                    else {
                        isCorrect = 1;
                    }
                }
                else
                {
                    isCorrect = 0;
                    isClicked2 = 0;
                    opt2.setScaleX(1);
                    opt2.setScaleY(1);
                }

            }
        });opt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isClicked3 != 1){
                    isClicked3 = 1;
                    opt3.setScaleX(1.1f);
                    opt3.setScaleY(1.1f);

                    if (opt3.getText().toString().equals(freedum_hashmap.get("Answer"))){
                        isCorrect = 2;
                    }
                    else {
                        isCorrect = 1;
                    }
                }
                else
                {
                    isCorrect = 0;
                    isClicked3 = 0;
                    opt3.setScaleX(1);
                    opt3.setScaleY(1);
                }

            }
        });opt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isClicked4 != 1){
                    isClicked4 = 1;
                    opt4.setScaleX(1.1f);
                    opt4.setScaleY(1.1f);

                    if (opt4.getText().toString().equals(freedum_hashmap.get("Answer"))){
                        isCorrect = 2;
                    }
                    else {
                        isCorrect = 1;
                    }
                }
                else
                {
                    isCorrect = 0;
                    isClicked4 = 0;
                    opt4.setScaleX(1);
                    opt4.setScaleY(1);
                }

            }
        });

    }

    void showCorrect() {
        /*buttonl.setVisibility(View.VISIBLE);
        l.setText("כל הכבוד");
        buttonl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LessonActivity.j++;
                    startActivity(new Intent(freedumQs.this, LessonActivity.class));
                }
        }); */
        popup.setVisibility(View.VISIBLE);
        continueBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LessonActivity.j++;
                //LessonActivity.points++;
                startActivity(new Intent(freedumQs.this, LessonActivity.class));
            }
        });
    }
    void showWrong(){
        /*buttonl.setVisibility(View.VISIBLE);
        l.setText("נסה שוב");
        buttonl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(freedumQs.this, freedumQs.class));
            }
        });*/
        popupWrong.setVisibility(View.VISIBLE);
        continueBtn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(freedumQs.this, freedumQs.class));
            }
        });

    }
}
