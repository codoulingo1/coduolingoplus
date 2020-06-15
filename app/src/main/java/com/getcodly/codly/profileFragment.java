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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    public static String name;
    CountDownTimer mcountdown;
    private GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;
    Button toSearch;
    Button backToTree;
    Button ןמהןאקף;
    HashMap<String, String> old_streak;
    TextView setStreak;
    public static HashMap<String, String> ret;
    Button changeName;
    EditText cgName;
    boolean changed = false;
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
        profImg = (ImageView) v.findViewById(R.id.imageView2);
        listView = (ListView) v.findViewById(R.id.friendsList);
        setStreak = (TextView) v.findViewById(R.id.streak);
        setName = (TextView) v.findViewById(R.id.set_name);
        backToTree =  (Button) v.findViewById(R.id.back_to_tree);
        toSearch =  (Button) v.findViewById(R.id.addFriend);
        listView = (ListView) v.findViewById(R.id.friendsList);
        viewPager = v.findViewById(R.id.viewPager);

        friendsFragment = new FriendsFragment();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        old_streak = DownloadReadlessons.get_last_lesson(ReadWrite.read(getActivity().getFilesDir()+File.separator+ "user"));
        String idp = ReadWrite.read(getActivity().getFilesDir()+ File.separator + "user");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users").child(idp);
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

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                url_old = dataSnapshot.child("imgUrl").getValue(String.class);//paste here google drive picture shareable link but change "open?" to "uc?"
                Log.d("profile_Activity", url_old);
                name = dataSnapshot.child("name").getValue(String.class);

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
                try {
                    Picasso.with(getActivity()).load(url_old).resizeDimen(R.dimen.image_size, R.dimen.image_size).placeholder(R.drawable.goj).into(profImg);
                } catch (Exception e) {
                    profImg.setImageResource(R.drawable.user_pic);
                }
                try {
                    setName.setText(name);
                    ret = new HashMap<>();
                    int i = 0;
                    list = new ArrayList<String>();
                    for (String friend : old_streak.get("friends").split("-")) {
                        try {
                            list.add(friend.split("/")[1]);
                            ret.put(String.valueOf(i), friend.split("/")[0]);
                            Log.d("hi", friend.split("/")[0]);
                            i++;
                        } catch (Exception e) {

                        }
                    }
                    String[] stringArray = list.toArray(new String[list.size()]);
                    ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, stringArray);
                    listView.setAdapter(itemsAdapter);
                    int year = Integer.parseInt(old_streak.get("year"));
                    int month = Integer.parseInt(old_streak.get("month"));
                    int day = Integer.parseInt(old_streak.get("date"));
                    Calendar calendar = Calendar.getInstance();

                    // Move calendar to yesterday
                    calendar.add(Calendar.DATE, -1);

                    // Get current date of calendar which point to the yesterday now
                    int yesterday = calendar.get(Calendar.DATE);
                    Calendar calendar_3 = Calendar.getInstance();

                    // Move calendar to yesterday
                    calendar_3.add(Calendar.DATE, -2);

                    // Get current date of calendar which point to the yesterday now
                    int bf = calendar_3.get(Calendar.DATE);
                    Calendar calendar2 = Calendar.getInstance();
                    int today = calendar2.get(Calendar.DATE);
                    Log.d("hello", String.valueOf(today));
                    if (year == calendar.get(Calendar.YEAR) - 1900) {
                        Log.d("1", "1");
                        if (month == calendar.get(Calendar.MONTH)) {
                            Log.d("2", "2");
                            if (day == yesterday) {
                                Log.d("3", "3");
                                setStreak.setText("Streak: " + String.valueOf(Integer.parseInt(old_streak.get("streak"))));
                            } else if (day == today) {
                                Log.d("3", "3");
                                setStreak.setText("Streak: " + String.valueOf(old_streak.get("streak")));

                            } else if (day == bf && old_streak.get("streak freeze").equals("true")) {
                                setStreak.setText("Streak: " + String.valueOf(old_streak.get("streak")));
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("Users").child(ReadWrite.read(getActivity().getFilesDir() + File.separator + "user"));
                                myRef.child("lastLessonD").child("year").setValue(calendar.get(Calendar.YEAR) - 1900);
                                myRef.child("lastLessonD").child("month").setValue(calendar.get(Calendar.MONTH));
                                myRef.child("lastLessonD").child("date").setValue(calendar.get(Calendar.DATE));
                                myRef.child("streak freeze").setValue("false");

                            } else {
                                Log.d("3", "3");
                                setStreak.setText("Streak: 0");
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("Users").child(ReadWrite.read(getActivity().getFilesDir() + File.separator + "user"));
                                myRef.child("streak freeze").setValue("false");
                                myRef.child("streak").setValue(0);
                            }
                        }
                    }
                }
                catch (Exception e){

                }
            }
        }.start();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
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
        });


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
