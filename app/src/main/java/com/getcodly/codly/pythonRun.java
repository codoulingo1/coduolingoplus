package com.getcodly.codly;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
import com.chaquo.python.android.AndroidPlatform;

import java.util.Timer;
import java.util.TimerTask;

public class pythonRun extends Fragment {
    String last;
    String code;
    Button pyEnt;
    EditText pyInp;
    TextView out;
    public pythonRun() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_python_run, container, false);
        out = (TextView) v.findViewById(R.id.outPython);
        pyInp = (EditText) v.findViewById(R.id.pyInp);
        pyEnt = (Button) v.findViewById(R.id.pyEnt);
        ReadWrite.write(getActivity().getFilesDir() + "/" + "a", "");
        Python.start(new AndroidPlatform(getActivity()));
        new Thread(new Runnable() {
            public void run(){
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            if(pythonCode.run) {
                                code = pythonCode.pythonCode;
                                ReadWrite.write(getActivity().getFilesDir() + "/" + "pyCode", code.replace("print", "pr").replace("input", "inp"));
                                Python py = Python.getInstance();
                                PyObject pyFile = py.getModule("compiler");
                                pyFile.callAttr("main");
                                pythonCode.run = false;
                            }
                        } catch (Exception e){
                            Log.d("eroor", e.getLocalizedMessage());
                            out.setText(e.getLocalizedMessage());
                        }
                    }
                }, 0, 1000);
            }
        }).start();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //Log.d("hi", ReadWrite.read(PythonActivity.this.getFilesDir() + "/" + "a") + "0");
                if (!ReadWrite.read(getActivity().getFilesDir() + "/" + "pyOut").equals(last)) {
                    Log.d("hi", ReadWrite.read(getActivity().getFilesDir() + "/" + "pyOut"));
                    last = ReadWrite.read(getActivity().getFilesDir() + "/" + "pyOut");
                    out.setText(last.replace("|", System.getProperty("line.separator")));
                    //ReadWrite.write(getActivity().getFilesDir() + "/" + "a", "");
                }
                if(ReadWrite.read(getActivity().getFilesDir() + "/" + "pyInp").equals("wait")){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pyEnt.setVisibility(View.VISIBLE);
                            pyInp.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        }, 0, 1000);
        pyEnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReadWrite.write(getActivity().getFilesDir() + "/" + "pyInp", pyInp.getText().toString());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pyEnt.setVisibility(View.INVISIBLE);
                        pyInp.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
        return v;
    }
}