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
    String unuateksto;
    int wrloc;
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
        unuateksto = MainActivity.shared_hashmap.get("additional");
        Log.d("hi", "h" + optAns[0]);
        final Button dlt = (Button) findViewById(R.id.dlt);
        final Button dltall = (Button) findViewById(R.id.dltall);
        Button[] ops = {opt1, opt2, opt3, opt4, opt5, opt6};
        back_ch.add(unuateksto);
        int opnum = 0;
        for (Button op : ops){
            try{
                op.setText(optAns[opnum]);
                opnum++;
            }
            catch (Exception e) {
                op.setVisibility(View.INVISIBLE);
            }
            }
        ans = (TextView) findViewById(R.id.textView3);
        ans.setText(unuateksto);
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
        int opt1txt = opt1.getText().toString().length();
        int opt2txt = opt2.getText().toString().length();
        int opt3txt = opt3.getText().toString().length();
        int opt4txt = opt4.getText().toString().length();
        int opt5txt = opt5.getText().toString().length();
        int opt6txt = opt6.getText().toString().length();

        opt1.getLayoutParams().width = opt1txt * 25 + 100;
        opt2.getLayoutParams().width = opt2txt * 25 + 100;
        opt3.getLayoutParams().width = opt3txt * 25 + 100;
        opt4.getLayoutParams().width = opt4txt * 25 + 100;
        opt5.getLayoutParams().width = opt5txt * 25 + 100;
        opt6.getLayoutParams().width = opt6txt * 25 + 100;

    }
    public void choice(Button opt){
        String text;
        Log.d("hi", back_ch.get(back_ch.size() - 1));
        Log.d("hi", unuateksto);

            try {
            if (!back_ch.get(back_ch.size() - 1).equals(unuateksto))
            {
                Log.d("hi1", "2");
                text = back_ch.get(back_ch.size() - 1);
            }else{
                text = unuateksto;
                Log.d("hi2", "1");

            }

        }
        catch (Exception e){
            text = unuateksto;
            Log.d("hi3", "4");
        }
        String add = opt.getText().toString();
        Log.d("hello", String.format(Text.replace(Text.replace(text, '£', '%', 0), '%', '£', 1), add));
        ans.setText(String.format(Text.replace(Text.replace(text, '£', '%', 0), '%', '£', 1), add));//add the input
        back_ch.add(String.format(Text.replace(Text.replace(text, '£', '%', 0), '%', '£', 1), add));//add the input
    }
    public void back_choice() {
        try{
        if (back_ch.get(back_ch.size() - 2).length() < back_ch.get(back_ch.size() - 1).length() || Text.findstring("£", back_ch.get(back_ch.size() - 2))) {
            ans.setText(back_ch.get(back_ch.size() - 2));
            back_ch.remove(back_ch.size() - 1);}


        else {
            rese();
        }
        }catch (Exception e){
            rese();
        }
    }
    public void rese() {
        ans.setText(unuateksto);
        back_ch.clear();
        back_ch.add(unuateksto);
    }
    void getLength(){
        //String btnText;
        //btnText = btn2.getText().toString();
        //btnText.length();

    }

}
