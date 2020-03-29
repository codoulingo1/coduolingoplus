package com.example.coduolingo;

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

    TextView qs;
    Button submit;
    Boolean isRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lessonCreator("57933", "Math");
        //loadquestion("57933", "Math", "26"); //I hate you all google employees. But not you gradle the legendary elephant.
    }

    public void loadquestion(String id, String name, String qs_num) {
        isRight = false;
        String lesson = DownloadReadlessons.downloadlesson(id, MainActivity.this); // download lesson by ID
        submit = (Button) findViewById(R.id.button);
        qs = (TextView) findViewById(R.id.textView);
        Log.d("gojo", "h"); // pro vi estas gojo
        final HashMap<String, String> hashMap = DownloadReadlessons.readqs(id, name, qs_num, MainActivity.this); // read qs by ID + name + question number
        qs.setText(hashMap.get("qs"));
        Log.d("gojo", hashMap.get("qs"));
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inp = (EditText) findViewById(R.id.inp);
                String ans = inp.getText().toString();
                if (ans.equals(hashMap.get("Answer"))) {
                    qs.setText("Guten");
                    isRight = true; //savta
                    return;
                } else{
                    isRight = false;
                }
            }
        });
    }

    public void lessonCreator(String ID, String name){

        for (int i = 1; i<=3; i++) {
            if(i == 1){
                loadquestion(ID, name, String.valueOf(i));
            }else if(isRight == true){
                loadquestion(ID, name, String.valueOf(i));
            }

        }

    }

    void sleep(int time){
        Runnable r = new Runnable() {
            @Override
            public void run(){
            }
        };
        Handler h = new Handler();
        h.postDelayed(r, time);
    }
}
