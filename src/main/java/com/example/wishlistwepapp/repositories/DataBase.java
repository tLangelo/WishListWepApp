package com.example.wishlistwepapp.repositories;

import com.example.wishlistwepapp.models.User;
import com.example.wishlistwepapp.models.Wish;
import com.example.wishlistwepapp.models.WishList;

import java.sql.*;

public class DataBase {


    private static Statement statement;
    private static Connection connection;
    private static ResultSet resultSet;


    public static void main(String[] args) {
        connectToDB();
        User user = new User("Peter", "peterpan@mail.dk", "tropådet");
        WishList wishList = new WishList("julegaver", "Det må gerne være dyre julegave i år");

        //addUser(user);
        user.addWishList(wishList);
        addWishList(user, wishList);
        Wish wish = new Wish();
        addWish(user.getWishlist("julegaver"), wish);
        updateUserName(user, "PeterPan");
        System.out.println(getUserByEmail("peterpan@mail.dk"));

        closeConnection();

    }

    public static void connectToDB(){
        try
        {
            String url = "jdbc:mysql://name-mysql-database.mysql.database.azure.com:3306/wish_list_app";
            String username = "admin_user@name-mysql-database";
            String password = "jug8ZELL_spig";
            connection = DriverManager.getConnection(url,username,password);
            System.out.println("Ok, we have a connection.");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void addUser(User user){

        String userName = user.getName();
        String userEmail = user.getEmail();

        String sqlString = "INSERT INTO users (`user_name`, `user_email`) VALUES ('" + userName + "', '" + userEmail + "');";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sqlString);
            System.out.println(userName + " got inserted into database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeUser(User user){
        String sqlString = "DELETE FROM users WHERE user_email = " + "'" + user.getEmail() + "'";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sqlString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateUserName(User user, String newName){


        String sqlString = "UPDATE users SET user_name = " + "'" + newName + "' WHERE user_email = " + "'" + user.getEmail() + "'";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sqlString);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static User getUserByEmail(String userEmail){

        String sqlString = "SELECT * FROM users WHERE user_email = '" + userEmail + "';";

        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = statement.executeQuery(sqlString);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        User toReturn = null;

        System.out.println("\t|----------------------------------------------------|");
        while(true) {

            try {
                if (!resultSet.next()) break;
                String col0 = resultSet.getString("user_id");
                String col1 = resultSet.getString("user_name");
                String col2 = resultSet.getString("user_email");
                //String col3 = resultSet.getString("user_password");

                toReturn = new User(Integer.valueOf(col0), col1, col2, null, null);

                System.out.printf("\t| %-4s | %-7s | %-33s |", col0, col1, col2);
                System.out.println();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }//end while loop
        System.out.println("\t|----------------------------------------------------|");

        return toReturn;
    }

    public static void addWishList(User user, WishList wishList){
        String title = wishList.getTitle();
        String description = wishList.getDescription();

        String sqlString = "INSERT INTO wish_lists (`user_id`, `title`, `description`) VALUES ('" + user.getId() + "', '" + title + "', '" + description + "');";

/*
        INSERT INTO TAB_STUDENT(name_student, id_teacher_fk)
        SELECT 'Joe The Student', id_teacher
        FROM TAB_TEACHER
        WHERE name_teacher = 'Professor Jack'
        LIMIT 1

 */

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sqlString);
            System.out.println(title + " got inserted into database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addWish(WishList wishList, Wish wish){

        if (wishList == null){

        }
        else{


        }
    }

    public static void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
