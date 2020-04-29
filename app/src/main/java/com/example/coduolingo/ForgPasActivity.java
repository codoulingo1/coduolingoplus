package com.example.coduolingo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;

public class ForgPasActivity extends AppCompatActivity {

    EditText pasInp;
    Button con;
    EditText phoneNumInp;
    Button con2;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forg_pas);
        con = (Button) findViewById(R.id.entID);
        con2 = (Button) findViewById(R.id.entPa);
        con2.setVisibility(View.INVISIBLE);
        pasInp = findViewById(R.id.inpIDpas);
        text = (TextView) findViewById(R.id.textVpas);
        text.setText( "כתוב את הקוד שנשלח למספר הטלפון:"  + usernameloginActivity.T_phone.substring(0, 8));
        con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pasInp.getText().toString().equals(usernameloginActivity.T_pas)) {
                    text.setText("קבע סיסמא חדשה:");
                    con.setVisibility(View.GONE);
                    con2.setVisibility(View.VISIBLE);
                    pasInp.setText("");
                }
            }
        });
        con2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Users");
                DatabaseReference user = myRef.child(usernameloginActivity.T_email);
                user.child("pas").setValue(pasInp.getText().toString());
                Toast.makeText(ForgPasActivity.this, "יסמא חדשה נקבעה" ,Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ForgPasActivity.this ,usernameloginActivity.class));
            }
        });
    }
}
