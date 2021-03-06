package com.getcodly.codly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
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
import java.util.concurrent.TimeUnit;

public class freedumQs extends AppCompatActivity {

    ImageButton continueBtn;
    Boolean isCorrect = false;

    Button opt1;
    Button opt2;
    Button opt3;
    Button opt4;

    private AnimatedVectorDrawable animation;


    ProgressBar pb;
    TextView l;
    Button buttonl;
    HashMap<String, String> freedum_hashmap;
    ImageButton backBtn;
    RelativeLayout popup;
    RelativeLayout popupWrong;
    ImageButton continueBtn4;
    ImageButton continueBtn6;

    Boolean isOpt1Pressed;
    Boolean isOpt2Pressed;
    Boolean isOpt3Pressed;
    Boolean isOpt4Pressed;

    String selectedOpt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freedum_qs);
        continueBtn = (ImageButton) findViewById(R.id.continueBtn3);
        continueBtn.setVisibility(View.INVISIBLE);
        popupWrong = (RelativeLayout) findViewById(R.id.popup2);
        //continueBtn.setBackgroundColor(Color.TRANSPARENT);
        popup = (RelativeLayout) findViewById(R.id.Popup1);
        backBtn = (ImageButton) findViewById(R.id.backBtn2);
        final TextView wt = (TextView) findViewById(R.id.textView6);
        opt1 = (Button) findViewById(R.id.Opt1);
        opt2 = (Button) findViewById(R.id.Opt2);
        opt3 = (Button) findViewById(R.id.Opt3);
        opt4 = (Button) findViewById(R.id.Opt4);
        continueBtn4 = (ImageButton) findViewById(R.id.continueBtn4);
        continueBtn6 = (ImageButton) findViewById(R.id.continueBtn6);
        pb = (ProgressBar) findViewById(R.id.progressBar);

        isOpt1Pressed = false;
        isOpt2Pressed = false;
        isOpt3Pressed = false;
        isOpt4Pressed = false;

        selectedOpt = null;


        //l = (TextView) findViewById(R.id.l2);
        //buttonl = (Button) findViewById(R.id.buttonl);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedOpt.equals(freedum_hashmap.get("Answer"))){
                    showCorrect();
                }
                else{
                    wt.setTextSize(14);
                    wt.setText("התשובה:" + LessonActivity.shared_hashmap.get("Answer"));
                    showWrong();
                }
            }
        });

        freedumQss();
    }

    public void freedumQss() {
        freedum_hashmap = LessonActivity.shared_hashmap;
        TextView fr = (TextView) findViewById(R.id.freedumQuestion);
        SpannableStringBuilder builder = new SpannableStringBuilder();
        for (int i = 0; i < 10; i = i + 1) {
            try {
                if (i % 2 == 0 || i == 0) {
                    builder.append(freedum_hashmap.get("qs").split("\\*")[i]);
                } else {
                    SpannableString txtSpannable = new SpannableString(freedum_hashmap.get("qs").split("\\*")[i]);
                    StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
                    txtSpannable.setSpan(boldSpan, 0, freedum_hashmap.get("qs").split("\\*")[i].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    builder.append(txtSpannable);
                }
            } catch (Exception e) {
                break;
            }
        }
        fr.setText(builder);
        //buttonl.setVisibility(View.INVISIBLE);
        pb.setProgress(LessonActivity.pr);
        String[] optAns = freedum_hashmap.get("Content").split(",");
        final String freedum_id = LessonActivity.shared_id;
        final String freedum_name = LessonActivity.shared_name;
        opt1.setText(optAns[0]);

        opt2.setText(optAns[1]);
        try {
            opt3.setText(optAns[2]);
        } catch (Exception e) {
            opt3.setVisibility(View.INVISIBLE);
        }
        try {
            opt4.setText(optAns[3]);
        } catch (Exception e) {
            opt4.setVisibility(View.INVISIBLE);
        }
        opt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOpt1Pressed){
                    opt1.setBackgroundResource(R.drawable.freedum_button_pressed);
                    opt2.setBackgroundResource(R.drawable.freedum_button);
                    opt3.setBackgroundResource(R.drawable.freedum_button);
                    opt4.setBackgroundResource(R.drawable.freedum_button);
                    continueBtn.setVisibility(View.VISIBLE);
                    selectedOpt = opt1.getText().toString();
                    isOpt1Pressed = true;
                    isOpt3Pressed = false;
                    isOpt2Pressed = false;
                    isOpt4Pressed = false;
                } else {
                    opt1.setBackgroundResource(R.drawable.freedum_button);
                    continueBtn.setVisibility(View.VISIBLE);
                    selectedOpt = opt1.getText().toString();
                    isOpt1Pressed = false;
                }
            }
        });

        opt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOpt2Pressed){
                    opt2.setBackgroundResource(R.drawable.freedum_button_pressed);
                    opt1.setBackgroundResource(R.drawable.freedum_button);
                    opt3.setBackgroundResource(R.drawable.freedum_button);
                    opt4.setBackgroundResource(R.drawable.freedum_button);
                    continueBtn.setVisibility(View.VISIBLE);
                    selectedOpt = opt2.getText().toString();
                    isOpt2Pressed = true;
                    isOpt1Pressed = false;
                    isOpt3Pressed = false;
                    isOpt4Pressed = false;
                } else {
                    opt2.setBackgroundResource(R.drawable.freedum_button);
                    continueBtn.setVisibility(View.VISIBLE);
                    selectedOpt = opt2.getText().toString();
                    isOpt2Pressed = false;
                }
            }
        });
        opt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOpt2Pressed){
                    opt3.setBackgroundResource(R.drawable.freedum_button_pressed);
                    opt1.setBackgroundResource(R.drawable.freedum_button);
                    opt2.setBackgroundResource(R.drawable.freedum_button);
                    opt4.setBackgroundResource(R.drawable.freedum_button);
                    continueBtn.setVisibility(View.VISIBLE);
                    selectedOpt = opt3.getText().toString();
                    isOpt3Pressed = true;
                    isOpt1Pressed = false;
                    isOpt2Pressed = false;
                    isOpt4Pressed = false;
                } else {
                    opt3.setBackgroundResource(R.drawable.freedum_button);
                    continueBtn.setVisibility(View.VISIBLE);
                    selectedOpt = opt3.getText().toString();
                    isOpt3Pressed = false;
                }
            }
        });
        opt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOpt2Pressed){
                    opt4.setBackgroundResource(R.drawable.freedum_button_pressed);
                    opt1.setBackgroundResource(R.drawable.freedum_button);
                    opt3.setBackgroundResource(R.drawable.freedum_button);
                    opt2.setBackgroundResource(R.drawable.freedum_button);
                    continueBtn.setVisibility(View.VISIBLE);
                    selectedOpt = opt4.getText().toString();
                    isOpt4Pressed = true;
                    isOpt1Pressed = false;
                    isOpt2Pressed = false;
                    isOpt3Pressed = false;
                } else {
                    opt4.setBackgroundResource(R.drawable.freedum_button);
                    continueBtn.setVisibility(View.VISIBLE);
                    selectedOpt = opt4.getText().toString();
                    isOpt4Pressed = false;
                }
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LessonActivity.j > 1) {
                    LessonActivity.j = LessonActivity.j - 1;
                    LessonActivity.shared_xp = LessonActivity.shared_xp - 1;
                    startActivity(new Intent(freedumQs.this, LessonActivity.class));
                    overridePendingTransition(0, 0);
                }
            }
        });
    }
    void showCorrect() {

        /*popup.setVisibility(View.VISIBLE);
        continueBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LessonActivity.j++;
                //LessonActivity.points++;
                startActivity(new Intent(freedumQs.this, LessonActivity.class));
                overridePendingTransition(0, 0);
            }
        });*/
        continueBtn.setImageResource(R.drawable.avd_anim);
        animate();
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueBtn.setClickable(false);
                LessonActivity.j++;
                mainScreen.lessonWr++;
                LessonActivity.shared_xp = LessonActivity.shared_xp + 1.5;
                //LessonActivity.points++;
                startActivity(new Intent(freedumQs.this, LessonActivity.class));
                overridePendingTransition(0, 0);
            }
        });

    }
    void showWrong(){
        popupWrong.setVisibility(View.VISIBLE);
        continueBtn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainScreen.lessonWr--;
                if(LessonActivity.shared_xp>=11){
                    LessonActivity.shared_xp = LessonActivity.shared_xp - 1;
                }
                startActivity(new Intent(freedumQs.this, freedumQs.class));
            }
        });

    }

    @Override
    public void onBackPressed(){
        DialogBack dialogBack = new DialogBack();
        dialogBack.show(getSupportFragmentManager(), "Example Dialog");
    }

    public void animate(){
        Drawable d = continueBtn.getDrawable();
        if (d instanceof AnimatedVectorDrawable) {
            animation = (AnimatedVectorDrawable) d;
            animation.start();
        }
    }

}
