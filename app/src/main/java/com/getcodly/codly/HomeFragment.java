package com.getcodly.codly;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class HomeFragment extends Fragment {

    RelativeLayout course1;
    ImageView courseImage1;
    ArrayList<String> pyC;
    ArrayList<String> htmlC;
    TextView t1;
    TextView t2;
    View clickView1;
    View clickView2;
    Button toProjects;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        course1 = (RelativeLayout) v.findViewById(R.id.course1);
        courseImage1 = (ImageView) v.findViewById(R.id.courseImage1);
        pyC = new ArrayList<String>();
        htmlC = new ArrayList<String>();
        pyC.addAll(Arrays.asList("1-1-1~מבוא לפייתון", "1-1-2~הקוד הראשון שלי", "1-2-1~מבוא למשתנים", "1-2-2~טקסטים ומספרים", "1-2-3~תכנות אינטראקטיבי",
                "1-3-1~תנאים", "1-3-2~הפקודות else וelif", "1-5-1~לולאת while", "1-4-1~שגיאות וחריגים"));
        htmlC.addAll(Arrays.asList("2-1-1~מבוא לפיתוח אתרים", "2-1-2~יצירת פסקאות", "2-2-1~מבנה של אתר", "2-3-1~יצירת האתר הראשון", "2-3-2~יצירת האתר הראשון 2", "2-4-1~תגיות מתקדמות"));
        t1 = (TextView) v.findViewById(R.id.t1);
        t2 = (TextView) v.findViewById(R.id.t2);
        clickView1 = (View) v.findViewById(R.id.clickView1);
        clickView2 = v.findViewById(R.id.clickView2);
        toProjects = v.findViewById(R.id.toProjects);

        clickView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), tree.class);
                startActivity(intent);
            }
        });

        clickView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), tree_html.class);
                startActivity(intent);
            }
        });
        toProjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), selectProject.class);
                startActivity(intent);
            }
        });



        return v;
    }
}
