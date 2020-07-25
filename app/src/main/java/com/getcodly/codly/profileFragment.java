package com.getcodly.codly;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class profileFragment extends Fragment {

    ListView listView;
    ImageView profImg;
    TextView setName;
    public static ArrayList<String> list;
    String url_old;
    boolean b = false;
    String streak;
    public static String name;
    private GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;
    Button toSearch;
    HashMap<String, String> old_streak;
    TextView setStreak;
    public static HashMap<String, String> ret;
    ImageButton profileSettingsBtn;
    private FriendsFragment friendsFragment;

    ViewPager viewPager;
    TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile2, container, false);
        Log.d("fragmentTest", "savta2");

        mAuth = FirebaseAuth.getInstance();
        tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        profileSettingsBtn = (ImageButton) v.findViewById(R.id.profileSettings);
        //listView = (ListView) v.findViewById(R.id.viewPager);
        profImg = (ImageView) v.findViewById(R.id.imageView2);
        setStreak = (TextView) v.findViewById(R.id.streakView);
        setName = (TextView) v.findViewById(R.id.set_name);
        viewPager = v.findViewById(R.id.viewPager);
        viewPager = v.findViewById(R.id.viewPager);

        friendsFragment = new FriendsFragment();



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        profileSettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsFragment nextFrag= new SettingsFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(((ViewGroup)getView().getParent()).getId(), nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });


        try {
            old_streak = DownloadReadlessons.get_last_lesson2(ReadWrite.read(getActivity().getFilesDir() + File.separator + "user"), new DownloadReadlessons.HashCallback() {
                @Override
                public void onCallback(HashMap value) {
                    if (!b) {
                        b = true;
                        name = (String) value.get("name");
                        streak = mainScreen.streak;
                        url_old = (String) value.get("img");
                        setName.setText(name);
                        setStreak.setText(streak);
                        ret = new HashMap<>();
                        int i = 0;
                        list = new ArrayList<String>();
                        try {
                            Picasso.with(getActivity()).load(url_old).resizeDimen(R.dimen.image_size, R.dimen.image_size).placeholder(R.drawable.goj).into(profImg);
                        } catch (Exception e) {
                            profImg.setImageResource(R.drawable.user_pic);
                        }
                        for (String friend : value.get("friends").toString().split("-")) {
                            try {
                                list.add(friend.split("/")[1]);
                                ret.put(String.valueOf(i), friend.split("/")[0]);
                                Log.d("hi", friend.split("/")[0]);
                                i++;
                            } catch (Exception e) {

                            }
                        }
                        String[] stringArray = list.toArray(new String[list.size()]);
                        try {
                            ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, stringArray);
                        }catch (Exception e){

                        }
                        //listView.setAdapter(itemsAdapter);
                    }
                }
            });
        }
        catch (Exception e){

        }

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                Log.i("HelloListView", "You clicked Item: " + "id" + " at position:" + position);
                Search.selected = ret.get(String.valueOf(position));
                name = list.get(position);
                Intent in = new Intent(getActivity(), FriendProfile.class);
                startActivity(in);
            }
        });
        backToTree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), mainScreen.class);
                startActivity(intent);
            }
        });
        toSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Search.class);
                startActivity(intent);
            }
        });*/


        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUpViewPager(ViewPager viewPager) {
        SectionPageAdapter adapter = new SectionPageAdapter(getChildFragmentManager());

        adapter.addFragment(new FriendsFragment(), "חברים");
        adapter.addFragment(new ProjectsFragment(), "פרוייקטים");

        viewPager.setAdapter(adapter);
    }
}

