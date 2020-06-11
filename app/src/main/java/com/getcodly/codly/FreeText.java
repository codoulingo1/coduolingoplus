package com.getcodly.codly;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.view.textclassifier.TextSelection;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class FreeText extends AppCompatActivity {

    TextView qs;
    ImageButton submit;
    RelativeLayout popupTruee;
    ImageButton continueBtn10;
    RelativeLayout popup11;
    ImageButton continueBtn11;
    private AnimatedVectorDrawable animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_free_text);
        popupTruee = (RelativeLayout) findViewById(R.id.PopupTruee);
        continueBtn10 = (ImageButton) findViewById(R.id.continueBtn10);
        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
        popup11 = (RelativeLayout) findViewById(R.id.popup11);
        continueBtn11 = (ImageButton) findViewById(R.id.continueBtn11);
        pb.setProgress(LessonActivity.pr);
        submit = (ImageButton) findViewById(R.id.button);
        qs = (TextView) findViewById(R.id.textView);
        Log.d("finished", "freetext");
        SpannableStringBuilder builder=new SpannableStringBuilder();
        for (int i = 0; i < 10; i = i + 1) {
            try {
                if (i % 2 == 0 || i==0) {
                    builder.append(LessonActivity.shared_hashmap.get("qs").split("\\*")[i]);
                }
                else{
                    SpannableString txtSpannable = new SpannableString(LessonActivity.shared_hashmap.get("qs").split("\\*")[i]);
                    StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
                    txtSpannable.setSpan(boldSpan, 0, LessonActivity.shared_hashmap.get("qs").split("\\*")[i].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    builder.append(txtSpannable);
                }
            }catch (Exception e){
                break;
            }
        }
        qs.setText(builder);
        Log.d(String.valueOf(LessonActivity.shared_hashmap.get("additional").length()), String.valueOf(LessonActivity.shared_hashmap.get("additional").length()));
        final EditText inp = (EditText) findViewById(R.id.inp);
        inp.setText(LessonActivity.shared_hashmap.get("additional"));
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ans = inp.getText().toString();
                if (Text.eqnova(ans, LessonActivity.shared_hashmap.get("Answer"), LessonActivity.shared_hashmap.get("additional").length())) {
                    showCorrect();
                }

                else{
                    showWrong();
                }
            }
        }
        );
        inp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

    }
    void showCorrect() {
        /*popupTruee.setVisibility(View.VISIBLE);
        continueBtn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LessonActivity.j++;
                //LessonActivity.points++;
                startActivity(new Intent(FreeText.this, LessonActivity.class));
            }
        });*/
        submit.setImageResource(R.drawable.avd_anim);
        animate();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LessonActivity.j++;
                LessonActivity.shared_xp = LessonActivity.shared_xp + 2;
                //LessonActivity.points++;
                startActivity(new Intent(FreeText.this, LessonActivity.class));
            }
        });
    }

    void showWrong(){
        popup11.setVisibility(View.VISIBLE);
        continueBtn11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(LessonActivity.shared_xp>=11){
                    LessonActivity.shared_xp = LessonActivity.shared_xp - 1;
                }
                startActivity(new Intent(FreeText.this, FreeText.class));
                overridePendingTransition(0, 0);
            }
        });

    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        DialogBack dialogBack = new DialogBack();
        dialogBack.show(getSupportFragmentManager(), "Example Dialog");
    }

    public void animate(){
        Drawable d = submit.getDrawable();
        if (d instanceof AnimatedVectorDrawable) {

            Log.d("testanim", "onCreate: instancefound" + d.toString());
            animation = (AnimatedVectorDrawable) d;
            animation.start();
        }
    }
}
