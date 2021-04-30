package com.getcodly.codly;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Ordering;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class LigaActivity extends AppCompatActivity {
    HashMap<String, ArrayList> data;
    TextView ligaText;
    ListView simpleList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_liga);
        simpleList = (ListView)findViewById(R.id.friendList2);
        data = DownloadReadlessons.get_liga(ReadWrite.read(LigaActivity.this.getFilesDir() + File.separator + "user"), new DownloadReadlessons.HashCallback3() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCallback(HashMap<String, ArrayList> value) {
                //Log.d("LigaVals", value.get("names").get(2));
                ArrayList<String> alt_name = value.get("names");
                ligaText = findViewById(R.id.ligaText);
                String type = value.get("type").get(0).toString();
                if (type.contains("1")){
                    ligaText.setText("ליגת כסף");
                }
                else if (type.contains("2")){
                    ligaText.setText("ליגת זהב");
                }
                else if (type.contains("3")){
                    ligaText.setText("ליגת יהלום");
                }
                ArrayList<String> name = new ArrayList<String>();
                ArrayList<Integer> xp = new ArrayList<Integer>(value.get("xp"));
                Log.d("h", xp.toString());
                Log.d("hh", alt_name.toString());
                ArrayList<Integer> alt_xp = value.get("xp");
                Collections.sort(xp, Collections.reverseOrder());
                int ii = 0;
                for (int i : xp){
                    int loc = alt_xp.indexOf(i);
                    Log.d(alt_xp.toString(), "g");
                    Log.d("loc", String.valueOf(loc));
                    if (i != -1) {
                        name.add(alt_name.get(loc) + "    " + i);
                        alt_xp.set(loc, -1);
                    }
                    ii++;
                }
                ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(LigaActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, name);
                simpleList.setAdapter(itemsAdapter);
                Log.d("salvete", String.valueOf(name));
                ;
            }
        });
    }
}
