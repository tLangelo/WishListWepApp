package com.example.wishlistwepapp.models;

import java.util.ArrayList;

public class WishList {

    private int id;
    private String title;
    private String description;
    private ArrayList<Wish> wishes;


    public WishList(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public WishList(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Wish> getWishes(){
        return this.wishes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "WishList{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", wishes=" + wishes +
                '}';
    }
}
