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

    public browserFragmentQSfreeText() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_browser, container, false);

        code = codeFramentQSfreeText.htmlCode;
        Log.d("blankBrowser", code);
        htmlView = (WebView) v.findViewById(R.id.HtmlView);
        htmlView.loadData(code, "text/html", "UTF-8");

        return v;
    }
}
