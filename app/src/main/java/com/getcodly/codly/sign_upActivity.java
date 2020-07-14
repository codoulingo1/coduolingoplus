package com.getcodly.codly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;

public class sign_upActivity extends AppCompatActivity {

    FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseAuth mAuth;
    EditText emailField;
    EditText passwordField;
    Button mLoginBtn;
    String personPhoto;
    EditText inp_name;
    private FirebaseAnalytics mFirebaseAnalytics;
    private String method;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        FirebaseAuth.getInstance().signOut();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        emailField = (EditText) findViewById(R.id.emailField2);
        passwordField = (EditText) findViewById(R.id.passwordField2);
        inp_name = (EditText) findViewById(R.id.inpName);
        mLoginBtn = (Button) findViewById(R.id.signInBtn2);
        mAuth = FirebaseAuth.getInstance();
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
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

        } else {
            method = "email";
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.METHOD, method);
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle);
            String personEmail = user.getEmail();
            try {
                personPhoto = user.getPhotoUrl().toString();
            } catch (Exception e){
                personPhoto = "";
            }
            ReadWrite.write(this.getFilesDir()+ File.separator + "user", personEmail.replace('.', ' ') + "G");
            FirebaseDatabase database = FirebaseDatabase.getInstance();

            DatabaseReference myRef = database.getReference("Users");
            DatabaseReference fireBase = myRef.child(String.valueOf(personEmail.replace('.', ' ') + "G"));
            fireBase.child("id").setValue("nonGmail");
            fireBase.child("email").setValue(personEmail);
            fireBase.child("imgUrl").setValue(personPhoto);
            String personName = inp_name.getText().toString();
            fireBase.child("name").setValue(personName);
            fireBase.child("pas").setValue(Text.getRandomString(10));
            fireBase.child("phoneNum").setValue(0);
            fireBase.child("lastLessonD").child("year").setValue(0);
            fireBase.child("lastLessonD").child("month").setValue(0);
            fireBase.child("lastLessonD").child("date").setValue(0);
            fireBase.child("streak").setValue(1);
            fireBase.child("streak freeze").setValue("false");
            fireBase.child("progress").setValue("");
            fireBase.child("xp").setValue(0);
            fireBase.child("friends").setValue("");
            Toast.makeText(sign_upActivity.this,"שלום " + personName ,Toast.LENGTH_SHORT).show();
            startActivity(new Intent(sign_upActivity.this, tree.class));
            Toast.makeText(sign_upActivity.this, "Success", Toast.LENGTH_LONG).show();
        }
    }
    private void signUp(){
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("savta", "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("savta", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(sign_upActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }

            }
        });
    }
}
