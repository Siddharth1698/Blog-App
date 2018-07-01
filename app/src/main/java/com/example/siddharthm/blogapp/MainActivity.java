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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mBlogList;
    private DatabaseReference mDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Blog");
        mBlogList = (RecyclerView)findViewById(R.id.blogList);
        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager(new LinearLayoutManager(this));


        }

    @Override
    protected void onStart() {
        super.onStart();
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
        return super.onOptionsItemSelected(item);
    }


}
