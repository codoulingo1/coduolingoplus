package com.getcodly.codly;

import android.content.Context;
import android.content.Intent;
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
import java.util.Arrays;
import java.util.List;
import java.util.zip.Inflater;

public class selectItemList extends BaseAdapter {
    Context context;
    ArrayList<String> names;
    ArrayList<Integer> xp;
    ArrayList<Integer> imgC;
    String n;
    LayoutInflater inflter;

    public selectItemList(Context applicationContext, ArrayList<String> names) {
        this.context = context;
        this.names = names;
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
        view = inflter.inflate(R.layout.select_list_item, null);
        TextView name = (TextView) view.findViewById(R.id.lessonTitle1);
        ImageView imgF = (ImageView) view.findViewById(R.id.eyalGoj1);
        RelativeLayout l = (RelativeLayout) view.findViewById(R.id.view);
        TextView againF = (TextView) view.findViewById(R.id.eyalGoj2);
        againF.setVisibility(View.INVISIBLE);
        imgF.setImageResource(R.drawable.ic_group_121);
        name.setText(names.get(i));
        if (mainScreen.progress.contains(names.get(i))){
            againF.setVisibility(View.VISIBLE);
            imgF.setImageResource(R.drawable.ic_check);
        }
        return view;
    }
}
