package com.getcodly.codly;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ExplainationQS extends AppCompatActivity {
    public static ImageView ExpImage;
    ImageButton continueBtn;
    private AnimatedVectorDrawable animation;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explaination_q_s);
        continueBtn = (ImageButton) findViewById(R.id.continueBtn);
        ExpImage = (ImageView) findViewById(R.id.imageView);
        String url = LessonActivity.shared_hashmap.get("Image"); //paste here google drive picture shareable link but change "open?" to "uc?"
        if(!url.equals("none")){
            Picasso.with(this).load(url).resizeDimen(R.dimen.image_size, R.dimen.image_size).placeholder(R.drawable.goj).into(ExpImage);
        }
        Log.d("url", url);
        explanationQs();
    }


    public void explanationQs() {
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
        fr.setText(builder);
        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setProgress(LessonActivity.pr);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LessonActivity.j++;
                LessonActivity.shared_xp = LessonActivity.shared_xp + 1;
                //LessonActivity.points++;
                if(LessonActivity.shared_hashmap.get("additional").equals("none")) {
                    startActivity(new Intent(ExplainationQS.this, LessonActivity.class));
                    overridePendingTransition(0, 0);
                }
                else{
                    builder.append(System.getProperty("line.separator"));
                    builder.append((LessonActivity.shared_hashmap.get("additional")));
                    fr.setText(builder);
                    LessonActivity.shared_hashmap.put("additional", "none");
                }
                //continueBtn.setImageResource(R.drawable.avd_anim);
                //animate();

            }
        });
    }

    @Override
    public void onBackPressed() {
        DialogBack dialogBack = new DialogBack();
        dialogBack.show(getSupportFragmentManager(), "Example Dialog");
    }

    void AnimateProgress(){
        ObjectAnimator.ofInt(pb, "progress", LessonActivity.pr, 100)
                .setDuration(300)
                .start();
    }
    /*public void animate(){
        Drawable d = continueBtn.getDrawable();
        if (d instanceof AnimatedVectorDrawable) {

            Log.d("testanim", "onCreate: instancefound" + d.toString());
            animation = (AnimatedVectorDrawable) d;
            animation.start();
        }
    }*/
}
