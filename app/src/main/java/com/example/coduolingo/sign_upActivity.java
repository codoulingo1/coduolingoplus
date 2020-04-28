package com.example.coduolingo;


import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class sign_upActivity extends AppCompatActivity {

    EditText emailInp;
    EditText pasInp;
    EditText nameinp;
    Button con;
    TextView to_sign_up;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        con = (Button) findViewById(R.id.sign_up_btn);
        emailInp = (EditText) findViewById(R.id.inpemail);
        pasInp = (EditText) findViewById(R.id.inppas);
        nameinp = (EditText) findViewById(R.id.inpname);
        con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (emailInp.getText().toString().length()>5 & emailInp.getText().toString().contains("@")) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Users");
                    DatabaseReference user = myRef.child(emailInp.getText().toString().replace('.', ' '));
                    user.child("id").setValue("");
                    user.child("email").setValue(emailInp.getText().toString());
                    user.child("imgUrl").setValue("");
                    user.child("name").setValue(nameinp.getText().toString());
                    user.child("pas").setValue(pasInp.getText().toString());
                    ReadWrite.write(Environment.getExternalStorageDirectory() +"/" + "user", emailInp.getText().toString().replace('.', ' '));
                    File a = new File(Environment.getExternalStorageDirectory() +"/" + "user.txt");
                    Log.d("hi", String.valueOf(a.exists()));
                    startActivity(new Intent(sign_upActivity.this, tree.class));
                }
            }
        });
    }
}
