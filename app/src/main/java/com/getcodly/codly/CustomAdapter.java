package com.getcodly.codly;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> names;
    ArrayList<Integer> xp;
    ArrayList<Integer> imgC;
    String n;
    String type;
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, ArrayList<String> names, ArrayList<Integer> xp, String n, ArrayList<Integer> imgC, String type) {
        this.context = context;
        this.names = names;
        this.xp = xp;
        this.type = type;
        this.imgC = imgC;
        this.n = n;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.liga_list, null);
        TextView name = (TextView) view.findViewById(R.id.aNametxt);
        TextView xpp = (TextView) view.findViewById(R.id.aVersiontxt);
        RelativeLayout l = (RelativeLayout) view.findViewById(R.id.view);
        TextView num = (TextView) view.findViewById(R.id.num);
        ImageView icon = (ImageView) view.findViewById(R.id.appIconIV);//
        if (names.get(i).equals(n)){
            SpannableStringBuilder builder = new SpannableStringBuilder();
            SpannableString txtSpannable = new SpannableString(names.get(i));
            l.setBackgroundColor(Color.parseColor("#98FB98"));
            StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
            txtSpannable.setSpan(boldSpan, 0, names.get(i).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.append(txtSpannable);
            name.setText(builder);
        }
        else {
            name.setText(names.get(i));
        }
        Log.d("num", String.valueOf(i));
        num.setText(String.valueOf(i + 1));
        if (i <= 9 && !type.contains("5")){
            //l.setBackgroundColor(Color.parseColor("#98FB98")); //goes up a league
            num.setTextColor(Color.parseColor("#5DAF71"));
        }
        else if (i >= 20 && !type.contains("1")){
            //l.setBackgroundColor(Color.parseColor("#ffc6c4")); //goes down a league
            num.setTextColor(Color.parseColor("#DE302B"));
        }
        else{
            l.setBackgroundColor(Color.parseColor("#F5F5F5")); //stays the same league
        }
        xpp.setTextColor(Color.parseColor("#B4B4B4"));
        xpp.setText(String.valueOf(xp.get(i)) + "XP");
        String firstLetter;
        if (names.get(i).split(" ").length == 1){
            firstLetter = String.valueOf(names.get(i).split(" ")[0].charAt(0));
        } else{
            try {
                firstLetter = String.valueOf(names.get(i).split(" ")[0].charAt(0)) + String.valueOf(names.get(i).split(" ")[1].charAt(0));
            } catch (Exception e){
                Log.d("malbona nomo", names.get(i));
                firstLetter = "A";
            }
        }
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color1 = generator.getRandomColor();
        try {
            color1 = imgC.get(i);
        } catch (Exception e){

        }
        Log.d(String.valueOf(color1), String.valueOf(color1));
        TextDrawable drawable = TextDrawable.builder().beginConfig()
                .width(60)  // width in px
                .height(60) // height in px
                .endConfig()
                .buildRound(firstLetter, color1);
        icon.setImageDrawable(drawable);
        return view;
    }
}
