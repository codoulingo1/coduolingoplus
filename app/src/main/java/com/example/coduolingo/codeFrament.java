package com.example.coduolingo;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


public class codeFrament extends Fragment  { //was extends Fragment, might need to change that

    public static String htmlCode;
    ImageButton submitBtn;
    EditText htmlInp;
    Button help1;
    Button help2;
    Button help3;

    private AnimatedVectorDrawable animation;

    public codeFrament() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_code_frament, container, false);
        submitBtn = (ImageButton) v.findViewById(R.id.submitHTML);
        htmlInp = (EditText) v.findViewById(R.id.inputHTML);
        help1 = (Button) v.findViewById(R.id.help1);
        help2 = (Button) v.findViewById(R.id.help2);
        help3 = (Button) v.findViewById(R.id.help3);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitBtn.setImageResource(R.drawable.avd_anim);
                animate();
                htmlCode = htmlInp.getText().toString();
                openFragment();
            }
        });

        help1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newCode = htmlInp.getText().toString() + ">";
                htmlInp.setText(newCode);
            }
        });
        help2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newCode = htmlInp.getText().toString() + "<";
                htmlInp.setText(newCode);
            }
        });
        help3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newCode = htmlInp.getText().toString() + "<";
                htmlInp.setText(newCode);
            }
        });
        help3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newCode = htmlInp.getText().toString() + "/";
                htmlInp.setText(newCode);
            }
        });
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


        return v;
    }

    private void openFragment() {
        browserFragment BrowserFragment = new browserFragment();

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.add(R.id.fragment_container, BrowserFragment, "FRAGMENT").commit();
    }

    public void animate(){
        Drawable d = submitBtn.getDrawable();
        if (d instanceof AnimatedVectorDrawable) {

            Log.d("testanim", "onCreate: instancefound" + d.toString());
            animation = (AnimatedVectorDrawable) d;
            animation.start();
        }
    }
}
