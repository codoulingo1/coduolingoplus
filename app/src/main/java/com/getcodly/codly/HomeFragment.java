package com.getcodly.codly;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
    String progress;
    TextView t2;
    View clickView1;
    View clickView2;
    Button toProjects;
    int courseProgressWeb1 = 0;
    int courseProgressPy1 = 0;
    ProgressBar courseProgressWebHome;
    ProgressBar courseProgressPyHome;
    TextView courseProgressPercentageWebHome;
    TextView courseProgressPercentagePyHome;

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
        courseProgressWebHome = v.findViewById(R.id.progressBarHome2);
        courseProgressPyHome = v.findViewById(R.id.progressBarHome1);
        courseProgressPercentagePyHome = v.findViewById(R.id.homeProgressPercentage1);
        courseProgressPercentageWebHome = v.findViewById(R.id.homeProgressPercentage2);

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
                Intent intent = new Intent(getActivity(), tree_html_improved.class);
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
        HashMap <String, String> date = DownloadReadlessons.get_last_lesson2(ReadWrite.read(getActivity().getFilesDir() + File.separator + "user"), new DownloadReadlessons.HashCallback() {
            @Override
            public void onCallback(HashMap<String, String> value) {
                progress = value.get("cProgress");
                setCourseProgressWeb();
                setProgressPython();
                if (ifLfinished(Arrays.asList("1-4-2"))){
                    t1.setText("שגיאות");
                }
                else if (ifLfinished(Arrays.asList("1-3-2"))){
                    t1.setText("לולאות");
                }
                else if (ifLfinished(Arrays.asList("1-2-3"))){
                    t1.setText("תנאים");
                }
                else if (ifLfinished(Arrays.asList("1-1-2"))){
                    t1.setText("משתנים");
                }
                else{
                    t1.setText("מבוא לפייתון");
                }
                if (ifLfinished(Arrays.asList("2-4-2"))){
                    t2.setText("קלט");
                }
                else if (ifLfinished(Arrays.asList("2-3-2"))){
                    t2.setText("תגיות מתקדמות");
                }
                else if (ifLfinished(Arrays.asList("2-2-1"))){
                    t2.setText("יצירת אתר ראשון");
                }
                else if (ifLfinished(Arrays.asList("2-1-2"))){
                    t2.setText("מבנה של אתר");
                }
                else{
                    t2.setText("מבוא");
                }

            }
        });

        return v;
    }
    boolean ifLfinished(List<String> id){
        boolean ret = true;
        for (String i : id){
            Log.d("testyTestyTest", mainScreen.progress);
            Log.d("testy2", i);
            if (!progress.contains(i)) {
                ret = false;
                break;
            }
        }
        Log.d(String.valueOf(ret), String.valueOf(ret));
        return ret;
    }

    void setCourseProgressWeb(){
        for(String i : progress.split(",")){
            if(i.startsWith("2")){
                courseProgressWeb1++;
            }
        }

        courseProgressWebHome.setProgress(courseProgressWeb1 * 100 / 10);

        int courseProgressWeb1Finale = courseProgressWeb1 * 100 / 10;

        if(courseProgressWeb1Finale == 0){
            courseProgressPercentageWebHome.setTextColor(Color.parseColor("#F5F5F5"));
            courseProgressPercentageWebHome.setText(String.valueOf(courseProgressWeb1Finale) + "%");
        } else{
            courseProgressPercentageWebHome.setText(String.valueOf(courseProgressWeb1Finale) + "%");
        }
    }
    void setProgressPython(){
        for(String i : progress.split(",")){
            if(i.startsWith("1")){
                courseProgressPy1++;
            }
        }

        courseProgressPyHome.setProgress(courseProgressPy1 * 100 / 17);

        int courseProgressPy1Finale = courseProgressPy1 * 100 / 17;

        if(courseProgressPy1Finale == 0){
            courseProgressPercentagePyHome.setTextColor(Color.parseColor("#F5F5F5"));
            courseProgressPercentagePyHome.setText(String.valueOf(courseProgressPy1Finale) + "%");
        } else{
            courseProgressPercentagePyHome.setText(String.valueOf(courseProgressPy1Finale) + "%");
        }

    }
}
