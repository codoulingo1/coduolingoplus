package com.getcodly.codly;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

        File path = new File(selectProject.this.getFilesDir() + "/codes/" + "");
        File list[] = path.listFiles();
        for( int i=0; i< list.length; i++)
        {
            myList.add( list[i].getName() );
        }

        ArrayAdapter<String> mProjectAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myList);

        projectList.setAdapter(mProjectAdapter);

    }
}
