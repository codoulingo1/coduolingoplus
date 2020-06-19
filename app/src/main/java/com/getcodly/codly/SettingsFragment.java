package com.getcodly.codly;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private static final int RESULT_OK = -1;

    public SettingsFragment() {
        // Required empty public constructor
    }

    String name;
    ImageView profileImg;
    EditText yourNameEditText;
    CountDownTimer mCountdown;
    String img;
    Button submitBtn;
    TextView signOutBtn;
    boolean isNotSaved;
    private GoogleSignInClient mGoogleSignInClient;
    Boolean isChanged = false;
    private FirebaseAuth firebaseAuth;
    private StorageReference mStorageRef;
    private static final int PICK_IMAGE_REQUEST = 1;
    TextView mButtonChooseImage;
    changeImageDialog changeImageDialoge = new changeImageDialog();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        profileImg = (ImageView) v.findViewById(R.id.imageViewSettings);
        yourNameEditText = (EditText) v.findViewById(R.id.yourNameEdit);
        submitBtn = (Button) v.findViewById(R.id.saveBtnProfile);
        signOutBtn = (TextView) v.findViewById(R.id.sign_outBtnProfile);
        mButtonChooseImage = (TextView) v.findViewById(R.id.changeImageBtn);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        checkFilePermissions();

        loadProfileSettings();

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImageDialoge.show(getParentFragmentManager(), "hi");
            }
        });

        mCountdown = new CountDownTimer(120, 50) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                isChanged = true;
            }
        }.start();

        yourNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(isChanged == true){
                    submitBtn.setVisibility(View.VISIBLE);

                    submitBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("Users");
                            DatabaseReference fireBase = myRef.child(ReadWrite.read(getActivity().getFilesDir()+File.separator + "user"));

                            fireBase.child("name").setValue(yourNameEditText.getText().toString());
                            Toast.makeText(getContext(), "השם השתנה בהצלחה", Toast.LENGTH_SHORT).show();
                            loadProfileSettings();
                            isChanged = false;
                            submitBtn.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }
        });



        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*mGoogleSignInClient.signOut();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);*/
                openDialog();
            }
        });

        return v;
    }

    void loadProfileSettings(){
        final HashMap<String, String> hashMap = DownloadReadlessons.get_last_lesson(ReadWrite.read(getActivity().getFilesDir()+ File.separator+ "user"));
        mCountdown = new CountDownTimer(100, 50) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                name = hashMap.get("name");
                img = hashMap.get("img");

                yourNameEditText.setText(name);
                Picasso.with(getActivity()).load(img).placeholder(R.drawable.user_pic).into(profileImg);
            }
        }.start();
    }

    public void openDialog() {
        OutDialog outDialog = new OutDialog();
        outDialog.show(getParentFragmentManager(), "Exit");
    }

    private void checkFilePermissions() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

        } else {
            Log.d("malbona", "malbona");
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

        } else {
            Log.d("malbona", "malbona");
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }
    }
}
