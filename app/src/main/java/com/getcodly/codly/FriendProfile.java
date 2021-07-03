package com.getcodly.codly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class FriendProfile extends AppCompatActivity {
    Button follow;
    Button inv;
    ImageView profImg;
    TextView setName;
    ArrayAdapter<String> itemsAdapter;
    ArrayList<String> names;
    public static ArrayList<String> vals;
    public static String oUserCodeToLoad;
    public static boolean isOUser;
    HashMap old_streak;
    HashMap old_friends;
    public static String friendUsername;
    private GoogleSignInClient mGoogleSignInClient;
    public static boolean nonShared = false;
    String oGeld;
    FirebaseAuth mAuth;
    Button backToTree;
    ProgressBar levelProgress;
    ViewPager viewPager;
    TabLayout tabLayout;
    TextView levelView;
    TextView courseXp1;
    TextView courseXp2;
    TextView xpView;
    TextView goalText;
    ProgressBar courseProgressbarWeb;
    ProgressBar courseProgressbarPy;
    TextView percentageProgress1;
    public static int courseProgressWeb = 0;
    TextView percentageProgress2;
    TextView levelBar;
    public static int courseProgressPython = 0;
    TextView setStreak;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile2);
        if (nonShared){
            Toast.makeText(FriendProfile.this, "לך ולמשתמש/ת שהזמנת לתחרות אין מספיק שיעורים משותפים",
                    Toast.LENGTH_LONG).show();
            nonShared = false;
        }
        mAuth = FirebaseAuth.getInstance();
        profImg = (ImageView) findViewById(R.id.imageView2);
        setName = (TextView) findViewById(R.id.set_name);
        backToTree =  (Button) findViewById(R.id.back_to_tree);
        follow =  (Button) findViewById(R.id.follow);
        ListView l = (ListView) findViewById(R.id.l);
        levelView = findViewById(R.id.level);
        xpView = findViewById(R.id.xpCount);
        courseXp1= findViewById(R.id.courseXpCountProfile1);
        courseXp2 = findViewById(R.id.courseXpCountProfile2);
        goalText = findViewById(R.id.goalText);
        courseProgressbarWeb = findViewById(R.id.courseProgressWeb);
        percentageProgress1 = findViewById(R.id.percentageProgress1);
        courseProgressbarPy = findViewById(R.id.courseProgressPy1);
        percentageProgress2 = findViewById(R.id.percentageProgress2);
        levelBar = findViewById(R.id.levelText);
        levelProgress = findViewById(R.id.levelProgress);
        levelView = findViewById(R.id.level);
        //inv =  (Button) findViewById(R.id.inv);
        setStreak = (TextView) findViewById(R.id.setStreak);
        courseProgressWeb = 0;
        courseProgressPython = 0;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        String idp = Search.selected;
        old_streak = DownloadReadlessons.get_last_lesson2(Search.selected, new DownloadReadlessons.HashCallback() {
            @Override
            public void onCallback(HashMap<String, String> value) {
                try {
                    if (!value.get("img").equals("0")) {
                        Picasso.get().load(value.get("img")).resizeDimen(R.dimen.image_size, R.dimen.image_size).placeholder(R.drawable.goj).into(profImg);
                    } else{
                        Log.d("drawable", "gut?");
                        String firstLetter;
                        if (value.get("name").split(" ").length == 1){
                            firstLetter = String.valueOf(value.get("name").split(" ")[0].charAt(0));
                        } else{
                            firstLetter = String.valueOf(value.get("name").split(" ")[0].charAt(0)) + String.valueOf(value.get("name").split(" ")[1].charAt(0));
                        }
                        ColorGenerator generator = ColorGenerator.MATERIAL;
                        int color1 = Integer.parseInt(value.get("imgC"));
                        TextDrawable drawable = TextDrawable.builder().beginConfig()
                                .width(60)  // width in px
                                .height(60) // height in px
                                .endConfig()
                                .buildRect(firstLetter, color1);
                        profImg.setImageDrawable(drawable);
                    }
                }catch(Exception e){
                    Log.d("drawable", "gut?");
                    String firstLetter;
                    if (value.get("name").split(" ").length == 1){
                        firstLetter = String.valueOf(value.get("name").split(" ")[0].charAt(0));
                    } else{
                        firstLetter = String.valueOf(value.get("name").split(" ")[0].charAt(0)) + String.valueOf(value.get("name").split(" ")[1].charAt(0));
                    }
                    ColorGenerator generator = ColorGenerator.MATERIAL;
                    int color1 = Integer.parseInt(value.get("imgC"));
                    TextDrawable drawable = TextDrawable.builder().beginConfig()
                            .width(60)  // width in px
                            .height(60) // height in px
                            .endConfig()
                            .buildRect(firstLetter, color1);
                    profImg.setImageDrawable(drawable);
                }

                setName.setText(value.get("name"));
                setProgressPython(value.get("cProgress"));
                setCourseProgressWeb(value.get("cProgress"));
                courseProgress(Integer.parseInt(value.get("htmlXp")), Integer.parseInt(value.get("pyXp")));
                setLevel(Integer.parseInt(value.get("xp")));
                friendUsername = value.get("name");
                oGeld = value.get("geld");
                String streak = String.valueOf(value.get("streak"));
                setStreak.setText(String.valueOf(streak));
                String friends = mainScreen.friends;
                if(friends.contains(Search.selected)){
                    follow.setText("הסר מרשימת החברים");
                }
                else{
                    follow.setText("הוסף לרשימת החברים");
                }
            }
        });
        HashMap d = DownloadReadlessons.get_docs(Search.selected, new DownloadReadlessons.HashCallback2() {
            @Override
            public void onCallback(HashMap<String, ArrayList<String>> value) {
                try {
                    Log.d("hihihi", value.get("vals").get(0));
                    String[] stringArray = value.get("names").toArray(new String[value.get("names").size()]);
                    names = value.get("names");
                    vals = value.get("vals");
                    itemsAdapter = new ArrayAdapter<String>(FriendProfile.this, android.R.layout.simple_list_item_1, android.R.id.text1, stringArray);
                    l.setAdapter(itemsAdapter);
                    int iInt = 0;
                    for (String i : value.get("vals")){
                        Log.d(String.valueOf(iInt), i);
                        iInt = iInt + 1;
                    }
                }catch (Exception e){

                }
            }
        });
