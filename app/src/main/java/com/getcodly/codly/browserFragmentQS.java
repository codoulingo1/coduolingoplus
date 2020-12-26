package com.getcodly.codly;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;

import java.util.Timer;
import java.util.TimerTask;

import androidx.fragment.app.Fragment;

public class browserFragmentQS extends Fragment {

    String last;
    String code;
    WebView htmlView;
    Button pyEnt;
    EditText pyInp;
    TextView out;

    public browserFragmentQS() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_browser, container, false);
        code = codeFramentQS.htmlCode;
        htmlView = (WebView) v.findViewById(R.id.HtmlView);
        if (NonFreedum.ifQsPython){
            try {
                ReadWrite.write(getContext().getFilesDir() + "/" + "pyCode", code.replace("print", "pr").replace("input()", "10"));
                //Python.start(new AndroidPlatform(runQs.this));
                Python py = Python.getInstance();
                PyObject pyFile = py.getModule("compiler_2");
                htmlView.loadData("<h3>" + pyFile.callAttr("main").toString().replace("|", System.getProperty("line.separator")) + "</h3>"
                        , "text/html", "UTF-8");
            }catch (Exception e) {
                Log.d("eroor", e.getLocalizedMessage());
                htmlView.loadData("<h3>" + e.getLocalizedMessage() + "</h3>"
                        , "text/html", "UTF-8");
            }
        }
        else {
            htmlView.loadData(code, "text/html", "UTF-8");
        }
        return v;
    }
}
