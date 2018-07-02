package com.example.siddharthm.blogapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private EditText mNameFeild;
    private EditText mEmailFeild;
    private EditText mPassFeild;
    private Button mSignupButton;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgressDialog;
    private DatabaseReference mDATABASE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        mDATABASE = FirebaseDatabase.getInstance().getReference().child("Users");
        mNameFeild = (EditText)findViewById(R.id.nameFeild);
        mEmailFeild = (EditText)findViewById(R.id.emailFeild);
        mPassFeild = (EditText)findViewById(R.id.passFeild);
        mSignupButton = (Button)findViewById(R.id.signupBtn);
        mProgressDialog = new ProgressDialog(this);
        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegister();
            }
        });
    }

    private void startRegister() {
        final String name = mNameFeild.getText().toString().trim();
        String email = mEmailFeild.getText().toString().trim();
        String pass = mPassFeild.getText().toString().trim();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)){
            mProgressDialog.setMessage("Signing Up...");
            mProgressDialog.show();
            mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        String useer_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference current_user_db = mDATABASE.child(useer_id);
                        current_user_db.child("name").setValue(name);
                        current_user_db.child("image").setValue("default");
                        mProgressDialog.dismiss();
                        Intent mainIntent = new Intent(RegisterActivity.this,MainActivity.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mainIntent);

                    }

                }
            });
        }
    }
}
