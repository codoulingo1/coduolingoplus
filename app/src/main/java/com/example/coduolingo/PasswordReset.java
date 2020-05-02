package com.example.coduolingo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.coduolingo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordReset extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText emailReset;
    Button resetSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);
        emailReset = (EditText) findViewById(R.id.emailReset);
        resetSend = (Button) findViewById(R.id.SendReset);

        mAuth = FirebaseAuth.getInstance();
        resetSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.sendPasswordResetEmail(emailReset.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("savta", "Email sent.");
                                }else{
                                    Log.d("savta", "Email Failed.");
                                }
                            }
                        });
            }
        });
    }
}
