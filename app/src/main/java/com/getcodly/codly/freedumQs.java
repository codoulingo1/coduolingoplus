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


    Boolean isOpt1Pressed = false;
    Boolean isOpt2Pressed = false;
    Boolean isOpt3Pressed = false;
    Boolean isOpt4Pressed = false;

    String selectedOpt = null;

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
                if (selectedOpt.equals(freedum_hashmap.get("Answer"))){
                    showCorrect();
                }
                else{
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
                if (isOpt1Pressed = false){
                    opt1.setScaleX(1.1f);
                    opt1.setScaleY(1.1f);
                    opt2.setScaleX(1f);
                    opt2.setScaleY(1f);//
                    opt3.setScaleX(1f);
                    opt3.setScaleY(1f);
                    opt4.setScaleX(1f);
                    opt4.setScaleY(1f);
                    continueBtn.setVisibility(View.VISIBLE);
                    selectedOpt = opt1.getText().toString();
                    isOpt1Pressed = true;
                } else {
                    opt1.setScaleX(1f);
                    opt1.setScaleY(1f);
                    opt2.setScaleX(1f);
                    opt2.setScaleY(1f);
                    opt3.setScaleX(1f);
                    opt3.setScaleY(1f);
                    opt4.setScaleX(1f);
                    opt4.setScaleY(1f);
                    continueBtn.setVisibility(View.VISIBLE);
                    selectedOpt = opt1.getText().toString();
                    isOpt1Pressed = false;
                }
            }
        });

        opt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpt2Pressed = false){
                    opt1.setScaleX(1f);
                    opt1.setScaleY(1f);
                    opt2.setScaleX(1.1f);
                    opt2.setScaleY(1.1f);
                    opt3.setScaleX(1f);
                    opt3.setScaleY(1f);
                    opt4.setScaleX(1f);
                    opt4.setScaleY(1f);
                    continueBtn.setVisibility(View.VISIBLE);
                    selectedOpt = opt2.getText().toString();
                    isOpt2Pressed = true;
                } else {
                    opt1.setScaleX(1f);
                    opt1.setScaleY(1f);
                    opt2.setScaleX(1f);
                    opt2.setScaleY(1f);
                    opt3.setScaleX(1f);
                    opt3.setScaleY(1f);
                    opt4.setScaleX(1f);
                    opt4.setScaleY(1f);
                    continueBtn.setVisibility(View.VISIBLE);
                    selectedOpt = opt2.getText().toString();
                    isOpt2Pressed = false;
                }
            }
        });
        opt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpt2Pressed = false){
                    opt1.setScaleX(1f);
                    opt1.setScaleY(1f);
                    opt2.setScaleX(1f);
                    opt2.setScaleY(1f);
                    opt3.setScaleX(1.1f);
                    opt3.setScaleY(1.1f);
                    opt4.setScaleX(1f);
                    opt4.setScaleY(1f);
                    continueBtn.setVisibility(View.VISIBLE);
                    selectedOpt = opt3.getText().toString();
                    isOpt3Pressed = true;
                } else {
                    opt1.setScaleX(1f);
                    opt1.setScaleY(1f);
                    opt2.setScaleX(1f);
                    opt2.setScaleY(1f);
                    opt3.setScaleX(1f);
                    opt3.setScaleY(1f);
                    opt4.setScaleX(1f);
                    opt4.setScaleY(1f);
                    continueBtn.setVisibility(View.VISIBLE);
                    selectedOpt = opt3.getText().toString();
                    isOpt3Pressed = false;
                }
            }
        });
        opt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpt2Pressed = false){
                    opt1.setScaleX(1f);
                    opt1.setScaleY(1f);
                    opt2.setScaleX(1f);
                    opt2.setScaleY(1f);
                    opt3.setScaleX(1.1f);
                    opt3.setScaleY(1.1f);
                    opt4.setScaleX(1.1f);
                    opt4.setScaleY(1.1f);
                    continueBtn.setVisibility(View.VISIBLE);
                    selectedOpt = opt4.getText().toString();
                    isOpt4Pressed = true;
                } else {
                    opt1.setScaleX(1f);
                    opt1.setScaleY(1f);
                    opt2.setScaleX(1f);
                    opt2.setScaleY(1f);
                    opt3.setScaleX(1f);
                    opt3.setScaleY(1f);
                    opt4.setScaleX(1f);
                    opt4.setScaleY(1f);
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
