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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class pythonCode extends Fragment {
    public static String pythonCode = "";
    ImageButton submitBtn;
    boolean ifn;
    public static MultiAutoCompleteTextView htmlInp;
    List<String> c;
    String htmlText;
    Button help1;
    Button help2;
    Button help3;
    ViewPager mViewPager;
    TabLayout tabsHost;
    String[] cc = new String[]{"type", "range", "dict", "int", "str", "float"};
    String[] applesin = new String[]{"print", "for", "if", "while", "in", "try", "except"};
    String[] language ={"print(", "range(", "for", "if", "while", "in", "type(", "try", "except", "dict(", "str(", "float("};
    public static boolean run;

    private AnimatedVectorDrawable animation;

    public pythonCode() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_python_code, container, false);
        submitBtn = (ImageButton) v.findViewById(R.id.submitHTML);
        htmlInp = (MultiAutoCompleteTextView) v.findViewById(R.id.inputHTML);
        Arrays.sort(cc, (str1, str2) -> str1.length() - str2.length());
        Arrays.sort(applesin, (str1, str2) -> str1.length() - str2.length());
        ifn = false;
        htmlInp.setFocusableInTouchMode(true);
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
        htmlInp.requestFocus();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getContext(),android.R.layout.select_dialog_item, language);

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
                        htmlInp.setText(str1);
                        try {
                            if (ifn){
                                htmlInp.setSelection(loc + 3);
                                ifn = false;
                            }
                            else {
                                htmlInp.setSelection(loc);
                            }
                        } catch (Exception e) {
                            htmlInp.setSelection(builder.length());
                        }
                    }
                }catch (Exception e){

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
        if (selectProject.codeToLoad != null){
            String codeToLoad2 = selectProject.codeToLoad;
            codeToLoad2 = codeToLoad2.replace("\\n", System.getProperty("line.separator"));
            htmlInp.setText(codeToLoad2);
        }
        mViewPager = (ViewPager) v.findViewById(R.id.view_pager6000);
        tabsHost = getActivity().findViewById(R.id.tabs6000);
        detectLanguage();


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitBtn.setImageResource(R.drawable.avd_anim);
                animate();
                run = true;
                pythonCode = htmlInp.getText().toString();
                //openFragment();
            }
        });


        htmlInp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                submitBtn.setImageResource(R.drawable.next3);
                pythonCode = htmlInp.getText().toString();
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
                x = x.substring(0, htmlInp.getSelectionStart()) + "(" + x.substring(htmlInp.getSelectionStart(), x.length());
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
                x = x.substring(0, htmlInp.getSelectionStart()) + "=" + x.substring(htmlInp.getSelectionStart(), x.length());
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
                x = x.substring(0, htmlInp.getSelectionStart()) + ")" + x.substring(htmlInp.getSelectionStart(), x.length());
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
        return v;
    }

    private void openFragment() {
        pythonRun pythonRun = new pythonRun();

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.add(R.id.fragment_container, pythonRun, "FRAGMENT").commit();
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