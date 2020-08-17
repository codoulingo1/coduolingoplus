package com.getcodly.codly;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
                myRef2.child("start_comp").setValue(mainScreen.sel);
                tree.LessonType = "comp";
                MainActivity.id = mainScreen.sel;
                MainActivity.name = "comp";
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });

        return builder.create();

    }
}