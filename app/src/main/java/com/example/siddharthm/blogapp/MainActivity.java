package com.example.siddharthm.blogapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mBlogList;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseUsers;
    private FirebaseAuth.AuthStateListener mAuthListner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                  if (firebaseAuth.getCurrentUser() == null){
                      Intent login_Intent = new Intent(MainActivity.this,LoginActivity.class);
                      login_Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                      startActivity(login_Intent);
                  }
            }
        };
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Blog");
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabaseReference.keepSynced(true);
        mBlogList = (RecyclerView)findViewById(R.id.blogList);
        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager(new LinearLayoutManager(this));
        checkUserExist();


        }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
        FirebaseRecyclerAdapter<PostsData,PostViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<PostsData, PostViewHolder>
                (PostsData.class,R.layout.activity_post_cards,PostViewHolder.class,mDatabaseReference) {
            @Override
            protected void populateViewHolder(PostViewHolder viewHolder, PostsData model, int position) {
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDescp(model.getDescp());
                viewHolder.setImage(getApplicationContext(),model.getImage());

            }
        };
        mBlogList.setAdapter(firebaseRecyclerAdapter);
    }

    private void checkUserExist() {
        if (mAuth.getCurrentUser()!=null){
        final String user_id = mAuth.getCurrentUser().getUid();
        mDatabaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (!dataSnapshot.hasChild(user_id)){
                    Intent setup_Intent = new Intent(MainActivity.this,SetupActivity.class);
                    setup_Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(setup_Intent);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }}
    public static class PostViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public PostViewHolder(View itemView) {
            super(itemView);
            mView = itemView ;
             }

        public void setTitle(String title){
            TextView post_title = (TextView)mView.findViewById(R.id.post_title);
            post_title.setText(title);
            }

        public void setDescp(String descp){
            TextView post_desc = (TextView)mView.findViewById(R.id.post_desc);
            post_desc.setText(descp);
        }
        public void setImage(Context ctx, String imagei){
            ImageView post_image = (ImageView)mView.findViewById(R.id.post_image1);
            Picasso.with(ctx).load(imagei).into(post_image);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add){
            startActivity(new Intent(MainActivity.this,PostActivity.class));
        }
        if (item.getItemId() == R.id.action_logout){
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        mAuth.signOut();
    }


}
