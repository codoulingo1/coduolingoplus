package com.getcodly.codly;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by bobyo on 27/03/2020.
 */

public class DownloadReadlessons {
    public static interface MyCallback {
        void onCallback(String value);
    }

    public static interface HashCallback {
        void onCallback(HashMap <String, String> value);
    }
    public static interface ListCallback {
        void onCallback(List<String> value);
    }

        public static String downloadlesson(String ID, final Context c, MyCallback m) {
            //final AtomicBoolean done = new AtomicBoolean(false);
            //String init="Inital Value";
            //shared.set(init);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Lessons").child(ID);
            myRef.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    final File newFile = new File(c.getFilesDir() + "/id/");
                    if (!newFile.exists()) {
                        newFile.mkdirs();
                        Log.d("Create", "dir");
                    }

                    String folder_main = dataSnapshot.child("LessonID").getValue(String.class) + dataSnapshot.child("LessonName").getValue(String.class);
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                        if (snap.getValue(String.class).length() != 0) {
                            Log.d(folder_main + snap.getKey(), "Value is: " + snap.getKey() + "    " + snap.getValue(String.class));
                            File f = new File(c.getFilesDir() + "/id/" + folder_main + snap.getKey() + ".txt");
                            if (!f.getParentFile().exists())
                                f.getParentFile().mkdirs();
                            if (!f.exists()) {
                                try {
                                    f.createNewFile();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            ReadWrite.write(c.getFilesDir() + "/id/" + folder_main + snap.getKey(), snap.getValue(String.class));
                        }
                    }
                    m.onCallback("done");
                    //done.set(true);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("error", "Failed to read value.", error.toException());
                }
            });
            return "a";
        }
    public static String downloadcomp(String ID, final Context c, MyCallback m) {
        //final AtomicBoolean done = new AtomicBoolean(false);
        //String init="Inital Value";
        //shared.set(init);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Lessons").child(ID);
        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final File newFile = new File(c.getFilesDir() + "/id/");
                if (!newFile.exists()) {
                    newFile.mkdirs();
                    Log.d("Create", "dir");
                }

                String folder_main = dataSnapshot.child("LessonID").getValue(String.class) + dataSnapshot.child("LessonName").getValue(String.class);
                MainActivity.name = dataSnapshot.child("LessonName").getValue(String.class);
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                int qs_num = 0;
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    try {
                    if (snap.getValue(String.class).length() != 0 && !snap.getValue(String.class).split("\\]|\\[")[1].replace("\\n", System.getProperty("line.separator")).equals("explain")) {
                        Log.d(folder_main + snap.getKey(), "Value is: " + snap.getKey() + "    " + snap.getValue(String.class));
                        File f = new File(c.getFilesDir() + "/id/" + folder_main + snap.getKey() + ".txt");
                        if (!f.getParentFile().exists())
                            f.getParentFile().mkdirs();
                        if (!f.exists()) {
                            try {
                                f.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        qs_num++;
                        ReadWrite.write(c.getFilesDir() + "/id/" + folder_main + "qs" + String.valueOf(qs_num), snap.getValue(String.class));
                    }
                } catch (Exception e) {

                    }
                    }
                m.onCallback("done");
                //done.set(true);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("error", "Failed to read value.", error.toException());
            }
        });
        return "a";
    }

        public static String downloadPractice(final String[] ID, final int maxQS, final Context c, MyCallback m) {
            FirebaseDatabase database = FirebaseDatabase.getInstance(); //hello
            DatabaseReference myRef = database.getReference("Lessons");
            myRef.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    final File newFile = new File(c.getFilesDir() + "/id/");
                    if (!newFile.exists()) {
                        newFile.mkdirs();
                        Log.d("Create", "dir");
                    }

                    String folder_main = "prac"; //hello
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    ArrayList<String> first = new ArrayList<String>();
                    for (String i : ID) {
                        for (DataSnapshot snap : dataSnapshot.child(i).getChildren()) {
                            if (snap.getValue(String.class).length() != 0) {
                                try {
                                    String[] arr = snap.getValue(String.class).split("\\]|\\[");
                                    if (!arr[1].replace("\\n", System.getProperty("line.separator")).equals("explain")
                                            && !arr[1].replace("\\n", System.getProperty("line.separator")).equals("runPy")
                                            && !arr[1].replace("\\n", System.getProperty("line.separator")).equals("showPy")) {
                                        first.add(snap.getValue(String.class));
                                    }
                                } catch (Exception e) {
                                    Log.d("err", String.valueOf(snap.getKey()));
                                }
                            }
                        }
                    }
                    Collections.shuffle(first);
                    int wr_num = 1;
                    for (String wr : first) {
                        File f = new File(c.getFilesDir() + "/id/" + folder_main + "qs" + wr_num + ".txt");
                        if (!f.getParentFile().exists())
                            f.getParentFile().mkdirs();
                        if (!f.exists()) {
                            try {
                                f.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (wr_num <= maxQS) {
                            Log.d(folder_main + "qs" + wr_num, "Value is: " + "qs" + wr_num + "    " + wr);
                            ReadWrite.write(c.getFilesDir() + "/id/" + folder_main + "qs" + wr_num, wr);
                        }

                        wr_num++;
                        Log.d("wr_num", String.valueOf(wr_num));
                    }
                    m.onCallback("done");
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("error", "Failed to read value.", error.toException());
                }
            });
            return "a";
        }

        public static HashMap<String, String> readqs(String id, String name, String qs_num, Context c) {
            String content = ReadWrite.read(c.getFilesDir() + "/" + "id" + "/" + id + name + "qs" + qs_num);
            HashMap<String, String> hashMap = new HashMap<>();
            String[] arr = content.split("\\]|\\[");
            Log.d("check", content.toString());
            try {
                hashMap.put("type", arr[1].replace("\\n", System.getProperty("line.separator")));
            } catch (Exception e) {
                hashMap.put("type", arr[1]);
            }
            try {
                hashMap.put("qs", arr[3].replace("\\n", System.getProperty("line.separator")));
            } catch (Exception e) {
                hashMap.put("qs", arr[3]);
            }
            try {
                hashMap.put("Content", arr[5].replace("\\n", System.getProperty("line.separator")));
            } catch (Exception e) {
                hashMap.put("Content", arr[5]);
            }
            try {
                hashMap.put("Image", arr[7].replace("\\n", System.getProperty("line.separator")));
            } catch (Exception e) {
                hashMap.put("Image", arr[7]);
            }
            try {
                hashMap.put("Answer", arr[9].replace("\\n", System.getProperty("line.separator")));
            } catch (Exception e) {
                hashMap.put("Answer", arr[9]);
            }
            try {
                hashMap.put("additional", arr[11].replace("\\n", System.getProperty("line.separator")));
            } catch (Exception e) {
                hashMap.put("additional", arr[11]);
            }

            return hashMap;
        }

        public static String readImage(String id, String name, String qs_num, Context c) {
            String content = ReadWrite.read(c.getFilesDir() + "/" + "id" + "/" + id + name + "qs" + qs_num);
            HashMap<String, String> hashMap = new HashMap<>();
            String[] arr = content.split("\\]|\\[");
            Log.d("check", content.toString());
            return arr[7];

        }

    public static HashMap<String, String> get_last_lesson(String email) {
        final HashMap<String, String> ret = new HashMap<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users").child(email);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is uploaded
                ret.put("year", dataSnapshot.child("lastLessonD").child("year").getValue().toString());
                //ret.put("month", dataSnapshot.child("lastLessonD").child("month").getValue().toString());
                ret.put("date", dataSnapshot.child("lastLessonD").child("date").getValue().toString());
                ret.put("streak", dataSnapshot.child("streak").getValue().toString());
                ret.put("cProgress", dataSnapshot.child("progress").getValue().toString());//2
                ret.put("xp", dataSnapshot.child("xp").getValue().toString());
                ret.put("name", dataSnapshot.child("name").getValue().toString());
                ret.put("img", dataSnapshot.child("imgUrl").getValue().toString());
                ret.put("email", dataSnapshot.child("email").getValue().toString());
                ret.put("streak freeze", dataSnapshot.child("streak freeze").getValue().toString());
                ret.put("friends", dataSnapshot.child("friends").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Failed to read value.", error.toException());
            }
        });
        return ret;
    }
        public static HashMap<String, String> get_last_lesson2(String email, HashCallback m) {
            final HashMap<String, String> ret = new HashMap<>();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Users").child(email);
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is uploaded
                    try {
                        ret.put("streak", dataSnapshot.child("streak").getValue().toString());
                        ret.put("year", dataSnapshot.child("lastLessonD").child("year").getValue().toString());
                        //ret.put("month", dataSnapshot.child("lastLessonD").child("month").getValue().toString());
                        ret.put("date", dataSnapshot.child("lastLessonD").child("date").getValue().toString());
                        ret.put("cProgress", dataSnapshot.child("progress").getValue().toString());//2
                        ret.put("xp", dataSnapshot.child("xp").getValue().toString());
                        ret.put("name", dataSnapshot.child("name").getValue().toString());
                        ret.put("img", dataSnapshot.child("imgUrl").getValue().toString());
                        ret.put("email", dataSnapshot.child("email").getValue().toString());
                        ret.put("streak freeze", dataSnapshot.child("streak freeze").getValue().toString());
                        ret.put("friends", dataSnapshot.child("friends").getValue().toString());
                    } catch (Exception e){
                        ret.put("streak", "0");
                        ret.put("year", "0");
                        //ret.put("month", dataSnapshot.child("lastLessonD").child("month").getValue().toString());
                        ret.put("date", "0");
                        ret.put("cProgress", "0");//2
                        ret.put("xp", "0");
                        ret.put("name", "0");
                        ret.put("img", "0");
                        ret.put("email", "0");
                        ret.put("streak freeze", "0");
                        ret.put("friends", "0");
                    }
                    m.onCallback(ret);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("Failed to read value.", error.toException());
                }
            });
            return ret;
        }
    public static HashMap<String, String> get_progress(String email, HashCallback m) {
        final HashMap<String, String> ret = new HashMap<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users").child(email).child("progress");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is uploaded
                ret.put("progress", dataSnapshot.getValue().toString());
                m.onCallback(ret);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Failed to read value.", error.toException());
            }
        });
        return ret;
    }

        public static List<String> get_emails() {
            final List<String> Address;
            Address = new ArrayList<>();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Users");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    for (DataSnapshot fire_email : dataSnapshot.getChildren()) {
                        Address.add(fire_email.getKey());
                    }

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("Failed to read value.", error.toException());
                }
            });
            return Address;
        }
    public static void get_names(HashCallback m) {
        final HashMap<String, String> names = new HashMap<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot fire_email : dataSnapshot.getChildren()) {
                    try {
                        names.put(fire_email.child("name").getValue().toString(), fire_email.getKey());
                        Log.d("hi", names.toString());
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                m.onCallback(names);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Failed to read value.", error.toException());
            }
        });
    }
}
