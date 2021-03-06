package com.getcodly.codly;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FreeTextR extends AppCompatActivity {

    TextView qs;
    ImageButton submit;
    public static String loadAgain2 = "";
    RelativeLayout popupTruee;
    ImageButton backBtn;
    ImageButton continueBtn10;
    RelativeLayout popup11;
    ImageButton continueBtn11;
    private AnimatedVectorDrawable animation;
    EditText inp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_text_r);
        popupTruee = (RelativeLayout) findViewById(R.id.PopupTruee);
        continueBtn10 = (ImageButton) findViewById(R.id.continueBtn10);
        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
        final TextView wt = (TextView) findViewById(R.id.textView11);
        backBtn = (ImageButton) findViewById(R.id.backBtn4);
        popup11 = (RelativeLayout) findViewById(R.id.popup11);
        continueBtn11 = (ImageButton) findViewById(R.id.continueBtn11);
        pb.setProgress(LessonActivity.pr);
        submit = (ImageButton) findViewById(R.id.button);
        qs = (TextView) findViewById(R.id.textView);
        inp = (EditText) findViewById(R.id.inp);
        Log.d("finished", "freetext");
        SpannableStringBuilder builder=new SpannableStringBuilder();
        for (int i = 0; i < 10; i = i + 1) {
            try {
                if (i % 2 == 0 || i==0) {
                    builder.append(LessonActivity.shared_hashmap.get("qs").split("\\*")[i]);
                }
                else{
                    SpannableString txtSpannable = new SpannableString(LessonActivity.shared_hashmap.get("qs").split("\\*")[i]);
                    StyleSpan boldSpan = new StyleSpan(Typeface.BOLD); //additional = קוד מקורי
                    txtSpannable.setSpan(boldSpan, 0, LessonActivity.shared_hashmap.get("qs").split("\\*")[i].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    builder.append(txtSpannable);
                }
            }catch (Exception e){
                break;
            }
        }
        qs.setText(builder);
        Log.d(String.valueOf(LessonActivity.shared_hashmap.get("additional").length()), String.valueOf(LessonActivity.shared_hashmap.get("additional").length()));
        EditText inp = (EditText) findViewById(R.id.inp);
        if (!LessonActivity.shared_hashmap.get("additional").equals("none")) {
            inp.setText(LessonActivity.shared_hashmap.get("additional"));
        }
        if (!loadAgain2.equals("")){
            inp.setText(loadAgain2);
            loadAgain2 = "";
        }
        submit.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          String ans = inp.getText().toString();
                                          try {
                                              if (Text.eqnova(ans, LessonActivity.shared_hashmap.get("Answer"), LessonActivity.shared_hashmap.get("additional").length())) {
                                                  showCorrect();
                                              }
                                              else{
                                                  loadAgain2 = ans;
                                                  wt.setTextSize(14);
                                                  wt.setText("התשובה:" + LessonActivity.shared_hashmap.get("Answer"));
                                                  showWrong();
                                              }
                                          }catch (Exception e){
                                              if ((ans.equals(LessonActivity.shared_hashmap.get("Answer")))) {
                                                  showCorrect();
                                              }
                                              else{
                                                  loadAgain2 = ans;
                                                  wt.setTextSize(14);
                                                  wt.setText("התשובה:" + LessonActivity.shared_hashmap.get("Answer"));
                                                  showWrong();
                                              }
                                          }
                                      }
                                  }
        );
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LessonActivity.j > 1) {
                    LessonActivity.j = LessonActivity.j - 1;
                    LessonActivity.shared_xp = LessonActivity.shared_xp - 1;
                    startActivity(new Intent(FreeTextR.this, LessonActivity.class));
                    overridePendingTransition(0, 0);
                }
            }
        });
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
        submit.setImageResource(R.drawable.avd_anim);
        animate();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setClickable(false);
                LessonActivity.j++;
                mainScreen.lessonWr++;
                LessonActivity.shared_xp = LessonActivity.shared_xp + 2;
                //LessonActivity.points++;
                startActivity(new Intent(FreeTextR.this, LessonActivity.class));
            }
        });
    }

    void showWrong(){
        popup11.setVisibility(View.VISIBLE);
        continueBtn11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainScreen.lessonWr--;
                if(LessonActivity.shared_xp>=11){
                    LessonActivity.shared_xp = LessonActivity.shared_xp - 1;
                }
                startActivity(new Intent(FreeTextR.this, FreeTextR.class));
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
