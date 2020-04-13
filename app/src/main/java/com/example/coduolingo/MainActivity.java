package com.example.coduolingo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
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
    float maxJ;
    ProgressBar pb;
    public static int pr;
    public static String id = "57983";
    public static String name = "Math";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pb = (ProgressBar) findViewById(R.id.progressBar);

       String lesson = DownloadReadlessons.downloadlesson(id, MainActivity.this);
        for (int i = 1; i<20; i++){
            try {
                loadquestion(id, name, String.valueOf(i));
            }
            catch (Exception e) {
                maxJ = i-1;
                break;
            }
        }
        if (j>1 & j<maxJ+1){
            try {
                //pb.setMax(100); // 100 maximum value for the progress value
                lessonCreator(id, name, j);
            }
            catch(Exception e) {

            }
        }
        else if (j<=1) {
            lessonCreator(id, name, 1);
        }
        else {
            qs = (TextView) findViewById(R.id.textView);
            int b = 100 * (j);
            pr = Math.round(b/maxJ);
            for (int numTodel=1; numTodel<=maxJ; numTodel++){
                File dir = getFilesDir();
                File file = new File(".", id + name + "qs" + String.valueOf(numTodel) + ".txt");
                deleteFile(id + name + "qs" + String.valueOf(numTodel));
                ReadWrite.write("a", "a", MainActivity.this);
                ReadWrite.deleteFile("a", MainActivity.this);
                //Log.d(String.valueOf(file.exists()), String.valueOf(deleted));

            }
            pb.setProgress(pr);
            qs.setText("כל הכבוד!");

        }
    }

    public HashMap<String, String> loadquestion(String id, String name, String qs_num) {
        HashMap<String, String> hashMap = DownloadReadlessons.readqs(id, name, qs_num, MainActivity.this); // read qs by ID + name + question number
        return hashMap;

            }

    public void lessonCreator(final String ID, final String name, final int i){
        final HashMap<String, String> hashMap = loadquestion(ID, name, String.valueOf(i));
        j = i;
        shared_id = ID;
        shared_name = name;
        shared_hashmap = hashMap;
        progress();
        if (hashMap.get("type").equals("freedum")){
            startActivity(new Intent(MainActivity.this, freedumQs.class));
        }
        else if(hashMap.get("type").equals("explain")){
            startActivity(new Intent(MainActivity.this, ExplainationQS.class));
        }
        else if(hashMap.get("type").equals("nonfreetext")){
            startActivity(new Intent(MainActivity.this, NonFreedum.class));
        }
        else if(hashMap.get("type").equals("freetext")){
            startActivity(new Intent(MainActivity.this, FreeText.class));

        }
    else{
        qs.setText("finished");
    }}
    public void progress(){
        int b = 100 * (j-1);
        pr = Math.round(b/maxJ);
        pb.setProgress(pr);
    }
}


