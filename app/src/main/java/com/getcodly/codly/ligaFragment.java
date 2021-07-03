package com.getcodly.codly;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ExecutorCompletionService;

import cn.iwgang.countdownview.CountdownView;


public class ligaFragment extends Fragment {
    public ligaFragment() {
        // Required empty public constructor
    }

    HashMap<String, ArrayList> data;
    TextView ligaText;
    ListView simpleList;
    ImageView leagueImg;

    CountdownView mCountdownView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_liga, container, false);
        simpleList = (ListView)v.findViewById(R.id.friendList2);
        leagueImg = (ImageView)v.findViewById(R.id.leagueImg);
        mCountdownView = v.findViewById(R.id.countDown);

        Calendar calendar = Calendar.getInstance();
        int weekday = calendar.get(Calendar.DAY_OF_WEEK);
        int days = Calendar.SUNDAY - weekday;
        if (days <= 0)
        {
            days += 7;
        }
        calendar.add(Calendar.DAY_OF_YEAR, days);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 1);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);


        Date now = new Date();
        long currentDate = now.getTime();
        long targetDate = calendar.getTimeInMillis();
        long countDownToPickerDate = targetDate - currentDate;

        mCountdownView.start(countDownToPickerDate);

        data = DownloadReadlessons.get_liga(mainScreen.uId, new DownloadReadlessons.HashCallback3() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCallback(HashMap<String, ArrayList> value) {
                //Log.d("LigaVals", value.get("names").get(2));
                ArrayList<String> alt_name = value.get("names");
                ligaText = v.findViewById(R.id.ligaText);
                String type = value.get("type").get(0).toString();
                Log.d("type", type);
                if (type.contains("1")){
                    ligaText.setText("ליגת נחושת");
                    leagueImg.setImageResource(R.drawable.ic_league1);
                }
                else if (type.contains("2")){
                    ligaText.setText("ליגת כסף");
                    leagueImg.setImageResource(R.drawable.ic_league2);
                }
                else if (type.contains("3")){
                    ligaText.setText("ליגת זהב");
                    leagueImg.setImageResource(R.drawable.ic_league3);
                }
                else if (type.contains("4")){
                    ligaText.setText("ליגת אמרלד");
                    leagueImg.setImageResource(R.drawable.ic_league4);
                }
                else if (type.contains("5")){
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
                        try {
                            name.add(alt_name.get(loc));
                            imgC.add(alt_img.get(loc));
                        } catch (Exception e){

                        }
                        xpp.add(i);
                        alt_xp.set(loc, -1);
                    }
                    ii++;
                }
                try {
                    CustomAdapter customAdapter = new CustomAdapter(requireActivity().getApplicationContext(), name, xpp, mainScreen.name, imgC, type);
                    simpleList.setAdapter(customAdapter);
                } catch (Exception e){

                }

            }
        });
        return v;
    }
}