//        backToTree.setOnClickListener(new View.OnClickListener() {
       //     @Override
         //   public void onClick(View v) {
          //      startActivity(new Intent(FriendProfile.this, mainScreen.class));
          //  }
    //    });
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String friends = mainScreen.friends;
                if(friends.contains(Search.selected)){
                    FirebaseDatabase database = FirebaseDatabase.getInstance();

                    DatabaseReference myRef = database.getReference("Users");
                    DatabaseReference fireBase = myRef.child(String.valueOf(ReadWrite.read(FriendProfile.this.getFilesDir() + File.separator + "user")));
                    mainScreen.friends = friends.replaceAll("-" + Search.selected + "/" + friendUsername, "");
                    fireBase.child("friends").setValue(friends.replaceAll("-" + Search.selected + "/" + friendUsername, ""));
                    follow.setText("הוסף לרשימת החברים");
                }
                else{
                    FirebaseDatabase database = FirebaseDatabase.getInstance();

                    DatabaseReference myRef = database.getReference("Users");
                    DatabaseReference fireBase = myRef.child(String.valueOf(ReadWrite.read(FriendProfile.this.getFilesDir() + File.separator + "user")));
                    mainScreen.friends = friends + "-" + Search.selected + "/" + friendUsername;
                    fireBase.child("friends").setValue(friends + "-" + Search.selected + "/" + friendUsername);
                    follow.setText("הסר מרשימת החברים");
                }
                Log.d("mainScreen.friends", mainScreen.friends);
            }
        });
        l.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                Log.i("HelloListView", "You clicked Item: " + "id" + " at position:" + position);
                Log.d("deus",names.get(position));
                isOUser = true;
                oUserCodeToLoad = (String) Search.selected + "," + String.valueOf(position);
                String prType = vals.get(((position+1)*3)-1);
                if (prType.equals("html")) {
                    selectProject.codeToLoad = vals.get(((position+1)*3)-3);
                    Intent in = new Intent(FriendProfile.this, iframe2.class);
                    startActivity(in);
                }
                if (prType.equals("py")) {
                    selectPyProject.pyCodeToLoad = vals.get(((position+1)*3)-3);
                    selectProject.codeToLoad = vals.get(((position+1)*3)-3);
                    Intent in = new Intent(FriendProfile.this, PythonActivity2.class);
                    startActivity(in);
                }
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user){
        if(user == null){
            startActivity(new Intent(FriendProfile.this, Login.class));
        } else {
            //Toast.makeText(profile_Activity.this, "Logged In", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(FriendProfile.this, mainScreen.class));
    }
    void setLevel(int xp){
        if(xp > 525){
            levelView.setText("רמה 8");
            goalText.setText("∞");
            goalText.setTextColor(Color.parseColor("#47C5A0"));
            levelBar.setText("רמה 8");
            levelProgress.setProgress(100);
        } else if(xp > 400){
            levelView.setText("רמה 7");
            goalText.setText("525");
            levelBar.setText("רמה 7");
            levelProgress.setProgress((xp - 400)*100 / (525 - 400));
        } else if(xp > 300){
            levelView.setText("רמה 6");
            goalText.setText("400");
            levelBar.setText("רמה 6");
            levelProgress.setProgress((xp - 300)*100 / (400 - 300));
        } else if(xp > 150){
            levelView.setText("רמה 5");
            goalText.setText("300");
            levelBar.setText("רמה 5");
            levelProgress.setProgress((xp - 150)*100 / (300 - 150));
        } else if(xp > 100){
            levelView.setText("רמה 4");
            goalText.setText("150");
            levelBar.setText("רמה 4");
            levelProgress.setProgress((xp - 100)*100 / (150 - 100));
        } else if(xp > 60){
            levelView.setText("רמה 3");
            goalText.setText("100");
            levelBar.setText("רמה 3");
            levelProgress.setProgress((xp - 60)*100 / (100 - 60));
        } else if(xp > 30){
            levelView.setText("רמה 2");
            goalText.setText("60");
            levelBar.setText("רמה 2");
            levelProgress.setProgress((xp - 30)*100 / (60 - 30));
        } else if(xp <= 30){
            levelView.setText("רמה 1");
            goalText.setText("30");
            levelBar.setText("רמה 1");
            levelProgress.setProgress(xp*100 / 30);
        }

        xpView.setText(String.valueOf(xp));
    }

    void courseProgress(int xp, int pyXp){
        courseProgressbarWeb.setProgress(courseProgressWeb * 100 / 16);
        courseXp1.setText(String.valueOf(xp) + " XP");
        percentageProgress1.setText(String.valueOf(courseProgressWeb * 100 / 16) + "%");

        courseProgressbarPy.setProgress(courseProgressPython * 100 / 27);
        courseXp2.setText(String.valueOf(pyXp) + " XP");
        percentageProgress2.setText(String.valueOf(courseProgressPython * 100 / 27) + "%");

    }
    void setCourseProgressWeb(String progress){
        for(String i : progress.split(",")){
            if(i.startsWith("2")){
                courseProgressWeb++;
            }
        }

        Log.d("progressCount", String.valueOf(courseProgressWeb));
    }
    void setProgressPython(String progress){
        for(String i : progress.split(",")){
            if(i.startsWith("1")){
                courseProgressPython++;
            }
        }
    }
}
