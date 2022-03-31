package com.example.wishlistwepapp.models;

import java.util.ArrayList;

public class User {

    private String name;
    private String email;
    private String password;
    private ArrayList<WishList> wishlists;

    public User(String name, String email, String password) {
        this.name = name;
        if (isEmailValid(email))
            this.email = email;
        else
            this.email = null;
        this.password = password;
    }

    private boolean isEmailValid(String tar){
        boolean verification = false;

        if(tar.contains("@") && tar.contains("."))
            verification = true;

        return verification;
    }

    public String getEmail() {
        return email;
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
