package com.example.siddharthm.blogapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.security.PrivateKey;

public class PostActivity extends AppCompatActivity {

    private ImageButton mImageButton;
    private EditText mPostTitle;
    private EditText mPostDescp;
    private Button mPostButton;
    private Uri imageUri = null;
    private StorageReference mStorage;
    private ProgressDialog mpd;
    private static final int GALLERY_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        mStorage = FirebaseStorage.getInstance().getReference();
        mImageButton = (ImageButton)findViewById(R.id.imageSelect);
        mPostTitle = (EditText)findViewById(R.id.titleFeild);
        mPostDescp = (EditText)findViewById(R.id.despFeild);
        mPostButton = (Button)findViewById(R.id.submitButton);
        mpd = new ProgressDialog(this);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST);
            }
        });
        mPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }
        });
    }

    private void startPosting() {
        mpd.setMessage("Uploading to blog...");
        mpd.show();
        String title_val = mPostTitle.getText().toString().trim();
        String desc_val = mPostDescp.getText().toString().trim();
        if (!TextUtils.isEmpty(title_val) && !TextUtils.isEmpty(desc_val) && imageUri != null){
            StorageReference filepath = mStorage.child("Blog Images").child(imageUri.getLastPathSegment());
            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mpd.dismiss();
                }
            });


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
            imageUri = data.getData();
            mImageButton.setImageURI(imageUri);
        }
    }
}
