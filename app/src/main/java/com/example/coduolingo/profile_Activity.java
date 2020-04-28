package com.example.coduolingo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.google.android.gms.tasks.Tasks;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import androidx.appcompat.app.AppCompatActivity;

public class profile_Activity extends AppCompatActivity {

    Button skill1;
    Button skill2;
    Button skill3;
    ImageView profImg;
    TextView setName;
    String url_old;
    String name;
    private Button btnSignOut;
    CountDownTimer mcountdown;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profImg = (ImageView) findViewById(R.id.imageView2);
        setName = (TextView) findViewById(R.id.set_name);
        btnSignOut = findViewById(R.id.sign_out);
        btnSignOut.setVisibility(View.INVISIBLE);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        String idp = ReadWrite.read(Environment.getExternalStorageDirectory() +"/" + "user");
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
                Log.d("profile_Activity2", url_old);
                try {
                    Picasso.with(profile_Activity.this).load(url_old).resizeDimen(R.dimen.image_size, R.dimen.image_size).placeholder(R.drawable.goj).into(profImg);
                }catch (Exception e){

                }
                setName.setText(name);
                btnSignOut.setVisibility(View.VISIBLE);
            }
        }.start();
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGoogleSignInClient.signOut();
                Toast.makeText(profile_Activity.this,"You are Logged Out",Toast.LENGTH_SHORT).show();
                btnSignOut.setVisibility(View.INVISIBLE);
                File login = new File(Environment.getExternalStorageDirectory() +"/" + "user.txt");
                boolean del = login.delete();
                Log.d("boolean", String.valueOf(del));
                startActivity(new Intent(profile_Activity.this, Login.class));

            }
        });
    }

}