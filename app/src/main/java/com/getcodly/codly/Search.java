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
    HashMap <String, String> f;
    boolean b = false;
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
        DownloadReadlessons.get_names(new DownloadReadlessons.HashCallback() {
                                          @Override
                                          public void onCallback(HashMap<String, String> value) {
                                                b = true;
                                                f = value;
                                          }
                                      }
        );


        ed.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Do whatever you need. This will be fired only when submitting.
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Do whatever you need when text changes.
                // This will be fired every time you input any character.
                        if (b) {
                            i = 0;
                            hashMap = new HashMap<>();
                            final ArrayList names;
                            names = new ArrayList<>();
                            if (newText.equals("אני יונצי אני יונצי")){
                                yonziDialog yonziDialog = new yonziDialog();
                                yonziDialog.show(getSupportFragmentManager(), "name dialog");
                            }
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            for (String fire_email : f.keySet()) {
                                try {
                                    if (fire_email.toLowerCase().contains(ed.getQuery().toString().toLowerCase())) {
                                        names.add(fire_email);
                                        hashMap.put(String.valueOf(i), f.get(fire_email));
                                        i++;
                                    }
                                } catch (Exception e) {

                                }
                            }

                            //Creating a HashMap object

                            //Getting Collection of values from HashMap

                            Collection<String> values = names;

                            //Creating an ArrayList of values

                            ArrayList<String> listOfValues = new ArrayList<String>(values);

                            // Convert ArrayList to Array

                            String stringArray[] = listOfValues.toArray(new String[listOfValues.size()]);
                            ArrayAdapter<String> itemsAdapter =
                                    new ArrayAdapter<String>(Search.this, android.R.layout.simple_list_item_1, android.R.id.text1, stringArray);
                            listView.setAdapter(itemsAdapter);
                            //handler.postDelayed(this, delay);
                        }
                return false;
            }
        });

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