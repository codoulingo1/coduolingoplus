package com.getcodly.codly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class selectProject extends AppCompatActivity {

    Button createNew;
    ListView projectList;
    List myList;
    public static String codeToLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_project);

        createNew = findViewById(R.id.createNewProjectBtn);
        projectList = findViewById(R.id.projectsListView);

        createNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(selectProject.this, R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_sheet, (RelativeLayout) findViewById(R.id.bottomSheetContainer));
                bottomSheetView.findViewById(R.id.websiteSelect).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(selectProject.this, iframe2.class));
                    }
                });
                bottomSheetView.findViewById(R.id.pythonSelect).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(selectProject.this, PythonActivity2.class));
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

        File path = new File(selectProject.this.getFilesDir() + "/" + "codes");
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
                        codeToLoad = ReadWrite.read(selectProject.this.getFilesDir() + "/" + "codes/" + selectedCode);
                        Log.d("codeToLoad", codeToLoad);
                        startActivity(new Intent(selectProject.this, iframe2.class));
                    }
                });
            }
        }
    }

    @Override
    protected void onResume() {

        super.onResume();
        codeToLoad = null;

        File path = new File(selectProject.this.getFilesDir() + "/" + "codes");
        File[] list = path.listFiles();
        myList = new ArrayList();
        if(list != null){
            for( int i=0; i < list.length; i++)
            {
                myList.add(list[i].getName().replace(".txt", "").replaceAll("pyy", ".py").replaceAll("htmll", ".html"));

                ArrayAdapter<String> mProjectAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myList);

                projectList.setAdapter(mProjectAdapter);

                projectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selectedCode = String.valueOf(myList.get(position)).replaceAll(".html", "htmll").replaceAll(".py", "pyy");
                        codeToLoad = ReadWrite.read(selectProject.this.getFilesDir() + "/" + "codes/" + selectedCode);
                        Log.d("codeToLoad", codeToLoad);
                        if (selectedCode.contains("htmll")) {
                            startActivity(new Intent(selectProject.this, iframe2.class));
                        }
                        if (selectedCode.contains("pyy")) {
                            startActivity(new Intent(selectProject.this, PythonActivity2.class));
                        }
                    }
                });
            }
        }
    }
}
