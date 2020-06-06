package com.example.coduolingo;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

public class LessonActivity extends AppCompatActivity {

    public static HashMap<String, String> shared_hashmap;
    TextView qs;
    Button submit;
    Boolean isRight;
    public static String shared_id;
    public static String shared_name;
    public static Double shared_xp = 0.0;
    public static int j;
    public static int is_back;
    float maxJ;
    //ProgressBar pb;
    public static int pr;
    HashMap <String, String> date;
    CountDownTimer mcountdown;;
    public static int shared_xp2;

    ImageButton continueBtn20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_lesson);
        //pb = (ProgressBar) findViewById(R.id.progressBar2);
        String id = MainActivity.id;
        String name = MainActivity.name;
        for (int i = 1; i<20; i++){
            try {
                loadquestion(id, name, String.valueOf(i));
            }
            catch (Exception e) {
                maxJ = i-1;
                Log.d("hi", String.valueOf(maxJ));
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
            qs = (TextView) findViewById(R.id.textView4);
            int b = 100 * (j);
            pr = Math.round(b / maxJ);
            pr = Math.round(b / maxJ);
            date = DownloadReadlessons.get_last_lesson(ReadWrite.read(this.getFilesDir()+File.separator+ "user"));
            j = 0;
            File dirName = new File(Environment.getExternalStorageDirectory() + "/" + "id" + "/");
            boolean a = false;
            Log.d("bona", "bona");

            try {
                FileUtils.deleteDirectory(dirName);
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("malbona", "malbona");
            }
            Log.d(String.valueOf(a), String.valueOf(a));
            //Log.d(String.valueOf(file.exists()), String.valueOf(deleted));

            //pb.setProgress(pr);
            //qs.setText("כל הכבוד!");

            mcountdown = new CountDownTimer(1000, 1000) {
                @Override
                public void onTick(long l) {
                    //dialog.show();
                    Log.d("Loading", "Loading");
                }

                @Override
                public void onFinish() {
                    int year = Integer.parseInt(date.get("year"));
                    int month = Integer.parseInt(date.get("month"));
                    int day = Integer.parseInt(date.get("date"));
                    int xp = (int) Double.parseDouble(date.get("xp"));
                    String old_progress = String.valueOf(date.get("cProgress"));
                    Calendar calendar = Calendar.getInstance();

                    // Move calendar to yesterday
                    calendar.add(Calendar.DATE, -1);

                    // Get current date of calendar which point to the yesterday now
                    int yesterday = calendar.get(Calendar.DATE);
                    Calendar calendar2 = Calendar.getInstance();
                    int today = calendar2.get(Calendar.DATE);
                    Log.d("0", String.valueOf(calendar.get(Calendar.YEAR)));
                    if (year == calendar.get(Calendar.YEAR) - 1900) {
                        Log.d("1", "1");
                        if (month == calendar.get(Calendar.MONTH)) {
                            Log.d("2", "2");
                            if (day == yesterday) {
                                Log.d("3", "3");
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("Users").child(ReadWrite.read(LessonActivity.this.getFilesDir()+File.separator + "user"));
                                myRef.child("streak").setValue(String.valueOf(Integer.parseInt(date.get("streak")) + 1));
                            } else if (day == today) {
                                Log.d("3", "3");
                            } else {
                                Log.d("3", "3");
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("Users").child(ReadWrite.read(LessonActivity.this.getFilesDir()+File.separator + "user"));
                                myRef.child("streak").setValue(0);
                                Log.d("error", "day");
                            }
                        }else{
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("Users").child(ReadWrite.read(LessonActivity.this.getFilesDir()+File.separator + "user"));
                            myRef.child("streak").setValue(0);
                            Log.d("error", "month");
                        }
                    }else{
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("Users").child(ReadWrite.read(LessonActivity.this.getFilesDir()+File.separator + "user"));
                        myRef.child("streak").setValue(0);
                        Log.d("error", "year");
                    }
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Users");
                    DatabaseReference user = myRef.child(ReadWrite.read(LessonActivity.this.getFilesDir()+File.separator + "user"));
                    Date currentTime = Calendar.getInstance().getTime();
                    Timestamp ts = new Timestamp(currentTime.getTime());
                    user.child("lastLessonD").setValue(ts);
                    user.child("xp").setValue(xp + shared_xp);
                    shared_xp2 = shared_xp.intValue();
                    List<String> str_old_progress = Arrays.asList(old_progress.split(" "));
                    if(!str_old_progress.contains(MainActivity.id)) {
                        //user.child("progress").setValue(old_progress + " " + MainActivity.id);
                    }
                    startActivity(new Intent(LessonActivity.this, finalLesson.class));
                }
            }.start();
        }

        continueBtn20 = (ImageButton) findViewById(R.id.continueBtn2000);

        continueBtn20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LessonActivity.this, tree.class));
            }
        });
    }

    public HashMap<String, String> loadquestion(String id, String name, String qs_num) {
        HashMap<String, String> hashMap = DownloadReadlessons.readqs(id, name, qs_num, LessonActivity.this); // read qs by ID + name + question number
        return hashMap;

    }

    public void lessonCreator(final String ID, final String name, final int i){
        final HashMap<String, String> hashMap = loadquestion(ID, name, String.valueOf(i));
        Log.d(hashMap.toString(), hashMap.toString());
        j = i;
        shared_id = ID;
        shared_name = name;
        shared_hashmap = hashMap;
        progress();
        Log.d("j", String.valueOf(j));
        if (hashMap.get("type").equals("freedum")){
            startActivity(new Intent(LessonActivity.this, freedumQs.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        else if(hashMap.get("type").equals("explain")){
            startActivity(new Intent(LessonActivity.this, ExplainationQS.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        else if(hashMap.get("type").equals("nonfreetext")){
            startActivity(new Intent(LessonActivity.this, NonFreedum.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        else if(hashMap.get("type").equals("freetext")){
            startActivity(new Intent(LessonActivity.this, FreeText.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        else{
            qs.setText("finished");
        }}
    public void progress(){
        int b = 100 * (j-1);
        pr = Math.round(b/maxJ);
        //pb.setProgress(pr);
    }
    @Override
    public void onBackPressed() {
        DialogBack dialogBack = new DialogBack();
        dialogBack.show(getSupportFragmentManager(), "Example Dialog");
    }

}