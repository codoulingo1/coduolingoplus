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
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.CountDownTimer;
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
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FreeText extends AppCompatActivity {

    TextView qs;
    ImageButton submit;
    RelativeLayout popupTruee;
    ImageButton backBtn;
    ImageButton continueBtn10;
    RelativeLayout popup11;
    ImageButton continueBtn11;
    private AnimatedVectorDrawable animation;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    WebView webView;

    public static codeFramentQSfreeText CodeFramentQS1;
    private browserFragmentQSfreeText BrowserFragmentQS1;
    private TabLayout tabs;
    String ans;
    public static String htmlCodeParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_free_text);
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

        viewPager = findViewById(R.id.view_pager2);
        tabs = (TabLayout) findViewById(R.id.tabs2);
        tabs.setupWithViewPager(viewPager);

        CodeFramentQS1 = new codeFramentQSfreeText();
        //codeFramentQSfreeText.htmlCode =  CodeFramentQS1.getText().toString();
        BrowserFragmentQS1 = new browserFragmentQSfreeText();

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);

        viewPagerAdapter.addFragment(CodeFramentQS1, "תכנות");
        //viewPagerAdapter.addFragment(BrowserFragmentQS1, "תצוגה");

        viewPager.setAdapter(viewPagerAdapter);

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
        new CountDownTimer(50, 8) {
            public void onFinish() {
                try {
                    if (!LessonActivity.shared_hashmap.get("additional").equals("none")) {
                        CodeFramentQS1.setText(new SpannableString(LessonActivity.shared_hashmap.get("additional")));
                        textCHTML();
                    }
                    if (!tree.loadAgain.equals("")){
                        CodeFramentQS1.setText(new SpannableString(tree.loadAgain));
                        tree.loadAgain = "";
                        textCHTML();
                    }
                } catch (Exception e){
                    new CountDownTimer(500, 10) {
                        public void onFinish() {
                            if (!LessonActivity.shared_hashmap.get("additional").equals("none")) {
                                CodeFramentQS1.setText(new SpannableString(LessonActivity.shared_hashmap.get("additional")));
                                textCHTML();
                            }
                            if (!tree.loadAgain.equals("")){
                                CodeFramentQS1.setText(new SpannableString(tree.loadAgain));
                                tree.loadAgain = "";
                                textCHTML();
                            }
                        }

                        public void onTick(long millisUntilFinished) {

                        }
                    }.start();
                }
            }

            public void onTick(long millisUntilFinished) {

            }
        }.start();

        submit.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          ans = CodeFramentQS1.getText().toString();
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
                    startActivity(new Intent(FreeText.this, LessonActivity.class));
                    overridePendingTransition(0, 0);
                }
            }
        });


    }
    void showCorrect() {
        try {
            viewPagerAdapter.addFragment(BrowserFragmentQS1, "תצוגה");
            viewPager.setAdapter(viewPagerAdapter);
        } catch (Exception e){

        }
        submit.setImageResource(R.drawable.avd_anim);
        animate();
        //webView.loadData(CodeFramentQS1.getText().toString(), "text/html", "UTF-8");
        codeFramentQSfreeText.getCode((FragmentActivity) FreeText.this);
        codeFramentQSfreeText.htmlCode =  CodeFramentQS1.getText().toString();
        mainScreen.run = true;
        tabs.getTabAt(1).select();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setClickable(false);
                LessonActivity.j++;
                mainScreen.lessonWr++;
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
                mainScreen.lessonWr--;
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

    private class ViewPagerAdapter extends FragmentPagerAdapter
    {

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitle = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            fragmentTitle.add(title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }
    }
    public static void textCHTML(){
        String[] cc = new String[]{"i", "p", "b", "body", "html", "head", "h1", "h2", "h3", "h4", "h5", "h6", "br", "hr",
                "dl", "dd", "dt", "tr", "td", "table"};
        String[] bb = new String[]{"src", "font size", "href", "type href", "class", "id", "name", "rel", "doctype"};
        try {
            String htmlText = CodeFramentQS1.getText();
            SpannableStringBuilder builder = new SpannableStringBuilder();
            SpannableString str1 = new SpannableString(htmlText);
            for (String codeWord : cc) {
                int startt = 0;
                while (htmlText.indexOf(codeWord, startt) > -1) {
                    Log.d("hi", String.valueOf(htmlText.indexOf(codeWord, 3)));
                    if (htmlText.charAt(htmlText.indexOf(codeWord, startt) - 1) == '<' || htmlText.charAt(htmlText.indexOf(codeWord, startt) - 1) == '/'){
                        str1.setSpan(new ForegroundColorSpan(Color.rgb(53, 133, 228)), htmlText.indexOf(codeWord, startt), htmlText.indexOf(codeWord, startt) + codeWord.length(), 0);
                        startt = htmlText.indexOf(codeWord, startt) + codeWord.length();
                    }
                    else{
                        startt++;
                    }
                }
            }
            for (String codeWord2 : bb) {
                int startt = 0;
                while (htmlText.indexOf(codeWord2, startt) > -1) {
                    Log.d("hi", String.valueOf(htmlText.indexOf(codeWord2, 3)));
                    Log.d("saluton", String.valueOf(htmlText.charAt(htmlText.indexOf(codeWord2, startt) + codeWord2.length()) + "hallo"));
                    if (htmlText.charAt(htmlText.indexOf(codeWord2, startt) + codeWord2.length()) == '=') {
                        str1.setSpan(new ForegroundColorSpan(Color.rgb(170, 109, 173)), htmlText.indexOf(codeWord2, startt), htmlText.indexOf(codeWord2, startt) + codeWord2.length(), 0);
                        startt = htmlText.indexOf(codeWord2, startt) + codeWord2.length();
                    }
                    else{
                        startt++;
                    }
                }
            }
            int adNunc = 1;
            int altumAdNunc = 0;
            for (String tText : htmlText.split("\"")) {
                if (adNunc % 2 == 0) {
                    str1.setSpan(new ForegroundColorSpan(Color.rgb(125, 250, 111)), altumAdNunc, altumAdNunc + tText.length(), 0);
                }
                altumAdNunc = altumAdNunc + tText.length() + 1;
                adNunc = adNunc + 1;
            }
            CodeFramentQS1.setText(str1);
        }catch (Exception e){

        }
    }
}
