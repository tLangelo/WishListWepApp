package com.example.wishlistwepapp.models;

import java.util.ArrayList;

public class User {

    private int id;
    private String name;
    private String email;
    private String password;
    private ArrayList<WishList> wishlists = new ArrayList<>();

    public User(int id, String name, String email, String password, ArrayList<WishList> wishlists) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.wishlists = wishlists;
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void addWishList(WishList wishList){
        this.wishlists.add(wishList);
    }

    public WishList getWishlist(String wishListTitle){

        for (WishList wishList : wishlists) {
            if(wishList.getTitle().equals(wishListTitle)){
                return wishList;
            }
        }

        return null;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
