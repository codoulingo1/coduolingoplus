package com.getcodly.codly.adapter;

import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.getcodly.codly.fragment.walkthrough1;
import com.getcodly.codly.fragment.walkthrough2;
import com.getcodly.codly.fragment.walkthrough3;
import com.getcodly.codly.fragment.walkthrough4;

public class AdapterWalkthrough extends FragmentStatePagerAdapter {

    public AdapterWalkthrough(FragmentManager fm){
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                walkthrough1 tab1 = new walkthrough1();
                return tab1;

            case 1:
                walkthrough2 tab2 = new walkthrough2();
                return tab2;
            case 2:
                walkthrough3 tab3 = new walkthrough3();
                return tab3;
            case 3:
                walkthrough4 tab4 = new walkthrough4();
                return tab4;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
