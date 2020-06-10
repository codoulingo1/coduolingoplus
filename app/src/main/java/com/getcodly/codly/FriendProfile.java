package com.getcodly.codly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
import java.util.HashMap;

public class FriendProfile extends AppCompatActivity {
    Button follow;
    Button skill1;
    Button skill2;
    Button skill3;
    ImageView profImg;
    TextView setName;
    String url_old;
    HashMap old_streak;
    HashMap old_friends;
    String name;
    private Button btnSignOut;
    CountDownTimer mcountdown;
    private GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;
    Button backToTree;
    TextView setStreak;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);
        mAuth = FirebaseAuth.getInstance();
        profImg = (ImageView) findViewById(R.id.imageView2);
        setName = (TextView) findViewById(R.id.set_name);
        backToTree =  (Button) findViewById(R.id.back_to_tree);
        follow =  (Button) findViewById(R.id.follow);
        setStreak = (TextView) findViewById(R.id.streak);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        String idp = Search.selected;
        old_streak = DownloadReadlessons.get_last_lesson(Search.selected);
        old_friends = DownloadReadlessons.get_last_lesson(String.valueOf(ReadWrite.read(FriendProfile.this.getFilesDir()+File.separator+ "user")));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users").child(idp);
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
                //Log.d("profile_Activity2", url_old);
                /*try {
                    Picasso.with(profile_Activity.this).load(url_old).resizeDimen(R.dimen.image_size, R.dimen.image_size).placeholder(R.drawable.goj).into(profImg);
                }catch (Exception e){
                    Log.d("savta", "Image Error");
                }*/
                try {
                    Picasso.with(FriendProfile.this).load(url_old).resizeDimen(R.dimen.image_size, R.dimen.image_size).placeholder(R.drawable.goj).into(profImg);
                }catch(Exception e){
                    profImg.setImageResource(R.drawable.user_pic);
                }

                setName.setText(name);
                String streak = String.valueOf(old_streak.get("streak"));
                setStreak.setText(String.valueOf(streak));
                String friends = String.valueOf(old_friends.get("friends"));
                if(friends.contains(Search.selected)){
                    follow.setText("הסר מרשימת החברים");
                }
                else{
                    follow.setText("הוסף לרשימת החברים");
                }
            }
        }.start();
        backToTree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FriendProfile.this, mainScreen.class));
            }
        });
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String friends = String.valueOf(old_friends.get("friends"));
                if(friends.contains(Search.selected)){
                    FirebaseDatabase database = FirebaseDatabase.getInstance();

                    DatabaseReference myRef = database.getReference("Users");
                    DatabaseReference fireBase = myRef.child(String.valueOf(ReadWrite.read(FriendProfile.this.getFilesDir() + File.separator + "user")));
                    fireBase.child("friends").setValue(friends.replaceAll("-" + Search.selected + "/" + name, ""));
                    follow.setText("הוסף לרשימת החברים");
                }
                else{
                    FirebaseDatabase database = FirebaseDatabase.getInstance();

                    DatabaseReference myRef = database.getReference("Users");
                    DatabaseReference fireBase = myRef.child(String.valueOf(ReadWrite.read(FriendProfile.this.getFilesDir() + File.separator + "user")));
                    fireBase.child("friends").setValue(friends + "-" + Search.selected + "/" + name);
                    follow.setText("הסר מרשימת החברים");
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
}
