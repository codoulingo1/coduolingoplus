package com.getcodly.codly;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;

import androidx.appcompat.app.AppCompatDialogFragment;

public class Comp_Invite extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_comp__invite, null);
        TextView t = view.findViewById(R.id.textView17);
        t.setText(mainScreen.invName + "מזמין אותך לתחרות");
        builder.setView(view).setNegativeButton("ביטול", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("הצטרף", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseDatabase database1 = FirebaseDatabase.getInstance();
                
                DatabaseReference myRef2 = database1.getReference("Users").child(mainScreen.userId);
                DatabaseReference myRef3 = database1.getReference("Users");
                DatabaseReference user2 = myRef3.child(ReadWrite.read(getActivity().getFilesDir() + File.separator + "user"));
                myRef2.child("start_comp").setValue(mainScreen.sel);
                mainScreen.LessonType = "comp";
                int GeldToGive = 4;
                mainScreen.Geld -= GeldToGive;
                user2.child("geld").setValue(mainScreen.Geld);
                mainScreen.w = "comp";
                MainActivity.id = mainScreen.sel;
                MainActivity.name = "comp";
                Log.d("MainActivity.name", mainScreen.sel);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });

        return builder.create();

    }
}