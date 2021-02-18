package com.getcodly.codly;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
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

public class NonFreedum extends AppCompatActivity {
    //TextView ans;
    String org;
    List<String> back_ch = new ArrayList<String>();
    List<Integer> back_b = new ArrayList<Integer>();
    public static List<Integer> start_w = new ArrayList<Integer>();
    public static List<Integer> end_W = new ArrayList<Integer>();
    String unuateksto;
    ImageButton backBtn;
    int wrloc;
    public String f;
    RelativeLayout popupTrue;
    RelativeLayout popupFalse;
    ImageButton continueBtnTrue;
    public static int w;
    public static boolean ifQsPython;
    ImageButton continueBtnFalse;
    Button opt1;
    ProgressBar pb;
    private ObjectAnimator progressAnimator;
    int b = 0;
    WebView htmlView;
    TextView qs;
    Button showAnswer;

    private codeFramentQS CodeFramentQS1;
    private browserFragmentQS BrowserFragmentQS1;
    private TabLayout tabs;
    public static String htmlCodeParent;

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
        ifQsPython = LessonActivity.shared_hashmap.get("type").equals("pynonfreetext");
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
        htmlView = findViewById(R.id.htmlView3);
        popupTrue = (RelativeLayout) findViewById(R.id.PopupTrue);
        pb.setProgress(LessonActivity.pr);
        start_w.clear();
        end_W.clear();

        ViewPager viewPager = findViewById(R.id.view_pager2);
        tabs = (TabLayout) findViewById(R.id.tabs2);
        tabs.setupWithViewPager(viewPager);

