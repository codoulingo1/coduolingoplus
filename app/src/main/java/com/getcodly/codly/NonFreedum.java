package com.getcodly.codly;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.animation.ObjectAnimator;
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
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static com.getcodly.codly.codeFramentQS.setText;

public class NonFreedum extends AppCompatActivity {
    //TextView ans;
    public static String input;
    String org;
    List<String> back_ch = new ArrayList<String>();
    String unuateksto;
    ImageButton backBtn;
    int wrloc;
    public String f;
    RelativeLayout popupTrue;
    RelativeLayout popupFalse;
    ImageButton continueBtnTrue;
    public static int w;
    ImageButton continueBtnFalse;
    Button opt1;
    ProgressBar pb;
    private ObjectAnimator progressAnimator;
    //WebView htmlView;
    TextView qs;
    Button showAnswer;

    private codeFramentQS codeFramentQS1;
    private browserFragmentQS browserFragmentQS1;
    private TabLayout tabs;

    private AnimatedVectorDrawable animation;

    ImageButton check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_freedum);
        opt1 = (Button) findViewById(R.id.button1);
        final Button opt2 = (Button) findViewById(R.id.button2);
        final Button opt3 = (Button) findViewById(R.id.button3);
        final Button opt4 = (Button) findViewById(R.id.button4);
        final Button opt5 = (Button) findViewById(R.id.button5);
        showAnswer = findViewById(R.id.showAns);
        final TextView wt = (TextView) findViewById(R.id.textView6);
        final Button opt6 = (Button) findViewById(R.id.button6);
        backBtn = (ImageButton) findViewById(R.id.backBtn3);
        check = (ImageButton) findViewById(R.id.check);
        continueBtnFalse = (ImageButton) findViewById(R.id.continueBtnFalse);
        continueBtnTrue = (ImageButton) findViewById(R.id.continueBtnTrue);
        qs = (TextView) findViewById(R.id.textView2);
        pb = (ProgressBar) findViewById(R.id.progressBar);
        popupFalse = (RelativeLayout) findViewById(R.id.popupFalse);
        //ans = findViewById(R.id.textView3);
        //htmlView = findViewById(R.id.htmlView3);
        popupTrue = (RelativeLayout) findViewById(R.id.PopupTrue);
        pb.setProgress(LessonActivity.pr);

        codeFramentQS1 = new codeFramentQS();
        browserFragmentQS1 = new browserFragmentQS();

        ViewPager viewPager = findViewById(R.id.viewPagerQS);
        tabs = (TabLayout) findViewById(R.id.tabs1);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);

        viewPagerAdapter.addFragment(codeFramentQS1, "תכנות");
        viewPagerAdapter.addFragment(browserFragmentQS1, "תצוגה");

        viewPager.setAdapter(viewPagerAdapter);

        showAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupFalse.setVisibility(View.GONE);
                //ans.setText(LessonActivity.shared_hashmap.get("Answer"));
            }
        });
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
        String[] optAns = LessonActivity.shared_hashmap.get("Content").split(",");
        unuateksto = LessonActivity.shared_hashmap.get("additional");
        final Button dlt = (Button) findViewById(R.id.dlt);
        final Button dltall = (Button) findViewById(R.id.dltall);
        Button[] ops = {opt1, opt2, opt3, opt4, opt5, opt6};
        unuateksto = unuateksto.replaceAll("_", "%1$s");
        unuateksto = Text.replace(unuateksto, '%', '£', 0);

        back_ch.add(unuateksto);
        int opnum = 0;
        for (Button op : ops){
            try{
                op.setText(optAns[opnum]);
                opnum++;
            }
            catch (Exception e) {
                op.setVisibility(View.INVISIBLE);
            }
        }
        List<Integer> first_del =  Text.betweenIndex(unuateksto, '£', 's');
        char[] ch_new_text = unuateksto.toCharArray();
        for (int d : first_del){
            ch_new_text[d] = ' ';
        }
        f=String.valueOf(ch_new_text);
        codeFramentQS.setText(f);
        org = codeFramentQS.getText();
        opt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when play is clicked show stop button and hide play button
                choice(opt1);

            }
        });
        opt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when play is clicked show stop button and hide play button
                choice(opt2);
            }
        });
        opt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when play is clicked show stop button and hide play button
                choice(opt3);
            }
        });
        opt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when play is clicked show stop button and hide play button
                choice(opt4);

            }
        });
        opt5.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View v) {
            //when play is clicked show stop button and hide play button
            choice(opt5);

        }
    });
        opt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when play is clicked show stop button and hide play button
                choice(opt6);

            }
        });
        dlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when play is clicked show stop button and hide play button
                back_choice();

            }
        });
        dltall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when play is clicked show stop button and hide play button
                rese();

            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LessonActivity.j > 1) {
                    LessonActivity.j = LessonActivity.j - 1;
                    LessonActivity.shared_xp = LessonActivity.shared_xp - 1;
                    startActivity(new Intent(NonFreedum.this, LessonActivity.class));
                    overridePendingTransition(0, 0);
                }
            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when play is clicked show stop button and hide play button
                if (LessonActivity.shared_hashmap.get("Answer").equals(codeFramentQS.getText().toString())){
                    /*buttonl.setVisibility(View.VISIBLE);
                    buttonl.setText("המשך");
                    buttonl.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            LessonActivity.j++;
                            startActivity(new Intent(NonFreedum.this, LessonActivity.class));
                        }});*/
                    showCorrect();

                } else{
                    /*buttonl.setVisibility(View.VISIBLE);
                    buttonl.setText("נסה שוב");
                    buttonl.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(NonFreedum.this, NonFreedum.class));
                        }
                    });*/
                    NonFreedum.w++;
                    if (NonFreedum.w > 1){

                    }
                    showWrong();
                }

            }
        });
        int opt1txt = opt1.getText().toString().length();
        int opt2txt = opt2.getText().toString().length();
        int opt3txt = opt3.getText().toString().length();
        int opt4txt = opt4.getText().toString().length();
        int opt5txt = opt5.getText().toString().length();
        int opt6txt = opt6.getText().toString().length();
        

        opt1.getLayoutParams().width = opt1txt * 25 + 100;
        opt2.getLayoutParams().width = opt2txt * 25 + 100;
        opt3.getLayoutParams().width = opt3txt * 25 + 100;
        opt4.getLayoutParams().width = opt4txt * 25 + 100;
        opt5.getLayoutParams().width = opt5txt * 25 + 100;
        opt6.getLayoutParams().width = opt6txt * 25 + 100;

    }
    public void choice(Button opt){
        String text;

            try {
            if (!back_ch.get(back_ch.size() - 1).equals(unuateksto))
            {
                text = back_ch.get(back_ch.size() - 1);
            }else{
                text = unuateksto;

            }

        }
        catch (Exception e){
            text = unuateksto;
        }
        String add = opt.getText().toString();
        String new_text = String.format(Text.replace(Text.replace(text, '£', '%', 0), '%', '£', 1), add);
        List<Integer> del =  Text.betweenIndex(new_text, '£', 's');
        char[] ch_new_text = new_text.toCharArray();
        for (int d : del){
            ch_new_text[d] = ' ';
        }
        codeFramentQS.setText(String.valueOf(ch_new_text));//add the input
        back_ch.add(new_text);//add the input
    }
    public void back_choice() {
        try{
        if (back_ch.get(back_ch.size() - 2).length() < back_ch.get(back_ch.size() - 1).length() || Text.findstring("£", back_ch.get(back_ch.size() - 2))) {
            String new_text = back_ch.get(back_ch.size() - 2);
            List<Integer> del =  Text.betweenIndex(new_text, '£', 's');
            char[] ch_new_text = new_text.toCharArray();
            for (int d : del){
                ch_new_text[d] = ' ';
            }
            codeFramentQS.setText(String.valueOf(ch_new_text));
            back_ch.remove(back_ch.size() - 1);
        }


        else {
            rese();
        }
        }catch (Exception e){
            rese();
        }
    }
    public void rese() {
        codeFramentQS.setText(f);
        back_ch.clear();
        back_ch.add(unuateksto);
    }
    void getLength(){
        //String btnText;
        //btnText = btn2.getText().toString();
        //btnText.length();

    }

    void showCorrect(){
        check.setImageResource(R.drawable.avd_anim);
        animate();
        //htmlView.loadData(ans.getText().toString(), "text/html", "UTF-8");
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check.setClickable(false);
                LessonActivity.j++;
                mainScreen.lessonWr++;
                LessonActivity.shared_xp = LessonActivity.shared_xp + 1.5;
                //LessonActivity.points++;
                startActivity(new Intent(NonFreedum.this, LessonActivity.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }
    void showWrong(){
        popupFalse.setVisibility(View.VISIBLE);
        continueBtnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainScreen.lessonWr--;
                if(LessonActivity.shared_xp>=11){
                    LessonActivity.shared_xp = LessonActivity.shared_xp - 1;
                }
                startActivity(new Intent(NonFreedum.this, NonFreedum.class));

            }
        });
    }
    @Override
    public void onBackPressed() {
        DialogBack dialogBack = new DialogBack();
        dialogBack.show(getSupportFragmentManager(), "Example Dialog");
    }

    public void animate(){
        Drawable d = check.getDrawable();
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


