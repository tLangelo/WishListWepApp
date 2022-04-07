package com.example.wishlistwepapp.services;

import com.example.wishlistwepapp.models.User;
import com.example.wishlistwepapp.repositories.DataBase;

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

        DataBase.connectToDB();
        DataBase.addUser(new User(name, email, password));
        DataBase.closeConnection();

        return new User(name, email, password);
    }

    public void deleteUser(String email){
        DataBase.connectToDB();
        DataBase.removeUser(DataBase.getUserByEmail(email));
        DataBase.closeConnection();
    }

    private boolean isEmailValid(String tar){

        if(tar.contains("@") && tar.contains("."))
            return true;
        else
            return false;
    }

    public boolean isEmailInDatabase(String email){
        User user;
        DataBase.connectToDB();

        user = DataBase.getUserByEmail(email);
        DataBase.closeConnection();
        return user != null;
    }

    public User getUser(String email, String password){
        DataBase.connectToDB();
        User user = DataBase.getUserByEmail(email);
        DataBase.closeConnection();

        if(!user.getPassword().matches(password))
            return null;

        return user;
    }

    public User getSingleUser(){
        return new User("John","lol123");
    }

}
