package com.getcodly.codly;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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

import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class runQs extends AppCompatActivity {
    TextView qs;
    ImageButton submit;
    ImageButton runA;
    RelativeLayout popupTruee;
    ImageButton backBtn;
    TextView out;
    String[] cc = new String[]{"type", "range", "dict", "int", "str", "float", "input"};
    String[] applesin = new String[]{"print", "for", "if", "while", "try", "except"};
    ImageButton continueBtn10;
    RelativeLayout popup11;
    ImageButton continueBtn11;
    MultiAutoCompleteTextView inp;
    boolean ifn;
    List<String> c;
    String[] language ={"print(", "range(", "for", "if", "while", "input(", "type(", "try", "except", "dict(", "str(", "float("};
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
        ifn = false;
        runA = (ImageButton) findViewById(R.id.runA);
        if (LessonActivity.shared_hashmap.get("type").equals("showpy")){
            runA.setVisibility(View.GONE);
        }
        submit = (ImageButton) findViewById(R.id.button);
        qs = (TextView) findViewById(R.id.textView);
        out = (TextView) findViewById(R.id.out);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (runQs.this,android.R.layout.select_dialog_item, language);
        Log.d("finished", "freetext");
        c = new ArrayList<String>();
        c.add(".");
        c.add(" ");
        c.add("=");
        c.add(">");
        c.add("<");
        c.add("\n");
        c.add("+");
        c.add("-");
        c.add("/");
        c.add("*");
        c.add("(");
        c.add(")");
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
        inp = (MultiAutoCompleteTextView) findViewById(R.id.inp);
        inp.setText(LessonActivity.shared_hashmap.get("additional"));
        if (LessonActivity.shared_hashmap.get("type").equals("showPy")){
            inp.setKeyListener(null);
        }
        textCPy();
        inp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int loc = inp.getSelectionStart();
                String htmlText = s.toString();
                Log.d(String.valueOf(loc), String.valueOf(loc));
                try {
                    Log.d("hihi", String.valueOf(htmlText.charAt(loc - 1)));
                    if (htmlText.charAt(loc - 1) == '\n' && htmlText.charAt(loc - 2) == ':'){
                        htmlText = htmlText + "   ";
                        ifn = true;
                    }
                    if (c.contains(String.valueOf(htmlText.charAt(loc - 1)))){
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
                                str1.setSpan(new ForegroundColorSpan(Color.rgb(255,140,0)), htmlText.indexOf(codeWord2, startt), htmlText.indexOf(codeWord2, startt) + codeWord2.length(), 0);
                                startt = htmlText.indexOf(codeWord2, startt) + codeWord2.length();
                            }
                        }
                        int adNunc = 1;
                        int altumAdNunc = 0;
                        for (String tText: htmlText.split("\"")){
                            if (adNunc % 2 == 0){
                                str1.setSpan(new ForegroundColorSpan(Color.rgb(125, 250, 111)), altumAdNunc, altumAdNunc + tText.length(), 0);
                            }
                            altumAdNunc = altumAdNunc + tText.length() + 1;
                            adNunc = adNunc + 1;
                        }
                        inp.setText(str1);
                        try {
                            if (ifn){
                                inp.setSelection(loc + 3);
                                ifn = false;
                            }
                            else {
                                inp.setSelection(loc);
                            }
                        } catch (Exception e) {
                            inp.setSelection(builder.length());
                        }
                    }
                }catch (Exception e){

                }
                inp.setThreshold(1);//will start working from first character
                inp.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
                inp.setTokenizer(new KcsMultiAutoCompleteTextView(' '));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
    public void textCPy() {
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

}
