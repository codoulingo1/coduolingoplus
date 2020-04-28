package com.example.coduolingo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class usernameloginActivity extends AppCompatActivity {

    EditText emailInp;
    EditText pasInp;
    Button con;
    TextView to_sign_up;
    List<String> fireEmails;
    SpannableString ss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_username);
        con = (Button) findViewById(R.id.sign_up_btn);
        emailInp = (EditText) findViewById(R.id.inpemail);
        pasInp = (EditText) findViewById(R.id.inppas);
        final TextView textView = findViewById(R.id.to_sign_up);
        textView.setVisibility(View.INVISIBLE);

        ss = new SpannableString(textView.getText().toString());

        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(usernameloginActivity.this, sign_upActivity.class));
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };


        ss.setSpan(clickableSpan1, 25, 38, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        fireEmails = new ArrayList<>();
        final HashMap<String, String> hashMap = new HashMap<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(DataSnapshot fire_email: dataSnapshot.getChildren()){
                    Log.d("usernameloginActivity", fire_email.child("pas").getValue().toString());
                    fireEmails.add(fire_email.getKey());
                    hashMap.put(fire_email.getKey(), fire_email.child("pas").getValue().toString());
                }

            }            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Failed to read value.", error.toException());
            }
        });
        con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailInp.getText().toString();
                String pas = pasInp.getText().toString();
                if (email.length()>5){
                    Log.d(email, pas);
                    if (fireEmails.contains(email.replace('.', ' '))){
                        Log.d(email, pas);
                        if(hashMap.get(email.replace('.', ' ')).equals(pas)){
                            Log.d(email, pas);
                            ReadWrite.write(Environment.getExternalStorageDirectory() +"/" + "user", email.replace('.', ' '));
                            startActivity(new Intent(usernameloginActivity.this, tree.class));
                        }else{

                        }
                    }else{
                            textView.setVisibility(View.VISIBLE);
                            textView.setText(ss);
                            textView.setMovementMethod(LinkMovementMethod.getInstance());
                    }

                }else{
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(ss);
                    textView.setMovementMethod(LinkMovementMethod.getInstance());
                }
            }
        });
    }
}
