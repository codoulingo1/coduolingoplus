package com.getcodly.codly;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class selectLesson extends AppCompatActivity {
    Button l;
    ListView l_list;
    Button prac;
    ArrayList fin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_lesson);
        prac = (Button) findViewById(R.id.practice);
        l_list = (ListView) findViewById(R.id.friendList2);
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(selectLesson.this, android.R.layout.simple_list_item_1, android.R.id.text1, tree.namesShare);
        l_list.setAdapter(itemsAdapter);
        l = (Button) findViewById(R.id.nextLesson);
        prac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> str_old_progress = Arrays.asList(mainScreen.progress.split(" "));
                for (String f : tree.idShare){
                    if (str_old_progress.contains(f)){
                        fin.add(f);
                    }
                }
                tree.LessonType = "practice";
                MainActivity.id = "prac";
                MainActivity.name = "";
                try {
                    tree.practiceID = (String[]) fin.toArray(new String[0]);
                    startActivity(new Intent(selectLesson.this, MainActivity.class));
                } catch (Exception e){

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
                List<String> str_old_progress = Arrays.asList(mainScreen.progress.split(" "));
                    if (str_old_progress.contains(tree.idShare[position])){
                        MainActivity.id = tree.idShare[position];
                        MainActivity.name = tree.namesShare[position];

                        startActivity(new Intent(selectLesson.this, MainActivity.class));
                        overridePendingTransition(0,0);
                }
                    else if (tree.idShare[position].equals(MainActivity.id)){
                        startActivity(new Intent(selectLesson.this, MainActivity.class));
                        overridePendingTransition(0,0);
                    }
            }
        });
    }
}
