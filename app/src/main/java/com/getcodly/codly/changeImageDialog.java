package com.getcodly.codly;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;

public class changeImageDialog extends AppCompatDialogFragment {

    public Uri imageUri;
    private int RESULT_OK = -1;
    TextView mButtonChooseImage;
    TextView mButtonRemoveImage;
    TextView mButtonCaptureImage;
    private static final int PICK_IMAGE_REQUEST = 1;
    ImageView mImageView;
    Button mUploadImage;
    ProgressBar mProgressBar;
    StorageReference fileReference;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;
    Uri mImageUri;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_change_image, null);

        builder.setView(view);

        mButtonChooseImage = view.findViewById(R.id.textView16);
        mImageView = view.findViewById(R.id.profilePreview);
        mUploadImage = view.findViewById(R.id.saveImageBtn);
        mProgressBar = view.findViewById(R.id.uploadProgressBar);
        mButtonRemoveImage = view.findViewById(R.id.deleteProfilePic);

        mStorageRef = FirebaseStorage.getInstance().getReference("profile-pictures");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("profile-pictures");

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        mButtonRemoveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Users");
                DatabaseReference fireBase = myRef.child(ReadWrite.read(getActivity().getFilesDir()+ File.separator + "user"));
                mainScreen.img = "";
                fireBase.child("imgUrl").setValue("");
                dismiss();
            }
        });

        mUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()){

                } else {
                    uploadFile();
                }
            }
        });


        return builder.create();
    }

    public void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            mImageUri = data.getData();
            mImageView.setVisibility(View.VISIBLE);
            mUploadImage.setVisibility(View.VISIBLE);
            Picasso.get().load(mImageUri).into(mImageView);
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getActivity().getApplicationContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile(){
        if (mImageUri != null){
            fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));
            mProgressBar.setVisibility(View.VISIBLE);
            mUploadTask = fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(0);
                            downloadPicture();
                        }
                    }, 500);

                    Toast.makeText(getActivity(), "Upload Successful", Toast.LENGTH_SHORT).show();
                    UploadProfileImage uploadProfileImage = new UploadProfileImage("ProfilePic1", taskSnapshot.getUploadSessionUri().toString());
                    String uploadID = mDatabaseRef.push().getKey();
                    mDatabaseRef.child(uploadID).setValue(uploadProfileImage);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    mProgressBar.setProgress((int) progress);
                }
            });

        } else {

        }
    }

    void downloadPicture() {
        fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d("link", uri.toString());
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Users");
                DatabaseReference fireBase = myRef.child(ReadWrite.read(getActivity().getFilesDir()+ File.separator + "user"));

                fireBase.child("imgUrl").setValue(uri.toString());
                dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }


}
