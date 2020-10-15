package com.getcodly.codly;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ShopFragment extends Fragment {

    RelativeLayout relativeClick1;
    TextView priceItem1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstancState) {
        Log.d("fragmentTest", "savta2");
        View v = inflater.inflate(R.layout.fragment_shop, container, false);
        RelativeLayout relativeClick1 = (RelativeLayout) v.findViewById(R.id.c);
        priceItem1 = v.findViewById(R.id.priceItem1);

        relativeClick1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FirebaseDatabase database = FirebaseDatabase.getInstance();

                DatabaseReference myRef = database.getReference("Users");
                DatabaseReference fireBase = myRef.child(ReadWrite.read(getActivity().getFilesDir()+ File.separator+ "user"));
                HashMap<String, String> a = DownloadReadlessons.get_last_lesson2(ReadWrite.read(getActivity().getFilesDir() + File.separator + "user"), new DownloadReadlessons.HashCallback() {
                    @Override
                    public void onCallback(HashMap<String, String> value) {
                        if(Boolean.valueOf(value.get("streak freeze")) != true){
                            if (mainScreen.Geld >= 5){
                                fireBase.child("streak freeze").setValue("true");
                                fireBase.child("geld").setValue(Integer.parseInt(value.get("geld")) - 5);
                                mainScreen.Geld -= 5;
                                priceItem1.setText("מחומש");
                                mainScreen.geldView.setText(String.valueOf(mainScreen.Geld));
                            } else{
                                //Not enough geld
                            }
                        } else{
                            //Already has a streak freeze equipped
                            priceItem1.setText("מחומש");
                        }
                    }
                });
            }
        });
        return v;

    }
}
