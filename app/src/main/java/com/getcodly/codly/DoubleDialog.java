package com.getcodly.codly;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatDialogFragment;

public class DoubleDialog extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.back_dialog, null);

        builder.setView(view).setNegativeButton("אני מעדיף שלא", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("המשך את הרצף שבוע הוסף", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();

                DatabaseReference myRef = database.getReference("Users");
                DatabaseReference fireBase = myRef.child(ReadWrite.read(getActivity().getFilesDir()+ File.separator+ "user"));
                HashMap<String, String> a = DownloadReadlessons.get_last_lesson2(ReadWrite.read(getActivity().getFilesDir() + File.separator + "user"), new DownloadReadlessons.HashCallback() {
                    @Override
                    public void onCallback(HashMap<String, String> value) {
                        if(Integer.parseInt(value.get("7streak")) == 0){
                            if (mainScreen.Geld >= 5) {
                                fireBase.child("7streak").setValue(1);
                                fireBase.child("geld").setValue(Integer.parseInt(value.get("geld")) - 5);
                                mainScreen.Geld -= 5;
                                mainScreen.geldView.setText(String.valueOf(mainScreen.Geld));
                            } else {
                                //Not enough geld
                                Toast.makeText(getActivity(), "אין מספיק מטבעות כדי לקנות", Toast.LENGTH_SHORT);

                            }
                        } else{
                            //Already has a streak freeze equipped
                        }
                    }
                });
            }
        });

        return builder.create();

    }
}
