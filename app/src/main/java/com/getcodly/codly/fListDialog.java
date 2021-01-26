package com.getcodly.codly;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.viewpager.widget.ViewPager;

public class fListDialog extends AppCompatDialogFragment {

    ViewPager viewPager;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.full_list_dialog, null);

        getChildFragmentManager().beginTransaction().add(R.id.container3, new FriendsFragment()).commit();

        builder.setView(v).setNegativeButton("סגור", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });



        return builder.create();

    }

    private void setUpViewPager(ViewPager viewPager) {
        SectionPageAdapter adapter = new SectionPageAdapter(getChildFragmentManager());

        adapter.addFragment(new FriendsFragment(), "חברים");
        adapter.addFragment(new ProjectsFragment(), "פרוייקטים");

        viewPager.setAdapter(adapter);
    }
}
