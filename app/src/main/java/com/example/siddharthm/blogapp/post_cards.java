package com.example.siddharthm.blogapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class post_cards extends AppCompatActivity {
    private ImageView imageFireUrl;
    private TextView titleView;
    private TextView descpView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_cards);
        titleView = (TextView)findViewById(R.id.titleView);
        descpView = (TextView)findViewById(R.id.descpView);
        imageFireUrl = (ImageView)findViewById(R.id.imageFireView);
    }
}
