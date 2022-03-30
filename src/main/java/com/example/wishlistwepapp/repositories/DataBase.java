package com.example.wishlistwepapp.repositories;

import com.example.wishlistwepapp.models.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataBase {


    private static Statement statement;
    private static Connection connection;
    private static ResultSet resultSet;
    private static String sqlAddress;


    public static void connectToDB(){

        try
        {
            String url = "jdbc:mysql://localhost:3306/wish_list_app";
            connection = DriverManager.getConnection(url,"Trip","Password1234");
            System.out.println("Ok, we have a connection.");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    public static void addUser(User user){

    }

    public static User getUserByUserName(String userName){
        return null;
    }

}
