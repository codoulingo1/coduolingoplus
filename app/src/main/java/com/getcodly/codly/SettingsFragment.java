package com.getcodly.codly;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


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
    public static int isBacking = 0;

    private SwitchCompat switchCompat;
    private RelativeLayout toPlus;
    private RelativeLayout toAbout;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        profileImg = (ImageView) v.findViewById(R.id.imageViewSettings);
        yourNameEditText = (EditText) v.findViewById(R.id.yourNameEdit);
        submitBtn = (Button) v.findViewById(R.id.saveBtnProfile);
        signOutBtn = (TextView) v.findViewById(R.id.sign_outBtnProfile);
        mButtonChooseImage = (TextView) v.findViewById(R.id.changeImageBtn);
        switchCompat = v.findViewById(R.id.switch1);
        toPlus = v.findViewById(R.id.toPlus);
        toAbout = v.findViewById(R.id.toAbout);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        mStorageRef = FirebaseStorage.getInstance().getReference();


        loadProfileSettings();

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checkFilePermissions();
                changeImageDialoge.show(getParentFragmentManager(), "hi");
            }
        });

        toPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), codlyPlusActivity.class));
            }
        });

        toAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AboutActivity.class));
            }
        });

        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switchCompat.isChecked()){

                } else{

                }
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
                            DownloadReadlessons.get_liga(mainScreen.uId, new DownloadReadlessons.HashCallback3() {
                                @Override
                                public void onCallback(HashMap<String, ArrayList> value) {
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = database.getReference("Users");
                                    DatabaseReference myRef2 = database.getReference("ligot");
                                    try {
                                        DatabaseReference fireBase = myRef.child(ReadWrite.read(requireActivity().getFilesDir()+File.separator + "user"));
                                        fireBase.child("name").setValue(yourNameEditText.getText().toString());
                                    } catch (Exception e){

                                    }
                                    DatabaseReference fireBase2 = myRef2.child(value.get("ligaId").get(0).toString());
                                    fireBase2.setValue(value.get("ligaC").get(0).toString().replaceAll(mainScreen.name, yourNameEditText.getText().toString()));
                                    Log.d("hi", yourNameEditText.getText().toString());
                                    mainScreen.name = yourNameEditText.getText().toString();
                                    Toast.makeText(getContext(), "השם השתנה בהצלחה", Toast.LENGTH_SHORT).show();
                                    loadProfileSettings();
                                    isChanged = false;
                                    submitBtn.setVisibility(View.INVISIBLE);
                                }
                            });
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

                name = mainScreen.name;
                img = mainScreen.img;

                yourNameEditText.setText(name);
                try {
                    Picasso.get().load(img).resizeDimen(R.dimen.image_size, R.dimen.image_size).placeholder(R.drawable.goj).into(profileImg);
                } catch (Exception e) {
                    Log.d("drawable", "gut?");
                    String firstLetter;
                    if (name.split(" ").length == 1){
                        firstLetter = String.valueOf(name.split(" ")[0].charAt(0));
                    } else{
                        firstLetter = String.valueOf(name.split(" ")[0].charAt(0)) + String.valueOf(name.split(" ")[1].charAt(0));
                    }
                    //ColorGenerator generator = ColorGenerator.MATERIAL;
                    int color1 = mainScreen.imgC;
                    Log.d(String.valueOf(color1), String.valueOf(color1));
                    TextDrawable drawable = TextDrawable.builder().beginConfig()
                            .width(250)  // width in px
                            .height(250) // height in px
                            .endConfig()
                            .buildRect(firstLetter, color1);
                    profileImg.setImageDrawable(drawable);
                }
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

    @Override
    public void onResume() {
        super.onResume();

        if(getView() == null){
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    startActivity(new Intent(getContext(), mainScreen.class));
                    isBacking = 1;
                    return true;
                }
                return false;
            }
        });
    }


}
