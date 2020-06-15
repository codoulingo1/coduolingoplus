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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

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
    public static EditText htmlInp;
    Button help1;
    Button help2;
    Button help3;
    ViewPager mViewPager;
    TabLayout tabsHost;

    private AnimatedVectorDrawable animation;

    public codeFrament() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_code_frament, container, false);
        submitBtn = (ImageButton) v.findViewById(R.id.submitHTML);
        htmlInp = (EditText) v.findViewById(R.id.inputHTML);
        help1 = (Button) v.findViewById(R.id.help1);
        help2 = (Button) v.findViewById(R.id.help2);
        help3 = (Button) v.findViewById(R.id.help3);
        mViewPager = (ViewPager) v.findViewById(R.id.view_pager6000);
        tabsHost = getActivity().findViewById(R.id.tabs6000);
        detectLanguage();


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitBtn.setImageResource(R.drawable.avd_anim);
                animate();
                htmlCode = htmlInp.getText().toString();
                openFragment();
            }
        });

        /*help1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String finalCode;
                int CurrentSelection = htmlInp.getSelectionStart();
                int endSelection = htmlInp.length();
                String code = htmlInp.getText().toString();
                String newCode1 = code.substring(0, CurrentSelection) + "<";
                try {
                    String newCode2 = code.substring(CurrentSelection + 1, endSelection);
                    finalCode = newCode1 + newCode2;
                }catch (Exception e){
                    finalCode = newCode1;
                }
                htmlInp.setText(finalCode);
                htmlInp.setSelection(htmlInp.getSelectionStart() + 1); //Moves the cursor to the correct place
            }
        });
        help2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String finalCode;
                int CurrentSelection = htmlInp.getSelectionStart();
                int endSelection = htmlInp.length();
                String code = htmlInp.getText().toString();
                String newCode1 = code.substring(0, CurrentSelection) + ">";
                try {
                    String newCode2 = code.substring(CurrentSelection + 1, endSelection);
                    finalCode = newCode1 + newCode2;
                }catch (Exception e){
                    finalCode = newCode1;
                }
                htmlInp.setText(finalCode);
                htmlInp.setSelection(htmlInp.getSelectionStart() + 1); //Moves the cursor to the correct place
            }
        });

        help3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String finalCode;
                int CurrentSelection = htmlInp.getSelectionStart();
                int endSelection = htmlInp.length();
                String code = htmlInp.getText().toString();
                String newCode1 = code.substring(0, CurrentSelection) + "/";
                try {
                    String newCode2 = code.substring(CurrentSelection + 1, endSelection);
                    finalCode = newCode1 + newCode2;
                }catch (Exception e){
                    finalCode = newCode1;
                }
                htmlInp.setText(finalCode);
                htmlInp.setSelection(htmlInp.getSelectionStart() + 1); //Moves the cursor to the correct place
            }
        });*/
        htmlInp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                submitBtn.setImageResource(R.drawable.next3);
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
                htmlInp.setText(x);
                htmlInp.setSelection(loc + 1);
            }
        });
        help2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String x = htmlInp.getText().toString();
                int loc = htmlInp.getSelectionStart();
                x = x.substring(0, htmlInp.getSelectionStart()) + "<" + x.substring(htmlInp.getSelectionStart(), x.length());
                Log.d("hi", String.valueOf(htmlInp.getSelectionStart()+1));
                htmlInp.setText(x);
                htmlInp.setSelection(loc + 1);
            }
        });
        help3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String x = htmlInp.getText().toString();
                int loc = htmlInp.getSelectionStart();
                x = x.substring(0, htmlInp.getSelectionStart()) + "/" + x.substring(htmlInp.getSelectionStart(), x.length());
                Log.d("hi", String.valueOf(htmlInp.getSelectionStart()+1));
                htmlInp.setText(x);
                htmlInp.setSelection(loc + 1);
            }
        });
        final Handler handler = new Handler();
        final int delay = 3000; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){
                String[] c = new String[]{"hi", "hello"};
                String text = htmlInp.getText().toString();
                SpannableStringBuilder builder = new SpannableStringBuilder();
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
        }, delay);
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
