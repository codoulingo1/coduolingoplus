package com.getcodly.codly;

import android.graphics.Color;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.text.Editable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.getcodly.codly.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class codeFrament extends Fragment  { //was extends Fragment, might need to change that

    public static String htmlCode;
    ImageButton submitBtn;
    public static MultiAutoCompleteTextView htmlInp;
    Button help1;
    Button help2;
    String htmlText;
    List<String> c;
    Button help3;
    String[] cc;
    String[] bb;
    ViewPager mViewPager;
    TabLayout tabsHost;
    String blankTemplate = "<!doctype html>\n<html>\n    <head>\n        \n    </head>\n    \n    <body>\n        \n    </body>\n</html>";
    String[] language ={"body>", "html>", "head>", "/body>", "/head>", "/html>", "h1>", "h2>", "h3>", "h4>", "h5>", "h6>", "/h1>", "/h2>", "/h3>", "/h4>", "/h5>", "/h6>", "p>", "/p>", "br>", "hr>", "a href=", "/a>",
            "dl>", "dd>", "dt>", "/dl>", "src=", "b>", "/b>", "i>", "/i>", "tr>", "td>", "/tr>", "/td>", "font size=", "/font>", "table>", "/table>"};
    private AnimatedVectorDrawable animation;

    public codeFrament() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_code_frament, container, false);
        c = new ArrayList<String>();
        cc = new String[]{"body", "html", "head", "h1", "h2", "h3", "h4", "h5", "h6", "br", "hr",
                "dl", "dd", "dt", "tr", "td", "table"};
        bb = new String[]{"src", "font size", "href", "type href", "class", "id", "id", "name", "rel"};
        Arrays.sort(cc, (str1, str2) -> str1.length() - str2.length());
        c.add(".");
        c.add(" ");
        c.add("=");
        c.add(">");
        c.add("<");
        c.add("\n");
        c.add("+");
        c.add("-");
        c.add("*");
        c.add("(");
        c.add(")");
        submitBtn = (ImageButton) v.findViewById(R.id.submitHTML);
        htmlInp = (MultiAutoCompleteTextView) v.findViewById(R.id.inputHTML);
        htmlInp.setFocusableInTouchMode(true);
        htmlInp.requestFocus();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getContext(),android.R.layout.select_dialog_item, language);
        //Getting the instance of AutoCompleteTextView
        //htmlInp.setThreshold(1);//will start working from first character
        //htmlInp.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        htmlInp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int loc = htmlInp.getSelectionStart();
                htmlText = s.toString();
                Log.d(String.valueOf(loc), String.valueOf(loc));
                try {
                    Log.d("hihi", String.valueOf(htmlText.charAt(loc - 1)));
                    if (c.contains(String.valueOf(htmlText.charAt(loc - 1)))){
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
                        for (String tText: htmlText.split("\"")){
                            if (adNunc % 2 == 0){
                                str1.setSpan(new ForegroundColorSpan(Color.rgb(125, 250, 111)), altumAdNunc, altumAdNunc + tText.length(), 0);
                            }
                            altumAdNunc = altumAdNunc + tText.length() + 1;
                            adNunc = adNunc + 1;
                        }
                        htmlInp.setText(str1);
                        try {
                                htmlInp.setSelection(loc);
                        } catch (Exception e) {
                            htmlInp.setSelection(builder.length());
                        }
                    }
                }catch (Exception e){
                    Log.d("err", e.getLocalizedMessage());
                }
                htmlInp.setThreshold(1);//will start working from first character
                htmlInp.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
                htmlInp.setTokenizer(new KcsMultiAutoCompleteTextView(' '));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        help1 = (Button) v.findViewById(R.id.help1);
        help2 = (Button) v.findViewById(R.id.help2);
        help3 = (Button) v.findViewById(R.id.help3);
        mViewPager = (ViewPager) v.findViewById(R.id.view_pager6000);
        tabsHost = getActivity().findViewById(R.id.tabs6000);
        detectLanguage();
        if (selectProject.codeToLoad != null){
            String codeToLoad2 = selectProject.codeToLoad;
            codeToLoad2 = codeToLoad2.replace("\\n", System.getProperty("line.separator"));
            htmlInp.setText(codeToLoad2);
        } else {
            htmlInp.setText(blankTemplate);
        }
        try {
            htmlCode = htmlInp.getText().toString();
        } catch (Exception e){

        }

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitBtn.setImageResource(R.drawable.avd_anim);
                animate();
                htmlCode = htmlInp.getText().toString();
                openFragment();
            }
        });

        htmlInp.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press

                    return true;
                }
                return false;
            }
        });

        htmlInp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                submitBtn.setImageResource(R.drawable.next3);
                htmlCode = htmlInp.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        help1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String x = htmlInp.getText().toString();
                int loc = htmlInp.getSelectionStart();
                x = x.substring(0, htmlInp.getSelectionStart()) + ">" + x.substring(htmlInp.getSelectionStart(), x.length());
                Log.d("hi", String.valueOf(htmlInp.getSelectionStart()+1));
                htmlText = x;
                Log.d(String.valueOf(loc), String.valueOf(loc));
                try {
                    Log.d("hihi", String.valueOf(htmlText.charAt(loc - 1)));
                    String text = x;
                    SpannableStringBuilder builder = new SpannableStringBuilder();
                    SpannableString str1 = new SpannableString(text);
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
                    for (String tText: htmlText.split("\"")){
                        if (adNunc % 2 == 0){
                            str1.setSpan(new ForegroundColorSpan(Color.rgb(125, 250, 111)), altumAdNunc, altumAdNunc + tText.length(), 0);
                        }
                        altumAdNunc = altumAdNunc + tText.length() + 1;
                        adNunc = adNunc + 1;
                    }
                    htmlInp.setText(str1);
                    try {
                        htmlInp.setSelection(loc + 1);
                    } catch (Exception e) {
                        htmlInp.setSelection(builder.length());
                    }
                }catch (Exception e) {
                    Log.d("e.getLocalizedMessage()", e.getLocalizedMessage());
                }
            }
        });
        help2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String x = htmlInp.getText().toString();
                int loc = htmlInp.getSelectionStart();
                x = x.substring(0, htmlInp.getSelectionStart()) + "<" + x.substring(htmlInp.getSelectionStart(), x.length());
                Log.d("hi", String.valueOf(htmlInp.getSelectionStart()+1));
                htmlText = x;
                Log.d(String.valueOf(loc), String.valueOf(loc));
                try {
                    Log.d("hihi", String.valueOf(htmlText.charAt(loc - 1)));
                    String text = x;
                    SpannableStringBuilder builder = new SpannableStringBuilder();
                    SpannableString str1 = new SpannableString(text);
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
                    for (String tText: htmlText.split("\"")){
                        if (adNunc % 2 == 0){
                            str1.setSpan(new ForegroundColorSpan(Color.rgb(125, 250, 111)), altumAdNunc, altumAdNunc + tText.length(), 0);
                        }
                        altumAdNunc = altumAdNunc + tText.length() + 1;
                        adNunc = adNunc + 1;
                    }
                    htmlInp.setText(str1);
                    try {
                        htmlInp.setSelection(loc + 1);
                    } catch (Exception e) {
                        htmlInp.setSelection(builder.length());
                    }
                }catch (Exception e) {
                    Log.d("e.getLocalizedMessage()", e.getLocalizedMessage());
                }
            }
        });
        help3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String x = htmlInp.getText().toString();
                int loc = htmlInp.getSelectionStart();
                x = x.substring(0, htmlInp.getSelectionStart()) + "/" + x.substring(htmlInp.getSelectionStart(), x.length());
                Log.d("hi", String.valueOf(htmlInp.getSelectionStart()+1));
                htmlText = x;
                Log.d(String.valueOf(loc), String.valueOf(loc));
                try {
                    Log.d("hihi", String.valueOf(htmlText.charAt(loc - 1)));
                    String text = x;
                    SpannableStringBuilder builder = new SpannableStringBuilder();
                    SpannableString str1 = new SpannableString(text);

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
                    for (String tText: htmlText.split("\"")){
                        if (adNunc % 2 == 0){
                            str1.setSpan(new ForegroundColorSpan(Color.rgb(125, 250, 111)), altumAdNunc, altumAdNunc + tText.length(), 0);
                        }
                        altumAdNunc = altumAdNunc + tText.length() + 1;
                        adNunc = adNunc + 1;
                    }
                    htmlInp.setText(str1);
                    try {
                        htmlInp.setSelection(loc + 1);
                    } catch (Exception e) {
                        htmlInp.setSelection(builder.length());
                    }
                }catch (Exception e) {
                    Log.d("e.getLocalizedMessage()", e.getLocalizedMessage());
                }
            }
        });
        final Handler handler = new Handler();
        final int delay = 3000; //milliseconds

        /*handler.postDelayed(new Runnable(){
            public void run(){
                String[] c = new String[]{"hello", "hi"};
                String text = htmlInp.getText().toString();
                SpannableStringBuilder builder = new SpannableStringBuilder();
                Arrays.sort(c, (str1, str2) -> str1.length() - str2.length());
                SpannableString str1= new SpannableString(text);
                for(String codeWord : c){
                    int start = 0;
                    while (text.indexOf(codeWord, start)>-1) {
                        Log.d("hi", String.valueOf(text.indexOf(codeWord, 3)));
                        str1.setSpan(new ForegroundColorSpan(Color.GREEN), text.indexOf(codeWord, start), text.indexOf(codeWord, start) + codeWord.length(), 0);
                        start = text.indexOf(codeWord, start) + codeWord.length();
                    }
                }
                int loc = htmlInp.getSelectionStart();
                htmlInp.setText(str1);
                try {
                    htmlInp.setSelection(loc);
                } catch (Exception e){
                    htmlInp.setSelection(builder.length());
                }
                handler.postDelayed(this, delay);
            }
        }, delay);*/
        return v;
    }

    private void openFragment() {
        browserFragment BrowserFragment = new browserFragment();

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.add(R.id.fragment_container, BrowserFragment, "FRAGMENT").commit();
        tabsHost.getTabAt(1).select();
        //((iframe2) getActivity()).changeTab(2);

    }

    public void animate(){
        Drawable d = submitBtn.getDrawable();
        if (d instanceof AnimatedVectorDrawable) {

            Log.d("testanim", "onCreate: instancefound" + d.toString());
            animation = (AnimatedVectorDrawable) d;
            animation.start();
        }
    }
    void detectLanguage(){
        String a = Locale.getDefault().getDisplayLanguage();
        Log.d("lan", a);
        if(a.equals("English")){
            String help1S = "";
        }
    }

}
