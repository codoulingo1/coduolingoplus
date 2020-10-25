package com.getcodly.codly;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.io.File;

public class CompWait extends AppCompatDialogFragment {

    TextView waitingText;
    long firstTime;
    boolean b = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        firstTime = System.currentTimeMillis();
        Handler handler = new Handler();
        FirebaseDatabase database_start = FirebaseDatabase.getInstance();
        DatabaseReference myRef_start = database_start.getReference("Users").child(ReadWrite.read(getActivity() + File.separator + "user")).child("start_comp");
        myRef_start.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is uploaded
                try {
                    if (!dataSnapshot.getValue().toString().equals("")){
                        String sel = dataSnapshot.getValue().toString();
                        myRef_start.setValue("");
                        startComp(sel);
                    }
                } catch (Exception e){

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.comp_waiting_dialog, null);
        waitingText = view.findViewById(R.id.waitingText);
        waitingText.setText(FriendProfile.friendUsername + "מחכה ל");
        builder.setView(view).setNegativeButton("ביטול", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();

    }

    void startComp(String id) {
        tree.LessonType = "comp";
        MainActivity.id = id;
        MainActivity.name = "comp";
        startActivity(new Intent(getContext(), MainActivity.class));
    }
}