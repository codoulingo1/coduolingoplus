package com.getcodly.codly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    ImageView toReallyGood;
    TextView toThanks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        toReallyGood = findViewById(R.id.reallyGood);
        toThanks = findViewById(R.id.par4);

        toReallyGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://reallygood.co.il/"));
                startActivity(browserIntent);
            }
        });
        toThanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thanksDialog thanksDialog1 = new thanksDialog();
                thanksDialog1.show(getSupportFragmentManager(), "thanks");
            }
        });

    }
}