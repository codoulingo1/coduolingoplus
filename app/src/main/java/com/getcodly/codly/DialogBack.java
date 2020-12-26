package com.getcodly.codly;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;


import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogBack extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.back_dialog, null);

        builder.setView(view).setNegativeButton("ביטול", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("לצאת", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    LessonActivity.j = 1;
                    if (mainScreen.w.equals("py")) {
                        startActivity(new Intent(getContext(), tree.class));
                    } else if (mainScreen.w.equals("html")) {
                        startActivity(new Intent(getContext(), tree_html_improved.class));
                    }else{
                        startActivity(new Intent(getContext(), mainScreen.class));
                    }
                }catch (Exception e){
                    LessonActivity.j = 1;
                    startActivity(new Intent(getContext(), mainScreen.class));
                }
            }
        });

        return builder.create();

    }
}
