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
        User u = new User(name, email, password);
        if(u.getEmail() == null)
            u.setEmail("generic@dog.com");

        return u;
    }

    public void deleteUser(){
        //placeholder
        ArrayList<User> placeholder = new ArrayList<>(Arrays.asList(new User("Hej","@.","123")));

        //placeholder.removeIf(user::equals);
    }

    public User getUser(String name, String email,String password){
        return new User(name, email,password);
    }

    public User getSingleUser(){
        return new User("John","lol123");
    }

}
