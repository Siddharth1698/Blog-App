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
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText mLoginEmail;
    private EditText mLoginPassword;
    private Button mLoginBtn;
    private Button createBtn;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseUsers;
    private ProgressDialog mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabaseUsers.keepSynced(true);
        mProgress = new ProgressDialog(this);
        mLoginEmail = (EditText)findViewById(R.id.loginEmailFeild);
        mLoginPassword = (EditText)findViewById(R.id.loginPasswordFeild);
        mLoginBtn =(Button)findViewById(R.id.loginBtn);
        createBtn = (Button)findViewById(R.id.createbtn);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checklogin();
            }
        });
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }

    private void checklogin() {
        String email = mLoginEmail.getText().toString().trim();
        String password = mLoginPassword.getText().toString().trim();

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            mProgress.setMessage("Checking Login...");
            mProgress.show();
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        mProgress.dismiss();
                        checkUserExist();

                    }else {
                        mProgress.dismiss();
                        Toast.makeText(LoginActivity.this,"Error Login",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void checkUserExist() {
        final String user_id = mAuth.getCurrentUser().getUid();
        mDatabaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild(user_id)){
                    Intent main_Intent = new Intent(LoginActivity.this,MainActivity.class);
                    main_Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(main_Intent);

                }else {
                    Intent setup_Intent = new Intent(LoginActivity.this,SetupActivity.class);
                    setup_Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(setup_Intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
