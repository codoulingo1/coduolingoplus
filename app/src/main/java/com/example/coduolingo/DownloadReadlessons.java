package com.example.coduolingo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.HashMap;

import androidx.core.app.ActivityCompat;

/**
 * Created by bobyo on 27/03/2020.
 */

public class DownloadReadlessons {
    public static String  downloadlesson(final String ID, final Context c) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Lessons").child(ID);
        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String folder_main = dataSnapshot.child("LessonID").getValue(String.class) + dataSnapshot.child("LessonName").getValue(String.class);
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    if (snap.getValue(String.class).length() != 0) {
                        Log.d("VAL", "Value is: " + snap.getKey() + "    " + snap.getValue(String.class)); //grandmas are very dumb
                        ActivityCompat.requestPermissions((Activity) c,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                        ActivityCompat.requestPermissions((Activity) c,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                        File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + ID);
                        boolean success = true;
                        if (!folder.exists()) {
                            success = folder.mkdirs();
                        }
                        if (success) {
                            ReadWrite.write(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + ID + "/" + folder_main + snap.getKey(), snap.getValue(String.class), c); // write qs text file
                            Log.d("sucsess", "guten");
                        } else {
                            Log.d("sucsess", "nicht guten");
                        }
                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("error", "Failed to read value.", error.toException());
            }
        });
        return "via avino";
    }
    public static HashMap<String, String> readqs(String id, String name, String qs_num, final Context c) {
        String content = ReadWrite.read(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + id + "/" + id + name + "qs" + qs_num, c); // read downloaded qs
        Log.d("loc", id + name + "qs" + qs_num);
        HashMap<String, String> hashMap = new HashMap<>(); // create hashmap
        String[] arr = content.split("\\]|\\[");
        hashMap.put("type", arr[1]);
        hashMap.put("qs", arr[3]);
        hashMap.put("Content", arr[5]);
        hashMap.put("Image", arr[7]);
        hashMap.put("Answer", arr[9]);
        hashMap.put("additional", arr[11]);
        return hashMap; //goj


    }
}
