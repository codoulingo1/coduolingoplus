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
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;



/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectsPrFragments extends Fragment {

    public ProjectsPrFragments() {
        // Required empty public constructor
    }

    ListView listView;
    ArrayList list;
    ArrayList<String> names;
    ArrayList<String> vals;
    HashMap ret;
    String name;
    TextView addFriend;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_projects_pr_fragments, container, false);
        listView = (ListView) v.findViewById(R.id.friendList2);
        addFriend = (TextView) v.findViewById(R.id.addFriendBtn);
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), selectProject.class);
                startActivity(intent);
            }
        });
        try {
            HashMap d = DownloadReadlessons.get_docs(ReadWrite.read(getActivity().getFilesDir() + File.separator + "user"), new DownloadReadlessons.HashCallback2() {
                @Override
                public void onCallback(HashMap<String, ArrayList<String>> value) {
                    try {
                        Log.d("hihihi", value.get("vals").get(0));
                        String[] stringArray = value.get("names").toArray(new String[value.get("names").size()]);
                        names = value.get("names");
                        vals = value.get("vals");
                        list = names;
                        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, stringArray);
                        listView.setAdapter(itemsAdapter);
                        int iInt = 0;
                    }catch (Exception e){

                    }
                }
            });
            justifyListViewHeightBasedOnChildren(listView);
            ret = profileFragment.ret;
            name = profileFragment.name;
        } catch (Exception e){
            Log.d("Crash", e.getLocalizedMessage());
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                Log.i("HelloListView", "You clicked Item: " + "id" + " at position:" + position);
                Log.d("deus",profileFragment.names.get(position));
                FriendProfile.isOUser = true;
                String prType = profileFragment.vals.get(((position+1)*3)-1);
                if (prType.equals("html")) {
                    selectProject.codeToLoad = profileFragment.vals.get(((position+1)*3)-3);
                    Intent in = new Intent(getActivity(), iframe2.class);
                    startActivity(in);
                }
                if (prType.equals("py")) {
                    selectPyProject.pyCodeToLoad = profileFragment.vals.get(((position+1)*3)-3);
                    selectProject.codeToLoad = profileFragment.vals.get(((position+1)*3)-3);
                    Intent in = new Intent(getActivity(), PythonActivity2.class);
                    startActivity(in);
                }
            }
        });

        return v;
    }

    public void justifyListViewHeightBasedOnChildren (ListView listView) {

        ListAdapter adapter = listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }

}
