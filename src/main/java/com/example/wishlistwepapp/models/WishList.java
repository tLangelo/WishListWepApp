package com.example.wishlistwepapp.models;

import java.util.ArrayList;

public class WishList {

    private String title;
    private String description;
    private ArrayList<Wish> wishes;


    public WishList(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
