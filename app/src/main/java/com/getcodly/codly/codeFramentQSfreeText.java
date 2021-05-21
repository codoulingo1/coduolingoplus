package com.getcodly.codly;

import android.graphics.Color;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class codeFramentQSfreeText extends Fragment  { //was extends Fragment, might need to change that

    public static String htmlCode;
    public static MultiAutoCompleteTextView htmlInp;
    String htmlText;
    List<String> c;
    String[] cc;
    String[] bb;
    ViewPager mViewPager;
    //public static TabLayout tabsHost;
    String blankTemplate = "<!doctype html>\n<html>\n    <head>\n        \n    </head>\n    \n    <body>\n        \n    </body>\n</html>";
    String[] language ={"body>", "html>", "head>", "/body>", "/head>", "/html>", "h1>", "h2>", "h3>", "h4>", "h5>", "h6>", "/h1>", "/h2>", "/h3>", "/h4>", "/h5>", "/h6>", "p>", "/p>", "br>", "hr>", "a href=", "/a>",
            "dl>", "dd>", "dt>", "/dl>", "src=", "b>", "/b>", "i>", "/i>", "tr>", "td>", "/tr>", "/td>", "font size=", "/font>", "table>", "/table>"};
    private AnimatedVectorDrawable animation;

    public codeFramentQSfreeText() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_code_frament_qs_free_text, container, false);

        c = new ArrayList<String>();
        String[] cc = new String[]{"i", "p", "b", "body", "html", "head", "h1", "h2", "h3", "h4", "h5", "h6", "br", "hr",
                "dl", "dd", "dt", "tr", "td", "table"};
        String[] bb = new String[]{"src", "font size", "href", "type href", "class", "id", "name", "rel", "doctype"};
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
                                if (htmlText.charAt(htmlText.indexOf(codeWord, startt) - 1) == '<' || htmlText.charAt(htmlText.indexOf(codeWord, startt) - 1) == '/') {
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
                                if (htmlText.charAt(htmlText.indexOf(codeWord2, startt) + codeWord2.length()) == '=') {
                                    str1.setSpan(new ForegroundColorSpan(Color.rgb(170, 109, 173)), htmlText.indexOf(codeWord2, startt), htmlText.indexOf(codeWord2, startt) + codeWord2.length(), 0);
                                    startt = htmlText.indexOf(codeWord2, startt) + codeWord2.length();
                                }
                                else {
                                    startt++;
                                }
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
        mViewPager = (ViewPager) v.findViewById(R.id.view_pager6000);
        //tabsHost = getActivity().findViewById(R.id.tabs6000);
        detectLanguage();
        if (selectProject.codeToLoad != null){
            String codeToLoad2 = selectProject.codeToLoad;
            codeToLoad2 = codeToLoad2.replace("\\n", System.getProperty("line.separator"));
            htmlInp.setText(codeToLoad2);
        } else {
            htmlInp.setText(blankTemplate);
        }

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
                htmlCode = htmlInp.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

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

    public static void getCode(FragmentActivity fa){
        htmlCode = htmlInp.getText().toString();
        openFragment(fa);
    }

    public static void openFragment(FragmentActivity fa) {
        browserFragmentQS BrowserFragmentQS1 = new browserFragmentQS();

        FragmentManager fragmentManager = fa.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.add(R.id.fragment_container, BrowserFragmentQS1, "FRAGMENT").commit();
        //tabsHost.getTabAt(1).select();
        //((iframe2) getActivity()).changeTab(2);

    }

    void detectLanguage(){
        String a = Locale.getDefault().getDisplayLanguage();
        Log.d("lan", a);
        if(a.equals("English")){
            String help1S = "";
        }
    }

    public static void setText(SpannableString text){
        htmlInp.setText(text);
    }
    public static String getText(){
        return htmlInp.getText().toString();
    }

}
