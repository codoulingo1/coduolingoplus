package com.example.coduolingo;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    RelativeLayout course1;
    ImageView courseImage1;
    View clickView1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        course1 = (RelativeLayout) v.findViewById(R.id.course1);
        courseImage1 = (ImageView) v.findViewById(R.id.courseImage1);
        clickView1 = (View) v.findViewById(R.id.clickView1);

        clickView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), tree.class);
                startActivity(intent);
            }
        });



        return v;
    }
}
