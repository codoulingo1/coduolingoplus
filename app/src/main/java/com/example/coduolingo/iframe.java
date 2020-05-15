package com.example.coduolingo;

        import androidx.appcompat.app.AppCompatActivity;

        import android.os.Bundle;
        import android.view.View;
        import android.webkit.WebView;
        import android.webkit.WebViewClient;
        import android.widget.Button;
        import android.widget.EditText;

public class iframe extends AppCompatActivity {

    String htmlCode;
    WebView htmlView;
    Button submitBtn;
    EditText htmlInp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iframe);

        htmlView = (WebView) findViewById(R.id.HtmlView);
        submitBtn = (Button) findViewById(R.id.submitHTML);
        htmlInp = (EditText) findViewById(R.id.inputHTML);

        htmlView.getSettings().setJavaScriptEnabled(true);
        htmlView.setWebViewClient(new WebViewClient());

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                htmlCode = htmlInp.getText().toString();
                htmlView.loadData(htmlCode, "text/html", "UTF-8");
            }
        });

    }
}