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
    WebView webView;

    private codeFramentQSfreeText CodeFramentQS1;
    private browserFragmentQSfreeText BrowserFragmentQS1;
    private TabLayout tabs;
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

        ViewPager viewPager = findViewById(R.id.view_pager2);
        tabs = (TabLayout) findViewById(R.id.tabs2);
        tabs.setupWithViewPager(viewPager);

        CodeFramentQS1 = new codeFramentQSfreeText();
        BrowserFragmentQS1 = new browserFragmentQSfreeText();

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);

        viewPagerAdapter.addFragment(CodeFramentQS1, "תכנות");
        viewPagerAdapter.addFragment(BrowserFragmentQS1, "תצוגה");

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
                        CodeFramentQS1.setText(LessonActivity.shared_hashmap.get("additional"));
                    }
                    if (!tree.loadAgain.equals("")){
                        CodeFramentQS1.setText(tree.loadAgain);
                        tree.loadAgain = "";
                    }
                } catch (Exception e){
                    new CountDownTimer(500, 10) {
                        public void onFinish() {
                            if (!LessonActivity.shared_hashmap.get("additional").equals("none")) {
                                CodeFramentQS1.setText(LessonActivity.shared_hashmap.get("additional"));
                            }
                            if (!tree.loadAgain.equals("")){
                                CodeFramentQS1.setText(tree.loadAgain);
                                tree.loadAgain = "";
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
                String ans = CodeFramentQS1.getText().toString();
                try {
                    if (Text.eqnova(ans, LessonActivity.shared_hashmap.get("Answer"), LessonActivity.shared_hashmap.get("additional").length())) {
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
        submit.setImageResource(R.drawable.avd_anim);
        animate();
        //webView.loadData(CodeFramentQS1.getText().toString(), "text/html", "UTF-8");
        codeFramentQSfreeText.getCode((FragmentActivity) FreeText.this);
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
}
