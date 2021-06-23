package com.getcodly.codly;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

public class browserFragmentQSfreeText extends Fragment {

    WebView htmlView;
    String code;
    public static codeFramentQSfreeText CodeFramentQS1;

    public browserFragmentQSfreeText() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_browser_free_text, container, false);
        CodeFramentQS1 = new codeFramentQSfreeText();
        code = codeFramentQSfreeText.htmlCode;
        Log.d("blankBrowser1", code);
        try {
            if (code.length() > 5){
                htmlView = (WebView) v.findViewById(R.id.HtmlView);
                htmlView.loadData(code, "text/html", "UTF-8");
                Log.d("blankBrowser2", code);
                //FreeText.run = false;
            }
        }
        catch (Exception e){

        }

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        htmlView.loadData(CodeFramentQS1.getText().toString(), "text/html", "UTF-8");
    }
}
