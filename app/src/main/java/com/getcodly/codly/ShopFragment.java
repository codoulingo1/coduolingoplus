package com.getcodly.codly;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ShopFragment extends Fragment {

    RelativeLayout relativeClick1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("fragmentTest", "savta2");
        View v = inflater.inflate(R.layout.fragment_shop, container, false);
        RelativeLayout relativeClick1 = (RelativeLayout) v.findViewById(R.id.c);

        relativeClick1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FirebaseDatabase database = FirebaseDatabase.getInstance();

                DatabaseReference myRef = database.getReference("Users");
                DatabaseReference fireBase = myRef.child(ReadWrite.read(getActivity().getFilesDir()+ File.separator+ "user"));
                fireBase.child("streak freeze").setValue("true");
            }
        });
        return v;

    }
}
