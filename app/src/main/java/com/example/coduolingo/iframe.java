package com.example.coduolingo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

public class iframe extends AppCompatActivity {

    String htmlCode;
    HtmlTextView HTMLView;
    Button submitBtn;
    EditText htmlCodeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iframe);

        HTMLView = (HtmlTextView) findViewById(R.id.html_text);
        submitBtn = (Button) findViewById(R.id.submitHTML);
        htmlCodeText = (EditText) findViewById(R.id.inputHTML);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                htmlCode = htmlCodeText.getText().toString();
                if(htmlCode != null){
                    HTMLView.setHtml(htmlCode, new HtmlHttpImageGetter(HTMLView));
                }else {
                    Toast.makeText(iframe.this, "Invalid HTML", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
