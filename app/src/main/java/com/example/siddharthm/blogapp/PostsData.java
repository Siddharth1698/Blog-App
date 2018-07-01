package com.example.siddharthm.blogapp;

public class PostsData {
    String title;
    String descp;
    int photoid;

    public PostsData(String title, String descp, int photoid) {
        this.title = title;
        this.descp = descp;
        this.photoid = photoid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescp() {
        return descp;
    }

    public void setDescp(String descp) {
        this.descp = descp;
    }

    public int getPhotoid() {
        return photoid;
    }

    public void setPhotoid(int photoid) {
        this.photoid = photoid;
    }
}
