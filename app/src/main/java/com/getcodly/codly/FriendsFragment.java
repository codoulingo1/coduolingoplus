package com.getcodly.codly;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends Fragment {

    public FriendsFragment() {
        // Required empty public constructor
    }

    ListView listView;
    ArrayList list;
    HashMap ret;
    String name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_friendlist, container, false);
        listView = (ListView) v.findViewById(R.id.friendList2);
        CountDownTimer mcountdown = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                try {
                    list = profileFragment.list;
                    String stringArray[] = (String[]) list.toArray(new String[list.size()]);
                    ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, stringArray);
                    listView.setAdapter(itemsAdapter);
                    ret = profileFragment.ret;
                    name = profileFragment.name;
                } catch (Exception e){
                    Log.d("Crash", "It did not crash");
                }
            }
        }.start();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                Log.i("HelloListView", "You clicked Item: " + "id" + " at position:" + position);
                Search.selected = (String) ret.get(String.valueOf(position));
                name = (String) list.get(position);
                Intent in = new Intent(getActivity(), FriendProfile.class);
                startActivity(in);
            }
        });

        return v;
    }
}
