package com.getcodly.codly;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.app.ActivityCompat;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class SaveDialog extends AppCompatDialogFragment {

    private EditText mFileName;
    private ImageButton saveBtn;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.save_dialog, null);

        builder.setView(view);

        mFileName = (EditText) view.findViewById(R.id.fileName);
        saveBtn = view.findViewById(R.id.saveBtn);

        return  builder.create();
    }

    public void save(View v){
        String fileName = mFileName.getText().toString();
        FileOutputStream fos = null;

        try {

            fos = getActivity().openFileOutput(fileName, Context.MODE_PRIVATE);
            //fos.write();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void load(View v){

    }
}
