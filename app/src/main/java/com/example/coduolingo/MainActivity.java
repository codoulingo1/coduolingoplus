package com.example.coduolingo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static HashMap<String, String> shared_hashmap;
    TextView qs;
    Button submit;
    Boolean isRight;
    public static String shared_id;
    public static String shared_name;
    public static int j;
    public static int is_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (j>1){
            try {
                lessonCreator("57933", "Math", j);
            }
            catch(Exception e) {

            }
        }
        else {
            lessonCreator("57933", "Math", 1);
        }
        //loadquestion("57933", "Math", "26"); //I hate you all google employees. But not you gradle the legendary elephant.
    }

    public HashMap<String, String> loadquestion(String id, String name, String qs_num) {
        isRight = false;
        String lesson = DownloadReadlessons.downloadlesson(id, MainActivity.this); // download lesson by ID
        submit = (Button) findViewById(R.id.button);
        qs = (TextView) findViewById(R.id.textView);
        Log.d("gojo", "h"); // pro vi estas gojo
        HashMap<String, String> hashMap = DownloadReadlessons.readqs(id, name, qs_num, MainActivity.this); // read qs by ID + name + question number
        return hashMap;

            }

    public void lessonCreator(final String ID, final String name, final int i){
        final HashMap<String, String> hashMap = loadquestion(ID, name, String.valueOf(i));
        j = i;
        shared_id = ID;
        shared_name = name;
        shared_hashmap = hashMap;
        if (hashMap.get("type").equals("freedum")){
            startActivity(new Intent(MainActivity.this, freedumQs.class));
        }
        if(hashMap.get("type").equals("explain")){
            startActivity(new Intent(MainActivity.this, ExplainationQS.class));
        }
        if(hashMap.get("type").equals("nonfreetext")){
            startActivity(new Intent(MainActivity.this, NonFreedum.class));
        }

        qs.setText(hashMap.get("qs"));
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inp = (EditText) findViewById(R.id.inp);
                String ans = inp.getText().toString();
                if (ans.equals(hashMap.get("Answer"))) {
                    qs.setText("Guten");
                    try {
                        lessonCreator(ID, name, i + 1);
                    }
                    catch (Exception e){
                        Log.d("Error", "No files");
                    }
                 }
                else{

                }
            }
        });
    }
}


