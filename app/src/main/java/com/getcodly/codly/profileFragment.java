package com.getcodly.codly;

import android.content.Intent;
import android.graphics.Color;
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
import android.widget.ProgressBar;
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
    private FriendsFragment friendsFragment;

    ProgressBar levelProgress;
    ViewPager viewPager;
    TabLayout tabLayout;
    TextView levelView;
    TextView xpView;
    TextView goalText;
    ProgressBar courseProgressbarWeb;
    TextView percentageProgress1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile2, container, false);
        Log.d("fragmentTest", "savta2");

        mAuth = FirebaseAuth.getInstance();
        levelProgress = v.findViewById(R.id.levelProgress);
        tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        //listView = (ListView) v.findViewById(R.id.viewPager);
        profImg = (ImageView) v.findViewById(R.id.imageView2);
        setStreak = (TextView) v.findViewById(R.id.streakView);
        setName = (TextView) v.findViewById(R.id.set_name);
        viewPager = v.findViewById(R.id.viewPager);
        viewPager = v.findViewById(R.id.viewPager);
        levelView = v.findViewById(R.id.level);
        xpView = v.findViewById(R.id.xpCount);
        goalText = v.findViewById(R.id.goalText);
        courseProgressbarWeb = v.findViewById(R.id.courseProgressWeb);
        percentageProgress1 = v.findViewById(R.id.percentageProgress1);

        setLevel();
        courseProgress();



        friendsFragment = new FriendsFragment();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);


        try {
            if (!b) {
                b = true;
                name = mainScreen.name;
                streak = mainScreen.streak;
                url_old = mainScreen.img;
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
                for (String friend : mainScreen.friends.split("-")) {
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

    void setLevel(){
        if(mainScreen.user_xp > 525){
            levelView.setText("רמה 8");
            goalText.setText("∞");
            goalText.setTextColor(Color.parseColor("#47C5A0"));
            levelProgress.setProgress(100);
        } else if(mainScreen.user_xp > 400){
            levelView.setText("רמה 7");
            goalText.setText("525");
            levelProgress.setProgress((mainScreen.user_xp - 400)*100 / (525 - 400));
        } else if(mainScreen.user_xp > 300){
            levelView.setText("רמה 6");
            goalText.setText("400");
            levelProgress.setProgress((mainScreen.user_xp - 300)*100 / (400 - 300));
        } else if(mainScreen.user_xp > 150){
            levelView.setText("רמה 5");
            goalText.setText("300");
            levelProgress.setProgress((mainScreen.user_xp - 150)*100 / (300 - 150));
        } else if(mainScreen.user_xp > 100){
            levelView.setText("רמה 4");
            goalText.setText("150");
            levelProgress.setProgress((mainScreen.user_xp - 100)*100 / (150 - 100));
        } else if(mainScreen.user_xp > 60){
            levelView.setText("רמה 3");
            goalText.setText("100");
            levelProgress.setProgress((mainScreen.user_xp - 60)*100 / (100 - 60));
        } else if(mainScreen.user_xp > 30){
            levelView.setText("רמה 2");
            goalText.setText("60");
            levelProgress.setProgress((mainScreen.user_xp - 30)*100 / (60 - 30));
        } else if(mainScreen.user_xp <= 30){
            levelView.setText("רמה 1");
            goalText.setText("30");
            levelProgress.setProgress(mainScreen.user_xp*100 / 30);
        }

        xpView.setText(String.valueOf(mainScreen.user_xp));
    }

    void courseProgress(){
        courseProgressbarWeb.setProgress(mainScreen.courseProgressWeb * 100 / 8);
        percentageProgress1.setText(String.valueOf(mainScreen.courseProgressWeb * 100 / 8) + "%");

    }
}

