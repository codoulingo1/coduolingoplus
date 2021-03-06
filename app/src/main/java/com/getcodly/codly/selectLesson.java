package com.getcodly.codly;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class selectLesson extends AppCompatActivity {
    Button l;
    ArrayList new_names;
    ListView l_list;
    RelativeLayout prac;
    ArrayList fin;
    TextView lessonTitle;
    TextView progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_lesson);
        //Log.d(MainActivity.id, MainActivity.name);
        prac = findViewById(R.id.practice);
        l_list = (ListView) findViewById(R.id.friendList2);
        //Log.d("tName", tree.namesShare.get(1));
        Collections.reverse(tree.namesShare);
        Collections.reverse(tree.idShare);
        lessonTitle = findViewById(R.id.lessonTitle);
        progress = findViewById(R.id.progressFill);
        lessonTitle.setText(mainScreen.inLessonName);
        new_names = new ArrayList();
        int c = 0;
        for (String n : tree.namesShare){
            if (mainScreen.progress.contains(n)){
                new_names.add(n);
                c++;
            }else {
                new_names.add(n);
            }
        }
        selectItemList itemsAdapter = new selectItemList(selectLesson.this, new_names);
        l_list.setAdapter(itemsAdapter);
        progress.setText("הושלמו " + String.valueOf(c) + "/" + String.valueOf(new_names.size()));
        l = (Button) findViewById(R.id.nextLesson);
        prac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fin = new ArrayList();
                List<String> str_old_progress = Arrays.asList(mainScreen.progress.split(","));
                for (String f : tree.idShare){
                    if (str_old_progress.toString().contains(f) && !f.equals("1-1-1")){
                        fin.add(f);
                    }
                }
                try {
                    if (fin.size() > 0) {
                        mainScreen.LessonType = "practice";
                        MainActivity.id = "prac";
                        MainActivity.name = "";
                        tree.practiceID = (String[]) fin.toArray(new String[0]);
                        startActivity(new Intent(selectLesson.this, MainActivity.class));
                    }
                    else{
                        Toast.makeText(selectLesson.this, "עדיין לא למדת מספיק שיעורים בשביל לתרגל", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e) {
                    Toast.makeText(selectLesson.this, "עדיין לא למדת מספיק שיעורים בשביל לתרגל", Toast.LENGTH_LONG).show();
                }
            }
        });
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(selectLesson.this, MainActivity.class));
                overridePendingTransition(0,0);
            }
        });
        l_list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                List<String> str_alt_progress = Arrays.asList(mainScreen.progress.split(" "));
                if (str_alt_progress.toString().contains(tree.idShare.get(position))){
                    MainActivity.id = tree.idShare.get(position);
                    MainActivity.name = tree.namesShare.get(position);
                    //mainScreen.LessonType = "lesson";

                    startActivity(new Intent(selectLesson.this, MainActivity.class));
                    overridePendingTransition(0,0);
                }
                else if (tree.idShare.get(position).equals(MainActivity.id)){
                    //mainScreen.LessonType = "comp";
                    startActivity(new Intent(selectLesson.this, MainActivity.class));
                    overridePendingTransition(0,0);
                }
            }
        });
    }
}
