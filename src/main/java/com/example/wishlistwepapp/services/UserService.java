package com.example.wishlistwepapp.services;

import com.example.wishlistwepapp.models.User;

import java.util.ArrayList;
import java.util.Arrays;

public class UserService {
    //Singleton
    private static UserService single_instance = null;

    public static UserService getInstance(){
        if(single_instance == null)
            single_instance = new UserService();
        return single_instance;
    }

    public User createUser(String name, String email, String password){
        if(!isEmailValid(email))
            return null;
        else
            return new User(name, email, password);
    }

    public void deleteUser(){
        //placeholder
        ArrayList<User> placeholder = new ArrayList<>(Arrays.asList(new User("Hej","@.","123")));

        //placeholder.removeIf(user::equals);
    }

    private boolean isEmailValid(String tar){

        if(tar.contains("@") && tar.contains("."))
            return true;
        else
            return false;
    }

    public User getUser(String name, String password){
        return new User(name, password);
    }

    public User getSingleUser(){
        return new User("John","lol123");
    }

}
