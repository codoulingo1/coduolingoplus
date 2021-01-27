package com.getcodly.codly;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.app.ActivityCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SaveDialog extends AppCompatDialogFragment {

    private EditText mFileName;
    private ImageButton saveBtn;
    String CodeToSave;
    Boolean isButtonEnabled = false;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.save_dialog, null);

        builder.setView(view);

        mFileName = (EditText) view.findViewById(R.id.fileName);
        saveBtn = view.findViewById(R.id.saveBtn);
        CodeToSave = iframe2.htmlCodeParent;
        saveBtn.setClickable(false);


        mFileName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mFileName.getText().toString().trim().length() > 0){
                    saveBtn.setImageResource(R.drawable.save_btn_color);
                    isButtonEnabled = true;
                } else {
                    saveBtn.setImageResource(R.drawable.save_btn_gray);
                    isButtonEnabled = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isButtonEnabled == true){
                    String fileName = mFileName.getText().toString();
                    iframe2.fileName = fileName;
                    mFileName.setText("");
                    String htmlCodeToSave = codeFrament.htmlCode;
                    try {
                        htmlCodeToSave = htmlCodeToSave.replace(System.getProperty("line.separator"), "\\n");
                    }catch (Exception e){
                        htmlCodeToSave = "";
                    }
                    saveBtn.setImageResource(R.drawable.save_btn_gray);
                    String fileNameBetter = getContext().getFilesDir() + "/" + "codes/" + fileName + "htmll";
                    File f = new File(fileNameBetter);
                    f.getParentFile().mkdirs();
                    isButtonEnabled = false;
                    ReadWrite.write(fileNameBetter, htmlCodeToSave);
                    if (iframe2.p) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("Users");
                        DatabaseReference fireBase = myRef.child(String.valueOf(ReadWrite.read(getContext().getFilesDir() + File.separator + "user"))).child("docs").child(fileName);
                        fireBase.child("name").setValue(fileName);
                        fireBase.child("code").setValue(htmlCodeToSave);
                        fireBase.child("type").setValue("html");
                    }
                    iframe2.saved = true;
                    Log.d("testyTest", ReadWrite.read(fileNameBetter));
                    //dismiss();
                }
            }
        });

        return  builder.create();
    }

    /*public void save(View v){
        String fileName = mFileName.getText().toString().trim();
        FileOutputStream fos = null;

        try {
            fos = getActivity().openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(CodeToSave.getBytes());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void load(View v){
        String fileName = mFileName.getText().toString();
        FileInputStream fis = null;
        try {
            fis = getActivity().openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while((text = br.readLine()) != null){
                sb.append(text).append("\n");
            }

            mFileName.setText(sb.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/
}
