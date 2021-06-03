package com.getcodly.codly;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.time.LocalDate;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.temporal.TemporalAdjusters.next;
import static java.time.temporal.TemporalAdjusters.previous;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Ordering;

import java.io.File;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjuster;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class LigaActivity extends AppCompatActivity {
    HashMap<String, ArrayList> data;
    TextView ligaText;
    ListView simpleList;
    ImageView leagueImg;

    Calendar currentDate;
    Calendar endingDate;

    //Context context = getApplicationContext();
    //final public static Typeface font = Typeface.createFromAsset(context.getAssets(), "rubik_bold.xml");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liga);
        simpleList = (ListView)findViewById(R.id.friendList2);
        leagueImg = (ImageView)findViewById(R.id.leagueImg);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final LocalDate today = LocalDate.of(ZonedDateTime.now().getYear(), ZonedDateTime.now().getMonth(), ZonedDateTime.now().getDayOfMonth());
            final LocalDate nextSunday = today.with(next(SUNDAY));

        }

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
                    leagueImg.setImageResource(R.drawable.ic_league2);
                }
                else if (type.contains("2")){
                    ligaText.setText("ליגת זהב");
                    leagueImg.setImageResource(R.drawable.ic_league3);
                }
                else if (type.contains("3")){
                    ligaText.setText("ליגת יהלום");
                    leagueImg.setImageResource(R.drawable.ic_league5);
                }
                ArrayList<String> name = new ArrayList<String>();
                ArrayList<Integer> xp = new ArrayList<Integer>(value.get("xp"));
                ArrayList<Integer> alt_img = new ArrayList<Integer>(value.get("imgC"));
                ArrayList<Integer> xpp = new ArrayList<Integer>();
                ArrayList<Integer> imgC = new ArrayList<Integer>();
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
                        name.add(alt_name.get(loc));
                        try {
                            imgC.add(alt_img.get(loc));
                        } catch (Exception e){

                        }
                        xpp.add(i);
                        alt_xp.set(loc, -1);
                    }
                    ii++;
                }
                CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), name, xpp, mainScreen.name, imgC);

                simpleList.setAdapter(customAdapter);
                Log.d("salvete", String.valueOf(name));
                ;
            }
        });
    }
}
