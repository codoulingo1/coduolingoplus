package com.getcodly.codly;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class runQs extends AppCompatActivity {
    TextView qs;
    ImageButton submit;
    ImageButton runA;
    RelativeLayout popupTruee;
    ImageButton backBtn;
    TextView out;
    ImageButton continueBtn10;
    RelativeLayout popup11;
    ImageButton continueBtn11;
    private AnimatedVectorDrawable animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_qs);
        popupTruee = (RelativeLayout) findViewById(R.id.PopupTruee);
        continueBtn10 = (ImageButton) findViewById(R.id.continueBtn10);
        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
        backBtn = (ImageButton) findViewById(R.id.backBtn2);
        popup11 = (RelativeLayout) findViewById(R.id.popup11);
        continueBtn11 = (ImageButton) findViewById(R.id.continueBtn11);
        pb.setProgress(LessonActivity.pr);
        runA = (ImageButton) findViewById(R.id.runA);
        if (LessonActivity.shared_hashmap.get("type").equals("showpy")){
            runA.setVisibility(View.GONE);
        }
        submit = (ImageButton) findViewById(R.id.button);
        qs = (TextView) findViewById(R.id.textView);
        out = (TextView) findViewById(R.id.out);
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
        if (LessonActivity.shared_hashmap.get("type").equals("showPy")){
            inp.setKeyListener(null);
        }
        TextView out = (TextView) findViewById(R.id.out);
        submit.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          String ans = inp.getText().toString();
                                          ReadWrite.write(runQs.this.getFilesDir() + "/" + "pyCode", ans.replace("print", "pr").replace("input", "inp"));
                                          showCorrect();

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
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LessonActivity.j > 1) {
                    LessonActivity.j = LessonActivity.j - 1;
                    LessonActivity.shared_xp = LessonActivity.shared_xp - 1;
                    startActivity(new Intent(runQs.this, LessonActivity.class));
                    overridePendingTransition(0, 0);
                }
            }
        });
        runA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReadWrite.write(runQs.this.getFilesDir() + "/" + "pyCode", inp.getText().toString().replace("print", "pr").replace("input", "inp"));
                try {
                    Python py = Python.getInstance();
                    PyObject pyFile = py.getModule("compiler_2");
                    out.setText(pyFile.callAttr("main").toString().replace("|", System.getProperty("line.separator")));
                }catch (Exception e) {
                    Log.d("eroor", e.getLocalizedMessage());
                    out.setText(e.getLocalizedMessage());
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
        try {
            //Python.start(new AndroidPlatform(runQs.this));
            Python py = Python.getInstance();
            PyObject pyFile = py.getModule("compiler_2");
            out.setText(pyFile.callAttr("main").toString().replace("|", System.getProperty("line.separator")));
        }catch (Exception e) {
            Log.d("eroor", e.getLocalizedMessage());
            out.setText(e.getLocalizedMessage());
        }
        submit.setImageResource(R.drawable.avd_anim);

        animate();
        runA.setVisibility(View.VISIBLE);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setClickable(false);
                LessonActivity.j++;
                mainScreen.lessonWr++;
                LessonActivity.shared_xp = LessonActivity.shared_xp + 2;
                //LessonActivity.points++;
                startActivity(new Intent(runQs.this, LessonActivity.class));
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
