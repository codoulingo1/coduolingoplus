package com.getcodly.codly;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

public class browserFragment extends Fragment {

    WebView htmlView;
    String code;

    public browserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_browser, container, false);

        code = codeFrament.htmlCode;
        htmlView = (WebView) v.findViewById(R.id.HtmlView);
        htmlView.loadData(code, "text/html", "UTF-8");

        return v;
    }
}
