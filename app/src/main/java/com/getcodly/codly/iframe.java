package com.getcodly.codly;

        import androidx.appcompat.app.AppCompatActivity;

        import android.graphics.drawable.AnimatedVectorDrawable;
        import android.graphics.drawable.Drawable;
        import android.os.Bundle;
        import android.text.Editable;
        import android.text.TextWatcher;
        import android.util.Log;
        import android.view.View;
        import android.webkit.WebView;
        import android.webkit.WebViewClient;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageButton;


public class iframe extends AppCompatActivity {

    String htmlCode;
    WebView htmlView;
    ImageButton submitBtn;
    Button help1;
    EditText htmlInp;

    private AnimatedVectorDrawable animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iframe);

        htmlView = (WebView) findViewById(R.id.HtmlView);
        submitBtn = (ImageButton) findViewById(R.id.submitHTML);
        help1 = (Button) findViewById(R.id.help1);
        htmlInp = (EditText) findViewById(R.id.inputHTML);

        htmlView.getSettings().setJavaScriptEnabled(true);
        htmlView.setWebViewClient(new WebViewClient());

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitBtn.setImageResource(R.drawable.avd_anim);
                animate();
                htmlInp.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        submitBtn.setImageResource(R.drawable.next3);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                htmlCode = htmlInp.getText().toString();
                htmlInp.setText("hi");
                htmlView.loadData(htmlCode, "text/html", "UTF-8");
            }
        });

    }

    public void animate(){
        Drawable d = submitBtn.getDrawable();
        if (d instanceof AnimatedVectorDrawable) {

            Log.d("testanim", "onCreate: instancefound" + d.toString());
            animation = (AnimatedVectorDrawable) d;
            animation.start();
        }
    }
}