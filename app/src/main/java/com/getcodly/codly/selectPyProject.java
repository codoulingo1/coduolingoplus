package com.getcodly.codly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class selectPyProject extends AppCompatActivity {

    Button createNew;
    ListView projectList;
    List myList;
    public static String pyCodeToLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_project);

        createNew = findViewById(R.id.createNewProjectBtn);
        projectList = findViewById(R.id.projectsListView);

        createNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(selectPyProject.this, PythonActivity2.class));
            }
        });

        File path = new File(selectPyProject.this.getFilesDir() + "/" + "codes");
        File[] list = path.listFiles();
        myList = new ArrayList();
        if(list != null){
            for( int i=0; i < list.length; i++)
            {
                myList.add(list[i].getName().replace(".txt", ""));

                ArrayAdapter<String> mProjectAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myList);

                projectList.setAdapter(mProjectAdapter);

                projectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selectedCode = String.valueOf(myList.get(position));
                        pyCodeToLoad = ReadWrite.read(selectPyProject.this.getFilesDir() + "/" + "codes/" + selectedCode);
                        Log.d("codeToLoad", pyCodeToLoad);
                        startActivity(new Intent(selectPyProject.this, PythonActivity2.class));
                    }
                });
            }
        }
    }

    @Override
    protected void onResume() {

        super.onResume();
        pyCodeToLoad = null;

        File path = new File(selectPyProject.this.getFilesDir() + "/" + "codes");
        File[] list = path.listFiles();
        myList = new ArrayList();
        if(list != null){
            for( int i=0; i < list.length; i++)
            {
                myList.add(list[i].getName().replace(".txt", ""));

                ArrayAdapter<String> mProjectAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myList);

                projectList.setAdapter(mProjectAdapter);

                projectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selectedCode = String.valueOf(myList.get(position));
                        pyCodeToLoad = ReadWrite.read(selectPyProject.this.getFilesDir() + "/" + "codes/" + selectedCode);
                        Log.d("codeToLoad", pyCodeToLoad);
                        startActivity(new Intent(selectPyProject.this, PythonActivity2.class));
                    }
                });
            }
        }
    }
}
