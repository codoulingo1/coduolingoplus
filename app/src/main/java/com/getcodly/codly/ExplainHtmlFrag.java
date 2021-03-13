package com.getcodly.codly;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;


public class ExplainHtmlFrag extends Fragment {

    public ExplainHtmlFrag() {
        // Required empty public constructor
    }

    int add_num = 0;
    ImageButton backBtn;
    ImageButton continueBtn;
    WebView web;
    TextView text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        text = findViewById(R.id.qsText);
        continueBtn = findViewById(R.id.continueBtn42);
        web = findViewById(R.id.web);
        HashMap<String, String> explanation_hashmap = LessonActivity.shared_hashmap;
        TextView fr = (TextView)findViewById(R.id.Explanation);
        SpannableStringBuilder builder=new SpannableStringBuilder();
        for (int i = 0; i < 10; i = i + 1) {
            try {
                if (i % 2 == 0 || i==0) {
                    builder.append(explanation_hashmap.get("qs").split("\\*")[i]);
                }
                else{
                    SpannableString txtSpannable = new SpannableString(explanation_hashmap.get("qs").split("\\*")[i]);
                    StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
                    txtSpannable.setSpan(boldSpan, 0, explanation_hashmap.get("qs").split("\\*")[i].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    builder.append(txtSpannable);
                }
            }catch (Exception e){
                break;
            }
        }
        text.setText(builder);
        WebSettings webSettings = web.getSettings();
        web.loadData(LessonActivity.shared_hashmap.get("additional"), "text/html", "UTF-8");
        backBtn = (ImageButton) findViewById(R.id.backBtn);
        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setProgress(LessonActivity.pr);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LessonActivity.shared_xp = LessonActivity.shared_xp + 1;
                //LessonActivity.points++;
                if(LessonActivity.shared_hashmap.get("additional").equals("none")) {
                    continueBtn.setClickable(false);
                    mainScreen.lessonWr++;
                    LessonActivity.j = LessonActivity.j + 1;
                    startActivity(new Intent(ExplainHtml.this, LessonActivity.class));
                    overridePendingTransition(0, 0);
                }
                else{
                    try {
                        String check = explanation_hashmap.get("additional").split("~")[add_num];
                    } catch (Exception e){
                        continueBtn.setClickable(false);
                        mainScreen.lessonWr++;
                        LessonActivity.j = LessonActivity.j + 1;
                        startActivity(new Intent(ExplainHtml.this, LessonActivity.class));
                        overridePendingTransition(0, 0);
                    }
                    builder.append(System.getProperty("line.separator"));
                    for (int i = 0; i < 10; i = i + 1) {//savta
                        try {
                            if (i % 2 == 0 || i==0) {
                                builder.append(explanation_hashmap.get("additional").split("~")[add_num].split("\\*")[i]);
                            }
                            else{
                                SpannableString txtSpannable = new SpannableString(explanation_hashmap.get("additional").split("~")[add_num].split("\\*")[i]);
                                StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
                                txtSpannable.setSpan(boldSpan, 0, explanation_hashmap.get("additional").split("~")[add_num].split("\\*")[i].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                builder.append(txtSpannable);
                            }
                        }catch (Exception e){
                            break;
                        }
                    }
                    add_num = add_num + 1;
                    fr.setText(builder);
                }
                //continueBtn.setImageResource(R.drawable.avd_anim);
                //animate();

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (LessonActivity.j > 1) {
                    LessonActivity.j = LessonActivity.j - 1;
                    LessonActivity.shared_xp = LessonActivity.shared_xp - 1;
                    startActivity(new Intent(ExplainHtml.this, LessonActivity.class));
                    overridePendingTransition(0, 0);
                }
            }
        });

        return inflater.inflate(R.layout.fragment_explain_html, container, false);
    }
}
