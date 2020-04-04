package com.example.coduolingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NonFreedum extends AppCompatActivity {
    TextView ans;
    String org;
    List<String> back_ch = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_freedum);
        final Button opt1 = (Button) findViewById(R.id.button1);
        final Button opt2 = (Button) findViewById(R.id.button2);
        final Button opt3 = (Button) findViewById(R.id.button3);
        final Button opt4 = (Button) findViewById(R.id.button4);
        final Button opt5 = (Button) findViewById(R.id.button5);
        final Button opt6 = (Button) findViewById(R.id.button6);
        final Button check = (Button) findViewById(R.id.check);
        final TextView qs = (TextView) findViewById(R.id.textView2);
        qs.setText(MainActivity.shared_hashmap.get("qs"));
        String[] optAns = MainActivity.shared_hashmap.get("Content").split(",");
        Log.d("hi", "h" + optAns[0]);
        final Button dlt = (Button) findViewById(R.id.dlt);
        final Button dltall = (Button) findViewById(R.id.dltall);
        Button[] ops = {opt1, opt2, opt3, opt4, opt5, opt6};
        back_ch.add("");
        int opnum = 0;
        for (Button op : ops){
            try{
                op.setText(optAns[opnum]);
                opnum++;
            }
            catch (Exception e) {
                op.setVisibility(View.INVISIBLE);
                break;
            }
            }
        ans = (TextView) findViewById(R.id.textView3);
        org = ans.getText().toString();
        opt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when play is clicked show stop button and hide play button
                choice(opt1);

            }
        });
        opt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when play is clicked show stop button and hide play button
                choice(opt2);
            }
        });
        opt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when play is clicked show stop button and hide play button
                choice(opt3);
            }
        });
        opt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when play is clicked show stop button and hide play button
                choice(opt4);

            }
        });
        opt5.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View v) {
            //when play is clicked show stop button and hide play button
            choice(opt5);

        }
    });
        opt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when play is clicked show stop button and hide play button
                choice(opt6);

            }
        });
        dlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when play is clicked show stop button and hide play button
                back_choice();

            }
        });
        dltall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when play is clicked show stop button and hide play button
                rese();

            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when play is clicked show stop button and hide play button
                if (MainActivity.shared_hashmap.get("Answer").equals(ans.getText().toString())){
                    MainActivity.j++;
                    startActivity(new Intent(NonFreedum.this, MainActivity.class));
                }

            }
        });

    }
    public void choice(Button opt){
        String text = ans.getText().toString();
        String add = opt.getText().toString();
        ans.setText(text + add);
        back_ch.add(text+add);
    }
    public void back_choice() {
        try{
        if (back_ch.get(back_ch.size() - 2).length() < back_ch.get(back_ch.size() - 1).length()) {
            ans.setText(back_ch.get(back_ch.size() - 2));
            back_ch.remove(back_ch.size() - 2);}


        else {
            rese();
        }
        }catch (Exception e){
            rese();
        }
    }
    public void rese() {
        ans.setText(org);
    }
    void getLength(){
        //String btnText;
        //btnText = btn2.getText().toString();
        //btnText.length();

    }

}
