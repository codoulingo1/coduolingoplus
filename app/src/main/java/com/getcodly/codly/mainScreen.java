package com.getcodly.codly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class mainScreen extends AppCompatActivity {

    HashMap<String, String> date;
    public static String streak;
    public static String maxStreak;
    public static String streak7;
    public static String w = "c";
    public static String invName;
    public static int lessonWr = 0;
    public static String name;
    public static String userId = "";
    public static String sel;
    public static int pyXp;
    public static int htmlXp;
    public static boolean isDownload = false;
    public static String img;
    public static int imgC;
    public static String friends;
    public static String progress;
    FirebaseAuth mAuth;
    public static String LessonType = "";
    public static TextView geldView;
    public static Fragment selectedFragment = null;
    ImageButton settingsButton;
    public static String inLessonName;
    TextView setStreak;
    public static int Geld = 0;
    FirebaseDatabase database1;
    DatabaseReference user;
    public static int user_xp;
    public static int courseProgressPython = 0;
    public static int courseProgressWeb = 0;
    public static boolean run;
    private DatabaseReference mDatabase;

    private TextView toShop;

    private RelativeLayout fireBar;
    private RelativeLayout coinBar;

    private RelativeLayout fireTopSheet;
    private RelativeLayout geldTopSheet;
    private View pageCover;

    private TextView streakSheetNumber;
    private TextView lessonsDone;
    private TextView bestStreak;
    private boolean isTopSheetVisible;
    private boolean isGeldSheetVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        isTopSheetVisible = false;
        isGeldSheetVisible = false;

        courseProgressWeb = 0;
        courseProgressPython = 0;

        database1 = FirebaseDatabase.getInstance();
        user = database1.getReference("Users").child(ReadWrite.read(mainScreen.this.getFilesDir() + File.separator + "user"));

        mDatabase = FirebaseDatabase.getInstance().getReference();

        toShop = findViewById(R.id.enterShopBtn);
        coinBar = findViewById(R.id.coinBar);
        fireBar = findViewById(R.id.fireBar);
        fireTopSheet = findViewById(R.id.fireTopSheet);
        geldTopSheet = findViewById(R.id.geldTopSheet);
        pageCover = findViewById(R.id.pageCover1);
        streakSheetNumber = findViewById(R.id.streakSheetNumber);
        lessonsDone = findViewById(R.id.lessonsDone);
        bestStreak = findViewById(R.id.bestStreak);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(mainScreen.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                try {
                    DownloadReadlessons.get_emails(new DownloadReadlessons.ListCallback() {
                        @Override
                        public void onCallback(List<String> value) {
                            try {
                            Login.emails = value;
                            String personName = Login.account.getDisplayName();
                            String personGivenName = Login.account.getGivenName();
                            String personFamilyName = Login.account.getFamilyName();
                            String personEmail = Login.account.getEmail();
                            String personId = Login.account.getId();
                            Uri personPhoto = Login.account.getPhotoUrl();
                            if (!Login.emails.contains(Login.account.getId())) {
                                user.child("id").setValue(Login.account.getId());
                                user.child("email").setValue(personEmail);
                                user.child("imgUrl").setValue(personPhoto.toString());
                                user.child("name").setValue(personName);
                                user.child("phoneNum").setValue("");
                                user.child("lastLessonD").child("year").setValue(0);
                                user.child("lastLessonD").child("date").setValue(0);
                                user.child("streak").setValue(0);
                                user.child("maxStreak").setValue(0);//
                                user.child("streak freeze").setValue("false");
                                user.child("7streak").setValue(0);
                                user.child("xp").setValue(0);
                                Log.d("Create", "dir");
                                user.child("weekXp").setValue(0);
                                user.child("pyXp").setValue(0);
                                user.child("htmlXp").setValue(0);
                                user.child("shabes").setValue("false");
                                user.child("progress").setValue(Text.getRandomString(5));
                                user.child("start_comp").setValue("");
                                user.child("hasDoneLesson").setValue(false);
                                ColorGenerator generator = ColorGenerator.MATERIAL;
                                int color1 = generator.getRandomColor();
                                user.child("imgC").setValue(color1);
                                user.child("comp_w").setValue("");
                                user.child("comp").setValue("");
                                user.child("ligaType").setValue(1);
                                user.child("comp_time").setValue("1");
                                user.child("friends").setValue("");
                            }
                            }catch (Exception e){

                            }
                            String newToken = instanceIdResult.getToken();
                            Log.e("newToken", newToken);
                            user.child("token").setValue(newToken); //hi
                        }
                    });
                }catch (Exception e){
                    Log.e("!newToken", "a");
                }

            }
        });


        FirebaseMessaging.getInstance().subscribeToTopic("pushNotifications");

        LessonActivity.j = 1;
        lessonWr = 0;
        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(mainScreen.this ));
        }

        setStreak = findViewById(R.id.setStreak);

        settingsButton = findViewById(R.id.profileSettings);
        geldView = findViewById(R.id.geldView);





        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsFragment settingsFragment = new SettingsFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerMain, settingsFragment).commit();
            }
        });

        toShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShopFragment shopFragment = new ShopFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerMain, shopFragment).commit();
                fireTopSheet.animate().translationY(-600).setDuration(300);
                geldTopSheet.animate().translationY(-600).setDuration(300);
                pageCover.setVisibility(View.GONE);
                geldTopSheet.setVisibility(View.GONE);
                fireTopSheet.setVisibility(View.GONE);
                isTopSheetVisible = false;
                isGeldSheetVisible = false;
            }
        });



        mAuth = FirebaseAuth.getInstance();
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.navigation_shop:
                        selectedFragment = new ShopFragment();
                        break;
                    case R.id.navigation_profile:
                        selectedFragment = new profileFragment();
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerMain, selectedFragment).commit();

                return true;
            }
        });
        date = DownloadReadlessons.get_last_lesson2(ReadWrite.read(this.getFilesDir() + File.separator + "user"), new DownloadReadlessons.HashCallback() {
            @Override
            public void onCallback(HashMap<String, String> value) {

                Log.d("v", "v");
                streak = value.get("streak");
                maxStreak = value.get("maxStreak");
                streak7 = value.get("7streak");
                setStreak.setText(streak);
                progress = String.valueOf(value.get("cProgress"));
                isDownload = true;
                setCourseProgressWeb();
                setProgressPython();
                name = String.valueOf(value.get("name"));
                img = String.valueOf(value.get("img"));
                friends = String.valueOf(value.get("friends"));
                user_xp = Integer.parseInt(value.get("xp"));
                pyXp =  Integer.parseInt(value.get("pyXp"));
                imgC =  Integer.parseInt(value.get("imgC"));
                htmlXp =  Integer.parseInt(value.get("htmlXp"));
                try {
                    boolean b = progress.equals("a");
                } catch (Exception e){
                    Toast.makeText(mainScreen.this, "האינטרנט שלך לא יציב. מנסה שוב...", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(mainScreen.this, Login.class));
                }

                fireBar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!isTopSheetVisible){
                            fireTopSheet.setVisibility(View.VISIBLE);
                            geldTopSheet.setVisibility(View.GONE);
                            pageCover.setVisibility(View.VISIBLE);
                            fireTopSheet.setTranslationY(-600);
                            fireTopSheet.animate().translationY(0).setDuration(300);
                            streakSheetNumber.setText(streak);
                            bestStreak.setText(maxStreak);
                            lessonsDone.setText(String.valueOf(progress.split(",").length - 1));

                            isTopSheetVisible = true;
                        }
                        else{
                            fireTopSheet.animate().translationY(-600).setDuration(300);
                            pageCover.setVisibility(View.GONE);
                            fireTopSheet.setVisibility(View.GONE);
                            isTopSheetVisible = false;
                        }
                    }
                });

                coinBar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!isGeldSheetVisible){
                            geldTopSheet.setVisibility(View.VISIBLE);
                            fireTopSheet.setVisibility(View.GONE);
                            pageCover.setVisibility(View.VISIBLE);
                            geldTopSheet.setTranslationY(-600);
                            geldTopSheet.animate().translationY(0).setDuration(300);

                            isGeldSheetVisible = true;
                        }
                        else{
                            geldTopSheet.animate().translationY(-600).setDuration(300);
                            pageCover.setVisibility(View.GONE);
                            geldTopSheet.setVisibility(View.GONE);
                            isGeldSheetVisible = false;
                        }

                    }
                });

                pageCover.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fireTopSheet.animate().translationY(-600).setDuration(300);
                        geldTopSheet.animate().translationY(-600).setDuration(300);
                        pageCover.setVisibility(View.GONE);
                        geldTopSheet.setVisibility(View.GONE);
                        fireTopSheet.setVisibility(View.GONE);
                        isTopSheetVisible = false;
                        isGeldSheetVisible = false;
                    }
                });

                try {
                    Geld = Integer.parseInt(value.get("geld"));
                } catch (Exception e){
                    user.child("geld").setValue(0);
                }
                geldView.setText(String.valueOf(Geld));

                user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is uploaded
                        try {
                            if (!dataSnapshot.child("comp").getValue().toString().equals("")) {
                                    user.child("comp").setValue("");
                                    user.child("comp_time").setValue("1");
                                    userId = dataSnapshot.child("comp").getValue().toString();
                                    DownloadReadlessons.get_last_lesson2(userId, new DownloadReadlessons.HashCallback() {
                                        @Override
                                        public void onCallback(HashMap<String, String> value) {
                                            List<String> compL = Arrays.asList("2-3-2", "2-1-2", "1-3-2", "1-2-3", "1-4-2");
                                            String progress_2 = value.get("cProgress");
                                            invName = value.get("name");
                                            int sel_num = 0;
                                            try {
                                                sel = progress.split(",")[new Random().nextInt(progress.split(",").length)].split("~")[0];
                                            }catch (Exception e){
                                                Log.d("error", e.getLocalizedMessage());
                                            }
                                            while (!Arrays.asList(progress_2.split(",|\\~")).contains(sel) | !compL.contains(sel)) {
                                                sel_num++;
                                                try {
                                                    sel = progress.split(",")[new Random().nextInt(progress.split(",").length)].split("~")[0];
                                                    Log.d(progress_2.split(",")[1], sel);
                                                }catch (Exception e){
                                                    Log.d("error", e.getLocalizedMessage());
                                                }
                                                if (sel_num > 200) {
                                                    Log.d("sel", sel);
                                                    break;
                                                }
                                            }
                                            if (sel_num <= 200) {
                                                Comp_Invite dialogBack = new Comp_Invite();
                                                Log.d("sel", sel);
                                                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                                ft.add(dialogBack, "snooze_dialog");
                                                ft.commitAllowingStateLoss();
                                            } else{
                                                FirebaseDatabase database1 = FirebaseDatabase.getInstance();
                                                DatabaseReference myRef2 = database1.getReference("Users").child(mainScreen.userId);
                                                myRef2.child("start_comp").setValue("nonShared");
                                            }
                                            //DatabaseReference myRef2 = database1.getReference("Users").child(userId);
                                            //myRef2.child("start_comp").setValue(sel);
                                            //startComp(sel);
                                        }
                                    });
                            }
                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                //int month = Integer.parseInt(date.get("month"));

                int day = Integer.parseInt(value.get("date"));
            }
        });


        FirebaseDatabase database_start = FirebaseDatabase.getInstance();
        DatabaseReference myRef_win = database_start.getReference("Users").child(ReadWrite.read(mainScreen.this.getFilesDir() + File.separator + "user")).child("comp_w");
        myRef_win.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is uploaded
                try {
                    if (dataSnapshot.getValue().toString().equals("l")){
                        myRef_win.setValue("");
                        startActivity(new Intent(mainScreen.this, mainScreen.class));
                    }
                } catch (Exception e){
                    Log.d("err", "toLogin");
                    //startActivity(new Intent(mainScreen.this, Login.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerMain, new HomeFragment()).commit();


    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user){
        if(user == null){
            //not logged in
            Log.d("null", "logged in");
            startActivity(new Intent(mainScreen.this, Login.class));
        }
    }
    void startComp(String id) {
        LessonType = "comp";
        MainActivity.id = id;
        MainActivity.name = "comp";
        startActivity(new Intent(mainScreen.this, MainActivity.class));
    }

    void setCourseProgressWeb(){
        for(String i : progress.split(",")){
            if(i.startsWith("2")){
                courseProgressWeb++;
            }
        }

        Log.d("progressCount", String.valueOf(courseProgressWeb));
    }
    void setProgressPython(){
        for(String i : progress.split(",")){
            if(i.startsWith("1")){
                courseProgressPython++;
            }
        }
    }
}
