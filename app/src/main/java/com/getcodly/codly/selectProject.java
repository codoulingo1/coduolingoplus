package com.getcodly.codly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.util.List;

public class selectProject extends AppCompatActivity {

    Button createNew;
    ListView projectList;
    List myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_project);

        createNew = findViewById(R.id.createNewProjectBtn);
        projectList = findViewById(R.id.projectsListView);

        createNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(selectProject.this, iframe2.class));
            }
        });

        File path = new File(selectProject.this.getFilesDir() + "/codes/" + "");
        File list[] = path.listFiles();

        Log.d("testnum7", ReadWrite.read(selectProject.this.getFilesDir() + "/" + "codes/" + "abc") + "1");
        for( int i=0; i< list.length; i++)
        {
            try {
                myList.add(list[i].getName());
            } catch (Exception e){

            }
        }

        ArrayAdapter<String> mProjectAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myList);

        projectList.setAdapter(mProjectAdapter);

    }
}
