package com.getcodly.codly;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
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

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;

public class FreeTextPy extends AppCompatActivity {
    TextView qs;
    ImageButton submit;
    RelativeLayout popupTruee;
    ImageButton backBtn;
    ImageButton continueBtn10;
    RelativeLayout popup11;
    ImageButton continueBtn11;
    private AnimatedVectorDrawable animation;
    TextView out;
    public static EditText inp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_text_py);
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
        out = (TextView) findViewById(R.id.HtmlView2);
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
            textCPy();
        }
        if (!tree.loadAgain.equals("")){
            inp.setText(tree.loadAgain);
            tree.loadAgain = "";
            textCPy();
        }
        submit.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          String ans = inp.getText().toString();
                                          ReadWrite.write(FreeTextPy.this.getFilesDir()
                                                  + "/" + "pyCode", ans.replace("print", "pr").replace("input", "inp"));
                                          try {
                                              Python py = Python.getInstance();
                                              PyObject pyFile = py.getModule("compiler_2");
                                              out.setText(pyFile.callAttr("main").toString().replace("|", System.getProperty("line.separator")));
                                          }catch (Exception e) {
                                              Log.d("eroor", e.getLocalizedMessage());
                                              out.setText(e.getLocalizedMessage());
                                          }
                                          try {
                                              if (ans.equals(LessonActivity.shared_hashmap.get("Answer"))) {
                                                  showCorrect();
                                              }
                                              else{
                                                  tree.loadAgain = ans;
                                                  wt.setTextSize(14);
                                                  wt.setText("התשובה:" + LessonActivity.shared_hashmap.get("Answer"));
                                                  showWrong();
                                              }
                                          }catch (Exception e){
                                              if ((ans.equals(LessonActivity.shared_hashmap.get("Answer")))) {
                                                  showCorrect();
                                              }
                                              else{
                                                  tree.loadAgain = ans;
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
                    startActivity(new Intent(FreeTextPy.this, LessonActivity.class));
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
                startActivity(new Intent(FreeTextPy.this, LessonActivity.class));
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
                startActivity(new Intent(FreeTextPy.this, FreeTextPy.class));
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
    public static void textCPy() {
        String[] cc = new String[]{"type", "range", "dict", "int", "str", "float", "input"};
        String[] applesin = new String[]{"print", "for", "if", "while", "try", "except"};
        String htmlText = inp.getText().toString();
        try {
            SpannableStringBuilder builder = new SpannableStringBuilder();
            SpannableString str1 = new SpannableString(htmlText);
            for (String codeWord : cc) {
                int startt = 0;
                while (htmlText.indexOf(codeWord, startt) > -1) {
                    Log.d("hi", String.valueOf(htmlText.indexOf(codeWord, 3)));
                    str1.setSpan(new ForegroundColorSpan(Color.rgb(170, 109, 173)), htmlText.indexOf(codeWord, startt), htmlText.indexOf(codeWord, startt) + codeWord.length(), 0);
                    startt = htmlText.indexOf(codeWord, startt) + codeWord.length();
                }
            }
            for (String codeWord2 : applesin) {
                int startt = 0;
                while (htmlText.indexOf(codeWord2, startt) > -1) {
                    Log.d("hi", String.valueOf(htmlText.indexOf(codeWord2, 3)));
                    str1.setSpan(new ForegroundColorSpan(Color.rgb(255, 140, 0)), htmlText.indexOf(codeWord2, startt), htmlText.indexOf(codeWord2, startt) + codeWord2.length(), 0);
                    startt = htmlText.indexOf(codeWord2, startt) + codeWord2.length();
                }
            }
            inp.setText(str1);
        } catch (Exception e) {

        }
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
