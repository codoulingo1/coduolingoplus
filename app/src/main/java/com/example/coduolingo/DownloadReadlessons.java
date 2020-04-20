package com.example.coduolingo;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.IOException;
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
                final File newFile = new File(Environment.getExternalStorageDirectory() +"/id/");
                if (!newFile.exists()){
                    newFile.mkdirs();
                    Log.d("Create", "dir");
                }

                String folder_main = dataSnapshot.child("LessonID").getValue(String.class) + dataSnapshot.child("LessonName").getValue(String.class);
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    if (snap.getValue(String.class).length() != 0) {
                        Log.d(folder_main + snap.getKey(), "Value is: " + snap.getKey() + "    " + snap.getValue(String.class));
                        File f = new File(Environment.getExternalStorageDirectory() + "/id/" + folder_main + snap.getKey()  + ".txt");
                        if (!f.getParentFile().exists())
                            f.getParentFile().mkdirs();
                        if (!f.exists()) {
                            try {
                                f.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        ReadWrite.write(Environment.getExternalStorageDirectory() + "/id/" + folder_main + snap.getKey() , snap.getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("error", "Failed to read value.", error.toException());
            }
        });
        return "a";
    }
    public static HashMap<String, String> readqs(String id, String name, String qs_num) {
        String content = ReadWrite.read(Environment.getExternalStorageDirectory() +"/" + "id" + "/" + id + name + "qs" + qs_num);
        HashMap<String, String> hashMap = new HashMap<>();
        String[] arr = content.split("\\]|\\[");
        Log.d("check", content.toString());
        try {
            hashMap.put("type", arr[1].replace("\\n", System.getProperty("line.separator")));
        }catch (Exception e){hashMap.put("type", arr[1]);}try {
            hashMap.put("qs", arr[3].replace("\\n", System.getProperty("line.separator")));
        }catch (Exception e){hashMap.put("qs", arr[3]);}try {
            hashMap.put("Content", arr[5].replace("\\n", System.getProperty("line.separator")));
        }catch (Exception e){hashMap.put("Content", arr[5]);}try {
            hashMap.put("Image", arr[7].replace("\\n", System.getProperty("line.separator")));
        }catch (Exception e){hashMap.put("Image", arr[7]);}try {
            hashMap.put("Answer", arr[9].replace("\\n", System.getProperty("line.separator")));
        }catch (Exception e){hashMap.put("Answer", arr[9]);}try {
            hashMap.put("additional", arr[11].replace("\\n", System.getProperty("line.separator")));
        }catch (Exception e){hashMap.put("additional", arr[11]);}

        return hashMap;
    }
}
