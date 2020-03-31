package com.example.coduolingo;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * Created by bobyo on 27/03/2020.
 */

public class DownloadReadlessons {
    public static String  downloadlesson(String ID, final Context c) {
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
                        ReadWrite.write(folder_main + snap.getKey(), snap.getValue(String.class), c); // write qs text file
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
        String content = ReadWrite.read(id + name + "qs" + qs_num, c); // read downloaded qs
        HashMap<String, String> hashMap = new HashMap<>(); // create hashmap
        String[] arr = content.split("\\]|\\[");
        Log.d("check", content.toString());
        hashMap.put("type", arr[1]);
        Log.d("avino", arr[3]);
        hashMap.put("qs", arr[3]);
        hashMap.put("Content", arr[5]);
        hashMap.put("Image", arr[7]);
        hashMap.put("Answer", arr[9]);
        return hashMap; //goj


    }
}
