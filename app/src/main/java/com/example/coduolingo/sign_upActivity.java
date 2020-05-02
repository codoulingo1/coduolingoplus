package com.example.coduolingo;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class sign_upActivity extends AppCompatActivity {

    FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseAuth mAuth;
    EditText emailField;
    EditText passwordField;
    Button mLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        FirebaseAuth.getInstance().signOut();
        emailField = (EditText) findViewById(R.id.emailField2);
        passwordField = (EditText) findViewById(R.id.passwordField2);
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

                // ...
            }
        });
    }
}
