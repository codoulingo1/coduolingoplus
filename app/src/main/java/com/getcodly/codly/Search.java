package com.getcodly.codly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Search extends AppCompatActivity {
    CountDownTimer mcountdown;
    Handler handler;
    ListView listView;
    HashMap<String, String> hashMap;
    SearchView ed;
    public static String name;
    int i;
    public static String selected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ed = (SearchView) findViewById(R.id.editText);
        listView = (ListView) findViewById(R.id.list);
        handler = new Handler();
        final int delay = 500; //milliseconds
        ed.setIconified(false);

        handler.postDelayed(new Runnable(){
            public void run(){
                i = 0;
                hashMap = new HashMap<>();
                final ArrayList names;
                names = new ArrayList<>();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Users");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        for (DataSnapshot fire_email : dataSnapshot.getChildren()) {
                            try {
                                if(fire_email.child("name").getValue(String.class).toLowerCase().contains(ed.getQuery().toString().toLowerCase())){
                                    names.add(fire_email.child("name").getValue(String.class));
                                    hashMap.put(String.valueOf(i), fire_email.getKey());
                                    i++;
                                }
                            } catch (Exception e) {

                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("Failed to read value.", error.toException());
                    }
                });
                mcountdown = new CountDownTimer(1000, 100) {
                    @Override
                    public void onTick(long l) {
                        //dialog.show();
                        Log.d("Loading", "Loading");
                    }

                    @Override
                    public void onFinish() {
                        //Creating a HashMap object

                        //Getting Collection of values from HashMap

                        Collection<String> values = names;

                        //Creating an ArrayList of values

                        ArrayList<String> listOfValues = new ArrayList<String>(values);

                        // Convert ArrayList to Array

                        String stringArray[] = listOfValues.toArray(new String[listOfValues.size()]);
                        ArrayAdapter<String> itemsAdapter =
                                new ArrayAdapter<String>(Search.this, android.R.layout.simple_list_item_1, android.R.id.text1,  stringArray);
                        listView.setAdapter(itemsAdapter);
                    }
                }.start();
                        handler.postDelayed(this, delay);
            }
        }, delay);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                Log.i("HelloListView", "You clicked Item: " + "id" + " at position:" + position);
                selected = hashMap.get(String.valueOf(position));
                Log.d("hi", selected);
                name = ed.getQuery().toString();
                Intent in = new Intent(getApplicationContext(), FriendProfile.class);
                startActivity(in);
            }
        });//
    }

}
