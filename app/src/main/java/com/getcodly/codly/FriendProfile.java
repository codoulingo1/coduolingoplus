package com.getcodly.codly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
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
    TextView setStreak;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);
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
        inv =  (Button) findViewById(R.id.inv);
        setStreak = (TextView) findViewById(R.id.streak);
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
                    Picasso.with(FriendProfile.this).load(value.get("img")).resizeDimen(R.dimen.image_size, R.dimen.image_size).placeholder(R.drawable.goj).into(profImg);
                }catch(Exception e){
                    profImg.setImageResource(R.drawable.user_pic);
                }

                setName.setText(value.get("name"));
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
        backToTree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FriendProfile.this, mainScreen.class));
            }
        });
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
        inv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("c", String.valueOf(mainScreen.Geld));
                Log.d("c", String.valueOf(oGeld));
                if (mainScreen.Geld>=4 && Integer.parseInt(oGeld)>=4) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();

                    DatabaseReference myRef = database.getReference("Users");
                    DatabaseReference fireBase = myRef.child(Search.selected);
                    mainScreen.userId = Search.selected;
                    fireBase.child("comp").setValue(ReadWrite.read(FriendProfile.this.getFilesDir() + File.separator + "user"));
                    fireBase.child("comp_time").setValue(String.valueOf(System.currentTimeMillis()));
                    inv.setText("ממתין לאישור");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    CompWait dialogBack = new CompWait();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.add(dialogBack, "snooze_dialog");
                    ft.commitAllowingStateLoss();
                }
                else if (mainScreen.Geld>=4){
                    Toast.makeText(FriendProfile.this, "למשתמש/ת שהוזמן לתחרות אין מספיק מטבעות קודלי בשביל להשתתף בה",
                            Toast.LENGTH_LONG).show();
                }
                else if (Integer.parseInt(oGeld)>=4){
                    Toast.makeText(FriendProfile.this, "אין לך מספיק מטבעות קודלי בשביל להתחיל תחרות",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(FriendProfile.this, "לך ולמשתמש/ת השני שהוזמן לתחרות אין מספיק מטבעות קודלי בשביל להשתתף בה",
                            Toast.LENGTH_LONG).show();
                }
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
}
