package com.getcodly.codly;


import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class usernameloginActivity extends AppCompatActivity {

    FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseAuth mAuth;
    TextInputLayout emailField;
    TextInputLayout passwordField;
    ImageButton mLoginBtn;
    TextView signUp;
    TextView resetBtn;
    ImageButton backBtn;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_username);
        emailField = findViewById(R.id.emailField2);
        backBtn = findViewById(R.id.backBtn);
        passwordField =  findViewById(R.id.passwordField);
        mLoginBtn = findViewById(R.id.signInBtn1);
        signUp = findViewById(R.id.createAccount);
        resetBtn = findViewById(R.id.forgotPassword);

        mAuth = FirebaseAuth.getInstance();

        /*signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(usernameloginActivity.this, sign_upActivity.class));
            }
        });*/

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(usernameloginActivity.this, Login.class));
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logIn();
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(usernameloginActivity.this, PasswordReset.class));
            }
        });

    }

    private void updateUI(FirebaseUser user){
        if(user == null){

        } else {
            ReadWrite.write(this.getFilesDir()+File.separator+ "user", user.getEmail().replace('.', ' ') + "G");
            Toast.makeText(usernameloginActivity.this, "Success", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(usernameloginActivity.this, tree.class));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void logIn(){
        email = emailField.getEditText().getText().toString();
        String password = passwordField.getEditText().getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("savta", "signInWithEmail:failure", task.getException());
                            Toast.makeText(usernameloginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }
}
