package com.getcodly.codly;

import android.content.Context;
import android.os.CountDownTimer;
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
import java.io.UTFDataFormatException;
import java.util.ArrayList;
import java.util.Arrays;
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
    public static final HashMap<String, String> rett = new HashMap<>();
    public static interface MyCallback {
        void onCallback(String value);
    }

    public static interface HashCallback {
        void onCallback(HashMap <String, String> value);
    }
    public static interface HashCallback2 {
        void onCallback(HashMap<String, ArrayList<String>> value);
    }
    public static interface HashCallback3 {
        void onCallback(HashMap<String, ArrayList> value);
    }
    public static interface ListCallback {
        void onCallback(List<String> value);
    }

    public static HashMap<String, String> downloadlesson(String ID, final Context c, MyCallback m) {
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
                try{
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
                        rett.put(dataSnapshot.getKey() + snap.getKey(), snap.getValue(String.class));
                    }
                }
                }catch (Exception e){

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
        return rett;
    }
    public static String downloadcomp(String ID, final Context c, MyCallback m) {
        //final AtomicBoolean done = new AtomicBoolean(false);
        //String init="Inital Value";
        //shared.set(init);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Lessons").child(ID);
        Log.d(String.valueOf(ID), String.valueOf(ID));
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
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

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
                        rett.put("prac" + "qs" + wr_num, wr);
                    }

                    wr_num++;
                    Log.d("wr_num", String.valueOf(wr_num));
                }
                m.onCallback("done");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("error", "Failed to read value.", error.toException());
            }
        });
        return "a";
    }

    public static HashMap<String, String> readqs(String id, String name, String qs_num, Context c) {
        HashMap<String, String> hashMap = new HashMap<>();
        try {
            String content = rett.get(id + "qs" + qs_num);
            String[] arr = content.split("\\]|\\[");
            Log.d("check", content.toString());
            try {
                hashMap.put("type", arr[1].replace("\\n", System.getProperty("line.separator")));
            } catch (Exception e) {
                try {
                    hashMap.put("type", arr[1]);
                } catch (Exception exception) {
                    CountDownTimer mCountdownTimer;
                    mCountdownTimer = new CountDownTimer(1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            hashMap.put("type", arr[1].replace("\\n", System.getProperty("line.separator")));
                        }
                    };
                }
            }
            try {
                hashMap.put("qs", arr[3].replace("\\n", System.getProperty("line.separator")).replaceAll("aaa", "[").replaceAll("bbb", "]"));
            } catch (Exception e) {
                hashMap.put("qs", arr[3]);
                throw new Exception("Exception message");
            }
            try {
                hashMap.put("Content", arr[5].replace("\\n", System.getProperty("line.separator")).replaceAll("aaa", "[").replaceAll("bbb", "]"));
            } catch (Exception e) {
                hashMap.put("Content", arr[5]);
            }
            try {
                hashMap.put("Image", arr[7].replace("\\n", System.getProperty("line.separator")));
            } catch (Exception e) {
                hashMap.put("Image", arr[7]);
            }
            try {
                hashMap.put("Answer", arr[9].replace("\\n", System.getProperty("line.separator")).replaceAll("aaa", "[").replaceAll("bbb", "]"));
            } catch (Exception e) {
                hashMap.put("Answer", arr[9]);
            }
            try {
                hashMap.put("additional", arr[11].replace("\\n", System.getProperty("line.separator")).replaceAll("aaa", "[").replaceAll("bbb", "]"));
            } catch (Exception e) {
                hashMap.put("additional", arr[11]);
            }

        } catch (Exception e) {

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
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
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
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("saluton", "saluton");
                // This method is called once with the initial value and again
                // whenever data at this location is uploaded
                try {
                    ret.put("geld", dataSnapshot.child("geld").getValue().toString());
                    ret.put("streak", dataSnapshot.child("streak").getValue().toString());
                    ret.put("maxStreak", dataSnapshot.child("maxStreak").getValue().toString());
                    ret.put("year", dataSnapshot.child("lastLessonD").child("year").getValue().toString());
                    ret.put("date", dataSnapshot.child("lastLessonD").child("date").getValue().toString());
                    ret.put("cProgress", dataSnapshot.child("progress").getValue().toString());//2
                    ret.put("shabes", dataSnapshot.child("shabes").getValue().toString());
                    ret.put("xp", dataSnapshot.child("xp").getValue().toString());
                    ret.put("pyXp", dataSnapshot.child("pyXp").getValue().toString());
                    ret.put("htmlXp", dataSnapshot.child("htmlXp").getValue().toString());
                    ret.put("ligaType", dataSnapshot.child("ligaType").getValue().toString());
                    ret.put("name", dataSnapshot.child("name").getValue().toString());
                    ret.put("img", dataSnapshot.child("imgUrl").getValue().toString());
                    ret.put("email", dataSnapshot.child("email").getValue().toString());
                    ret.put("hasDoneLesson", dataSnapshot.child("hasDoneLesson").getValue().toString());
                    ret.put("imgC", dataSnapshot.child("imgC").getValue().toString());
                    ret.put("streak freeze", dataSnapshot.child("streak freeze").getValue().toString());
                    ret.put("7streak", dataSnapshot.child("7streak").getValue().toString());
                    ret.put("friends", dataSnapshot.child("friends").getValue().toString());
                    ret.put("weekXp", dataSnapshot.child("weekXp").getValue().toString());
                } catch (Exception e){
                    try {
                        ret.put("name", dataSnapshot.child("name").getValue().toString());
                        ret.put("streak", dataSnapshot.child("streak").getValue().toString());
                        ret.put("maxStreak", "0");
                        ret.put("xp", dataSnapshot.child("xp").getValue().toString());
                        ret.put("year", "0");
                        ret.put("date", "0");
                        ret.put("cProgress", "0");//2
                        ret.put("pyXp", "0");
                        ret.put("htmlXp", "0");
                        ret.put("img", "0");
                        ret.put("email", "0");
                        ret.put("streak freeze", "0");
                        ret.put("imgC", dataSnapshot.child("imgC").getValue().toString());
                        ret.put("ligaType", "0");
                        ret.put("7streak", "0");
                        ret.put("friends", "0");
                        Log.d("error", e.getLocalizedMessage());
                    } catch (Exception ee){
                        try {
                            ret.put("name", dataSnapshot.child("name").getValue().toString());
                            ret.put("streak", dataSnapshot.child("streak").getValue().toString());
                            ret.put("maxStreak", "0");
                            ret.put("xp", dataSnapshot.child("xp").getValue().toString());
                            ret.put("year", "0");
                            ret.put("date", "0");
                            ret.put("cProgress", "0");//2
                            ret.put("pyXp", "0");
                            ret.put("htmlXp", "0");
                            ret.put("img", "0");
                            ret.put("email", "0");
                            ret.put("streak freeze", "0");
                            ret.put("imgC", "0");
                            ret.put("ligaType", "0");
                            ret.put("7streak", "0");
                            ret.put("friends", "0");
                            Log.d("error", e.getLocalizedMessage());
                        } catch (Exception eee){
                            ret.put("streak", "0");
                            ret.put("maxStreak", "0");
                            ret.put("year", "0");
                            ret.put("date", "0");
                            ret.put("cProgress", "0");//2
                            ret.put("xp", "0");
                            ret.put("pyXp", "0");
                            ret.put("htmlXp", "0");
                            ret.put("name", "0");
                            ret.put("img", "0");
                            ret.put("email", "0");
                            ret.put("streak freeze", "0");
                            ret.put("imgC", "0");
                            ret.put("7streak", "0");
                            ret.put("friends", "0");
                            Log.d("error2", eee.getLocalizedMessage());
                        }
                    }
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
    public static HashMap<String, String> get_sLast_lesson2(String email, HashCallback m) {
        final HashMap<String, String> ret = new HashMap<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users").child(email);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("saluton", "saluton");
                // This method is called once with the initial value and again
                // whenever data at this location is uploaded
                try {
                    ret.put("geld", dataSnapshot.child("geld").getValue().toString());
                    ret.put("streak", dataSnapshot.child("streak").getValue().toString());
                    ret.put("maxStreak", dataSnapshot.child("maxStreak").getValue().toString());
                    ret.put("year", dataSnapshot.child("lastLessonD").child("year").getValue().toString());
                    ret.put("date", dataSnapshot.child("lastLessonD").child("date").getValue().toString());
                    ret.put("cProgress", dataSnapshot.child("progress").getValue().toString());//2
                    ret.put("shabes", dataSnapshot.child("shabes").getValue().toString());
                    ret.put("xp", dataSnapshot.child("xp").getValue().toString());
                    ret.put("pyXp", dataSnapshot.child("pyXp").getValue().toString());
                    ret.put("htmlXp", dataSnapshot.child("htmlXp").getValue().toString());
                    ret.put("ligaType", dataSnapshot.child("ligaType").getValue().toString());
                    ret.put("name", dataSnapshot.child("name").getValue().toString());
                    ret.put("img", dataSnapshot.child("imgUrl").getValue().toString());
                    ret.put("email", dataSnapshot.child("email").getValue().toString());
                    ret.put("hasDoneLesson", dataSnapshot.child("hasDoneLesson").getValue().toString());
                    ret.put("imgC", dataSnapshot.child("imgC").getValue().toString());
                    ret.put("streak freeze", dataSnapshot.child("streak freeze").getValue().toString());
                    ret.put("7streak", dataSnapshot.child("7streak").getValue().toString());
                    ret.put("friends", dataSnapshot.child("friends").getValue().toString());
                    ret.put("weekXp", dataSnapshot.child("weekXp").getValue().toString());
                } catch (Exception e){
                    try {
                        ret.put("name", dataSnapshot.child("name").getValue().toString());
                        ret.put("streak", dataSnapshot.child("streak").getValue().toString());
                        ret.put("maxStreak", "0");
                        ret.put("xp", dataSnapshot.child("xp").getValue().toString());
                        ret.put("year", "0");
                        ret.put("date", "0");
                        ret.put("cProgress", "0");//2
                        ret.put("pyXp", "0");
                        ret.put("htmlXp", "0");
                        ret.put("img", "0");
                        ret.put("email", "0");
                        ret.put("streak freeze", "0");
                        ret.put("imgC", dataSnapshot.child("imgC").getValue().toString());
                        ret.put("ligaType", "0");
                        ret.put("7streak", "0");
                        ret.put("friends", "0");
                        Log.d("error", e.getLocalizedMessage());
                    } catch (Exception ee){
                        try {
                            ret.put("name", dataSnapshot.child("name").getValue().toString());
                            ret.put("streak", dataSnapshot.child("streak").getValue().toString());
                            ret.put("maxStreak", "0");
                            ret.put("xp", dataSnapshot.child("xp").getValue().toString());
                            ret.put("year", "0");
                            ret.put("date", "0");
                            ret.put("cProgress", "0");//2
                            ret.put("pyXp", "0");
                            ret.put("htmlXp", "0");
                            ret.put("img", "0");
                            ret.put("email", "0");
                            ret.put("streak freeze", "0");
                            ret.put("imgC", "0");
                            ret.put("ligaType", "0");
                            ret.put("7streak", "0");
                            ret.put("friends", "0");
                            Log.d("error", e.getLocalizedMessage());
                        } catch (Exception eee){
                            ret.put("streak", "0");
                            ret.put("maxStreak", "0");
                            ret.put("year", "0");
                            ret.put("date", "0");
                            ret.put("cProgress", "0");//2
                            ret.put("xp", "0");
                            ret.put("pyXp", "0");
                            ret.put("htmlXp", "0");
                            ret.put("name", "0");
                            ret.put("img", "0");
                            ret.put("email", "0");
                            ret.put("streak freeze", "0");
                            ret.put("imgC", "0");
                            ret.put("7streak", "0");
                            ret.put("friends", "0");
                            Log.d("error2", eee.getLocalizedMessage());
                        }
                    }
                }
                m.onCallback(ret);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Failed to read value.", error.toException());
                ret.put("cProgress", "0");//2
                m.onCallback(ret);
            }
        });
        return ret;
    }
    public static HashMap<String, ArrayList> get_liga(String email, HashCallback3 m) {
        final HashMap<String, ArrayList> ret = new HashMap<>();
        ArrayList names = new ArrayList();
        ArrayList xp = new ArrayList();
        ArrayList imgC = new ArrayList();
        ArrayList type = new ArrayList();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");
        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        DatabaseReference myRef2 = database2.getReference("ligot");
        Log.d("hallo", String.valueOf(myRef2.getParent()));

        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("saluton1", "saluton1");

                // This method is called once with the initial value and again
                // whenever data at this location is uploaded
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot2) {
                        for (DataSnapshot liga : dataSnapshot.getChildren()){
                            if (liga.getValue().toString().contains(email)){
                                int j = 0;
                                for (String i : liga.getValue().toString().split(",")) {
                                    try {
                                        names.add(i.split("-")[1]);
                                    } catch (Exception e){

                                    }
                                    //Log.d("saluton5" + " " + j, names.get(j));
                                    try {
                                        xp.add(Integer.parseInt(dataSnapshot2.child(i.split("-")[0]).child("weekXp").getValue().toString()));
                                        imgC.add(Integer.parseInt(dataSnapshot2.child(i.split("-")[0]).child("imgC").getValue().toString()));
                                    } catch (Exception e){

                                    }
                                    j = j + 1;
                                }
                                ret.put("names", names);
                                type.add(liga.getKey().charAt(0));
                                ret.put("type", type);
                                ret.put("imgC", imgC);
                                ret.put("xp", xp);
                                m.onCallback(ret);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("Failed to read value.", error.toException());
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Failed to read value.", error.toException());
            }
        });
        return ret;
    }
    public static HashMap<String, ArrayList> get_liga2(String email, HashCallback3 m) {
        final HashMap<String, ArrayList> ret = new HashMap<>();
        ArrayList names = new ArrayList();
        ArrayList xp = new ArrayList();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");
        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        DatabaseReference myRef2 = database2.getReference("ligot");
        Log.d("hallo", String.valueOf(myRef2.getParent()));

        myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("saluton1", "saluton1");

                // This method is called once with the initial value and again
                // whenever data at this location is uploaded
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot2) {
                        Log.d("saluton2", "saluton2");
                        for (DataSnapshot liga : dataSnapshot.getChildren()){
                            Log.d("saluton3", liga.getValue().toString());
                            if (liga.getKey().contains(email)){
                                Log.d("saluton4", "saluton3");
                                int j = 0;
                                for (String i : liga.getValue().toString().split(",")) {
                                    try {
                                        names.add(i);
                                    } catch (Exception e){

                                    }
                                    //Log.d("saluton5" + " " + j, names.get(j));
                                    try {
                                        xp.add(Integer.parseInt(dataSnapshot2.child(i.split("-")[0]).child("xp").getValue().toString()));
                                    } catch (Exception e){

                                    }
                                    j = j + 1;
                                }
                                ret.put("names", names);
                                ret.put("xp", xp);
                                m.onCallback(ret);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("Failed to read value.", error.toException());
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Failed to read value.", error.toException());
            }
        });
        return ret;
    }
    public static HashMap<String, ArrayList> get_liga3(HashCallback3 m) {
        final HashMap<String, ArrayList> ret = new HashMap<>();
        ArrayList names = new ArrayList();
        ArrayList xp = new ArrayList();
        ArrayList key = new ArrayList();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");
        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        DatabaseReference myRef2 = database2.getReference("ligot");
        Log.d("hallo", String.valueOf(myRef2.getParent()));

        myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("saluton1", "saluton1");

                // This method is called once with the initial value and again
                // whenever data at this location is uploaded
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot2) {
                        Log.d("saluton2", "saluton2");
                        for (DataSnapshot liga : dataSnapshot.getChildren()){
                            Log.d("saluton3", liga.getValue().toString());
                            if ( liga.getValue().toString().contains("bot") && liga.getKey().toString().startsWith("1")){
                                Log.d("saluton4", "saluton3");
                                int j = 0;
                                for (String i : liga.getValue().toString().split(",")) {
                                    try {
                                        names.add(i);
                                        key.add(liga.getKey().toString());
                                    } catch (Exception e){

                                    }
                                    //Log.d("saluton5" + " " + j, names.get(j));
                                    try {
                                        xp.add(Integer.parseInt(dataSnapshot2.child(i.split("-")[0]).child("xp").getValue().toString()));
                                    } catch (Exception e){

                                    }
                                    j = j + 1;
                                }
                                ret.put("names", names);
                                ret.put("xp", xp);
                                ret.put("key", key);
                                m.onCallback(ret);
                            }
                            else {
                                ret.put("names", names);
                                ret.put("xp", xp);
                                m.onCallback(ret);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("Failed to read value.", error.toException());
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Failed to read value.", error.toException());
            }
        });
        return ret;
    }

    public static HashMap<String, ArrayList<String>> get_docs(String email, HashCallback2 m) {
        final HashMap<String, ArrayList<String>> ret = new HashMap<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users").child(email).child("docs");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is uploaded
                try {
                    ArrayList<String> docsNames = new ArrayList<String>();
                    ArrayList<String> docsVals = new ArrayList<String>();
                    for (DataSnapshot i : dataSnapshot.getChildren()){
                        docsNames.add(i.getKey());
                        for (DataSnapshot j : dataSnapshot.child(i.getKey()).getChildren()){
                            docsVals.add(j.getValue().toString());
                        }
                    }
                    ret.put("names", docsNames);
                    ret.put("vals", docsVals);

                } catch (Exception e){
                    ArrayList<String> docsNames = new ArrayList<String>();
                    ArrayList<String> docsVals = new ArrayList<String>();
                    ret.put("names",docsNames);
                    ret.put("vals",docsVals);
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

    public static List<String> get_emails(ListCallback m) {
        final List<String> Address;
        Address = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot fire_email : dataSnapshot.getChildren()) {
                    try {
                        Address.add(fire_email.child("id").getValue().toString());
                    } catch (Exception e){

                    }
                }
                m.onCallback(Address);

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
