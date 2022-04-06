package com.example.wishlistwepapp.models;

import com.example.wishlistwepapp.repositories.DataBase;

import java.util.ArrayList;

public class User {

    private int id;
    private String name;
    private String email;
    private String password;
    private ArrayList<WishList> wishlists = new ArrayList<>();

    //database constructor
    public User(int id, String name, String email, String password, ArrayList<WishList> wishlists) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.wishlists = wishlists;
    }

    //sign up constructor
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    //sign in constructor
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    private boolean isEmailValid(String tar){
        boolean verification = false;

        if(tar.contains("@") && tar.contains("."))
            verification = true;

        return verification;
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

    public ArrayList<WishList> getWishlists() {
        DataBase.connectToDB();
        wishlists = DataBase.getWishLists(this.id);
        DataBase.closeConnection();

        return wishlists;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {



        String wishListsAsString = "";

        for (WishList wishList : wishlists) {
            wishListsAsString += "\t" + wishList + "\n";
        }

        String formattetColNames = String.format("| %-4s | %-15s | %-33s | %-25s |", "Id", "Name", "E-mail", "Password");
        String formattetData = String.format("| %-4s | %-15s | %-33s | %-25s |", this.id, this.name, this.email, this.password);


        return "|----------------------------------------------------------------------------------------|\n" +
                formattetColNames + "\n" +
                "|----------------------------------------------------------------------------------------|\n" +
                formattetData +
                "\n\n"
                + wishListsAsString +
                "|----------------------------------------------------------------------------------------|\n";
    }
}
