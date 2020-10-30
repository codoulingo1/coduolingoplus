package com.getcodly.codly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.util.regex.Pattern;

public class sign_upActivity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    //"(?=.*[a-zA-Z])" +      //any letter
                    //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 4 characters
                    "$");
    FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    TextInputLayout emailField;
    TextInputLayout passwordField;
    ImageButton mLoginBtn;
    String personPhoto;
    TextInputLayout inp_name;
    private FirebaseAnalytics mFirebaseAnalytics;
    private String method;
    ImageButton backBtn;
    TextView emailError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        FirebaseAuth.getInstance().signOut();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        emailField = findViewById(R.id.emailField2);
        passwordField = findViewById(R.id.passwordField);
        backBtn = findViewById(R.id.backBtn);
        inp_name =  findViewById(R.id.usernameField);
        mLoginBtn = findViewById(R.id.signUpBtn1);
        emailError = findViewById(R.id.emailError);
        mAuth = FirebaseAuth.getInstance();
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(sign_upActivity.this, Login.class));
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

    private void updateUI(FirebaseUser user) {
        if (user == null) {

        } else {
            method = "email";
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.METHOD, method);
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle);
            String personEmail = user.getEmail();
            try {
                personPhoto = user.getPhotoUrl().toString();
            } catch (Exception e) {
                personPhoto = "";
            }
            ReadWrite.write(this.getFilesDir() + File.separator + "user", user.getUid());
            FirebaseDatabase database = FirebaseDatabase.getInstance();

            DatabaseReference myRef = database.getReference("Users");
            DatabaseReference fireBase = myRef.child(String.valueOf(user.getUid()));
            fireBase.child("id").setValue("nonGmail");
            fireBase.child("email").setValue(personEmail);
            fireBase.child("imgUrl").setValue(personPhoto);
            String personName = inp_name.getEditText().getText().toString();
            fireBase.child("name").setValue(personName);
            fireBase.child("pas").setValue(Text.getRandomString(10));
            fireBase.child("phoneNum").setValue(0);
            fireBase.child("lastLessonD").child("year").setValue(0);
            fireBase.child("lastLessonD").child("date").setValue(0);
            fireBase.child("streak").setValue(0);
            fireBase.child("streak freeze").setValue("false");
            fireBase.child("progress").setValue(Text.getRandomString(5) + ",");
            fireBase.child("start_comp").setValue("");
            fireBase.child("hasDoneLesson").setValue(false);
            fireBase.child("comp_w").setValue("");
            fireBase.child("comp").setValue("");
            fireBase.child("comp_time").setValue("1");
            fireBase.child("xp").setValue(0);
            fireBase.child("pyXp").setValue(0);
            fireBase.child("htmlXp").setValue(0);
            fireBase.child("friends").setValue("");
            Toast.makeText(sign_upActivity.this, "שלום " + personName, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(sign_upActivity.this, mainScreen.class));
            Toast.makeText(sign_upActivity.this, "Success", Toast.LENGTH_LONG).show();
        }
    }

    private void signUp() {
        if(!validateEmail() | !validatePassword()){
            return;
        } else {
            String email = emailField.getEditText().getText().toString();
            String password = passwordField.getEditText().getText().toString();

            Log.d("emailThing", email + password);

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d("savta", "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        Log.w("taskthing", task.getException());
                        emailError.setVisibility(View.VISIBLE);
                        updateUI(null);
                    }

                }
            });
        }
    }

    private Boolean validateEmail() {
        String emailInput = emailField.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()) {
            emailField.setErrorEnabled(true);
            emailField.setError("אנא הכניסו מייל.");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            emailField.setErrorEnabled(true);
            emailField.setError("אנא הכניסו מייל תקין.");
            return false;
        } else {
            emailField.setError(null);
            emailField.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword(){
        String passInput = passwordField.getEditText().getText().toString();

        if (passInput.isEmpty()){
            passwordField.setErrorEnabled(true);
            passwordField.setError("אנא הכניסו סיסמה.");
            return false;

        } else if(!PASSWORD_PATTERN.matcher(passInput).matches()){
            passwordField.setErrorEnabled(true);
            passwordField.setError("הסיסמה חייבת להיות לפחות 6 תווים וללא רווחים.");
            return false;
        } else {
            emailField.setError(null);
            emailField.setErrorEnabled(false);
            return true;
        }
    }
}
