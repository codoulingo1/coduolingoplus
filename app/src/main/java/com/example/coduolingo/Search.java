package com.example.coduolingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Search extends AppCompatActivity {
    CountDownTimer mcountdown;
    Handler handler;
    ListView listView;
    HashMap<String, String> hashMap;
    EditText ed;
    public static String name;
    int i;
    public static String selected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ed = (EditText) findViewById(R.id.editText);
        listView = (ListView) findViewById(R.id.list);
        handler = new Handler();
        final int delay = 500; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){
                i = 0;
                hashMap = new HashMap<>();
                final List<String> names;
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Users");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        for (DataSnapshot fire_email : dataSnapshot.getChildren()) {
                            if(fire_email.child("name").getValue(String.class).equals(ed.getText().toString())){
                                hashMap.put(String.valueOf(i), fire_email.getKey());
                                i++;
                            }

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("Failed to read value.", error.toException());
                    }
                });
                mcountdown = new CountDownTimer(1000, 1000) {
                    @Override
                    public void onTick(long l) {
                        //dialog.show();
                        Log.d("Loading", "Loading");
                    }

                    @Override
                    public void onFinish() {
                        //Creating a HashMap object

                        //Getting Collection of values from HashMap

                        Collection<String> values = hashMap.values();

                        //Creating an ArrayList of values

                        ArrayList<String> listOfValues = new ArrayList<String>(values);

                        // Convert ArrayList to Array

                        String stringArray[]=listOfValues.toArray(new String[listOfValues.size()]);
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
                name = ed.getText().toString();
                Intent in = new Intent(getApplicationContext(), FriendProfile.class);
                startActivity(in);
            }
        });
    }

}