        CodeFramentQS1 = new codeFramentQS();
        BrowserFragmentQS1 = new browserFragmentQS();

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);

        viewPagerAdapter.addFragment(CodeFramentQS1, "תכנות");
        viewPagerAdapter.addFragment(BrowserFragmentQS1, "תצוגה");

        viewPager.setAdapter(viewPagerAdapter);
        b = 0;
        back_b.add(0);

        showAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupFalse.setVisibility(View.GONE);
                CodeFramentQS1.setText(LessonActivity.shared_hashmap.get("Answer"));
                if (ifQsPython) {
                    textCPy();
                } else {
                    textCHTML();
                }
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



        new CountDownTimer(50, 8) {
            public void onFinish() {
                try {
                    CodeFramentQS1.setText(f);
                    org = CodeFramentQS1.getText();
                    if (ifQsPython) {
                        textCPy();
                    } else {
                        textCHTML();
                    }
                } catch (Exception e){
                    new CountDownTimer(500, 10) {
                        public void onFinish() {
                            CodeFramentQS1.setText(f);
                            org = CodeFramentQS1.getText();
                            if (ifQsPython) {
                                textCPy();
                            } else {
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
                if (LessonActivity.shared_hashmap.get("Answer").equals(CodeFramentQS1.getText())){
                    pythonCode.run = true;
                    codeFramentQS.getCode((FragmentActivity) NonFreedum.this);
                    tabs.getTabAt(1).select();
                    showCorrect();

                } else{

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
        try {
            List<Integer> first_del = Text.betweenIndex(text, '£', 's');
            SpannableStringBuilder builder = new SpannableStringBuilder();
            SpannableString txtSpannable = new SpannableString(String.valueOf(ch_new_text));
            txtSpannable.setSpan(new ForegroundColorSpan(Color.rgb(0, 0, 120)), first_del.get(0), first_del.get(0) + add.length(), 0);
            builder.append(txtSpannable);
            CodeFramentQS1.setTextB(txtSpannable);//add the input
            end_W.add(first_del.get(0) + add.length() + b);
            start_w.add(first_del.get(0) + b);
            back_ch.add(new_text);//add the input
            //b = b + (add.length() - 1);
            back_b.add(b);
        } catch (Exception e){

        }
        if (ifQsPython) {
            textCPy();
        } else {
            textCHTML();
        }
    }
    public void back_choice() {
        try{
            if (back_ch.get(back_ch.size() - 2).length() < back_ch.get(back_ch.size() - 1).length() || Text.findstring("£", back_ch.get(back_ch.size() - 2))) {
                String new_text = back_ch.get(back_ch.size() - 2);
                b = back_b.get(back_b.size() - 2);
                List<Integer> del =  Text.betweenIndex(new_text, '£', 's');
                char[] ch_new_text = new_text.toCharArray();
                for (int d : del){
                    ch_new_text[d] = ' ';
                }
                CodeFramentQS1.setText(String.valueOf(ch_new_text));
                if (ifQsPython) {
                    textCPy();
                } else {
                    textCHTML();
                }


                back_ch.remove(back_ch.size() - 1);
                start_w.remove(start_w.size() - 1);
                end_W.remove(end_W.size() - 1);
                back_b.remove(back_b.size() - 1);
            }


            else {
                rese();
            }
        }catch (Exception e){
            rese();
        }
    }
    public void rese() {
        CodeFramentQS1.setText(f);
        start_w.clear();
        end_W.clear();
        back_ch.clear();
        b = 0;
        back_ch.add(unuateksto);
        back_b.add(0);
        if (ifQsPython) {
            textCPy();
        } else {
            textCHTML();
        }
    }
    void getLength(){
        //String btnText;
        //btnText = btn2.getText().toString();
        //btnText.length();

    }

    void showCorrect(){
        check.setImageResource(R.drawable.avd_anim);
        animate();
        //htmlView.loadData(CodeFramentQS1.getText().toString(), "text/html", "UTF-8");
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
    public static void textCHTML(){
        String[] cc = new String[]{"i", "p", "body", "html", "head", "h1", "h2", "h3", "h4", "h5", "h6", "br", "hr",
                "dl", "dd", "dt", "tr", "td", "table"};
        String[] bb = new String[]{"src", "font size", "href", "type href", "class", "id", "id", "name", "rel", "doctype"};
        try {
            String htmlText = codeFramentQS.getText();
            SpannableStringBuilder builder = new SpannableStringBuilder();
            SpannableString str1 = new SpannableString(htmlText);
            for (String codeWord : cc) {
                int startt = 0;
                while (htmlText.indexOf(codeWord, startt) > -1) {
                    Log.d("hi", String.valueOf(htmlText.indexOf(codeWord, 3)));
                    str1.setSpan(new ForegroundColorSpan(Color.rgb(53, 133, 228)), htmlText.indexOf(codeWord, startt), htmlText.indexOf(codeWord, startt) + codeWord.length(), 0);
                    startt = htmlText.indexOf(codeWord, startt) + codeWord.length();
                }
            }
            for (String codeWord2 : bb) {
                int startt = 0;
                while (htmlText.indexOf(codeWord2, startt) > -1) {
                    Log.d("hi", String.valueOf(htmlText.indexOf(codeWord2, 3)));
                    str1.setSpan(new ForegroundColorSpan(Color.rgb(170, 109, 173)), htmlText.indexOf(codeWord2, startt), htmlText.indexOf(codeWord2, startt) + codeWord2.length(), 0);
                    startt = htmlText.indexOf(codeWord2, startt) + codeWord2.length();
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
            for (int i = 0; i < NonFreedum.start_w.size(); i++){
                Log.d(String.valueOf(start_w.get(i)), String.valueOf(end_W.get(i)));
                str1.setSpan(new ForegroundColorSpan(Color.rgb(0, 0, 120)), start_w.get(i), end_W.get(i), 0);
            }
            codeFramentQS.setTextB(str1);
        }catch (Exception e){

        }
    }
    public static void textCPy() {
        String[] cc = new String[]{"type", "range", "dict", "int", "str", "float", "input"};
        String[] applesin = new String[]{"print", "for", "if", "while", "try", "except"};
        String htmlText = codeFramentQS.getText();
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
                int adNunc = 1;
                int altumAdNunc = 0;
            for (int i = 0; i < NonFreedum.start_w.size(); i++){
                str1.setSpan(new ForegroundColorSpan(Color.rgb(0, 0, 120)), start_w.get(i), end_W.get(i), 0);
            }
                for (String tText : htmlText.split("\"")) {
                    if (adNunc % 2 == 0) {
                        str1.setSpan(new ForegroundColorSpan(Color.rgb(125, 250, 111)), altumAdNunc, altumAdNunc + tText.length(), 0);
                    }
                    altumAdNunc = altumAdNunc + tText.length() + 1;
                    adNunc = adNunc + 1;
                }
                codeFramentQS.setTextB(str1);
        } catch (Exception e) {

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
