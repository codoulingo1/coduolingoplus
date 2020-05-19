package com.example.coduolingo;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Debug;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

public class profileFragment extends Fragment {

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
    FirebaseAuth mAuth;
    Button backToTree;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        Log.d("fragmentTest", "savta2");

        mAuth = FirebaseAuth.getInstance();
        profImg = (ImageView) v.findViewById(R.id.imageView2);
        setName = (TextView) v.findViewById(R.id.set_name);
        btnSignOut = (Button) v.findViewById(R.id.sign_out);
        backToTree =  (Button) v.findViewById(R.id.back_to_tree);
        btnSignOut.setVisibility(View.INVISIBLE);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        String idp = ReadWrite.read(getActivity().getFilesDir()+ File.separator + "user");
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
                    Picasso.with(getActivity()).load(url_old).resizeDimen(R.dimen.image_size, R.dimen.image_size).placeholder(R.drawable.goj).into(profImg);
                }catch(Exception e){
                    profImg.setImageResource(R.drawable.user_pic);
                }

                setName.setText(name);
                btnSignOut.setVisibility(View.VISIBLE);
            }
        }.start();
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGoogleSignInClient.signOut();
                FirebaseAuth.getInstance().signOut();
                btnSignOut.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);

            }
        });
        backToTree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), mainScreen.class);
                startActivity(intent);
            }
        });



        return v;
    }
}
