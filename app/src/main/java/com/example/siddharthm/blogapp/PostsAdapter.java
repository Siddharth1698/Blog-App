package com.example.siddharthm.blogapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewHolder>{
    List<PostsData> postdata;

    public PostsAdapter(List<PostsData> postdata) {
        this.postdata = postdata;
    }




    @Override
    public PostsAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_post_cards,parent,false);
        PostViewHolder pvh = new PostViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.PostViewHolder holder, int position) {
        holder.titleName.setText(postdata.get(position).title);
        holder.descName.setText(postdata.get(position).descp);
        holder.Photo.setImageResource(postdata.get(position).photoid);

    }



    @Override
    public int getItemCount() {
        return postdata.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView titleName;
        TextView descName;
        ImageView Photo;

        PostViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card);
            titleName = (TextView)itemView.findViewById(R.id.titleView);
            descName = (TextView)itemView.findViewById(R.id.descpView);
            Photo = (ImageView)itemView.findViewById(R.id.imageFireView);

    }
}
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }}
