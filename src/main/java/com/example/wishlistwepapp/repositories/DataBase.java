package com.example.wishlistwepapp.repositories;

import com.example.wishlistwepapp.models.User;
import com.example.wishlistwepapp.models.Wish;
import com.example.wishlistwepapp.models.WishList;

import java.sql.*;
import java.util.ArrayList;

public class DataBase {

    private static Statement statement;
    private static Connection connection;
    private static ResultSet resultSet;


    // DATABASE CONNECTION============================================================================================

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

    public static void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void executeSqlQuery(String query){
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static ResultSet getResultSet(String query){

        ResultSet resultSet = null;

        try {
            statement = connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    // USER============================================================================================

    public static void addUser(User user){

        String userName = user.getName();
        String userEmail = user.getEmail();
        String userPassword = user.getPassword();

        String query = "INSERT INTO users (`user_name`, `user_email`, `user_password`) VALUES ('" + userName + "', '" + userEmail + "', '" + userPassword + "');";
        executeSqlQuery(query);
    }

    public static void removeUser(User user){

        String query = "DELETE FROM users WHERE user_id = " + "'" + user.getId() + "'";
        executeSqlQuery(query);
    }

    public static void viewUsers(){

        String query = "SELECT * FROM users;";

        resultSet = getResultSet(query);

        System.out.println("\t|----------------------------------------------------------------------------------------|");
        System.out.printf("\t| %-4s | %-15s | %-33s | %-25s |\n", "Id", "User Name", "E-mail", "Password");
        System.out.println("\t|----------------------------------------------------------------------------------------|");
        while(true) {

            try {
                if (!resultSet.next()) break;
                int col0 = resultSet.getInt("user_id");
                String col1 = resultSet.getString("user_name");
                String col2 = resultSet.getString("user_email");
                String col3 = resultSet.getString("user_password");



                System.out.printf("\t| %-4s | %-15s | %-33s | %-25s |", col0, col1, col2, col3);
                System.out.println();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("\t|----------------------------------------------------------------------------------------|");

    }

    public static User getUserByEmail(String userEmail){

        String query = "SELECT * FROM users WHERE user_email = '" + userEmail + "';";

        resultSet = getResultSet(query);

        User toReturn = null;

        while(true) {

            try {
                if (!resultSet.next()) break;
                int col0 = resultSet.getInt("user_id");
                String col1 = resultSet.getString("user_name");
                String col2 = resultSet.getString("user_email");
                String col3 = resultSet.getString("user_password");

                ArrayList<WishList> wishLists = getWishLists(col0);



                toReturn = new User(col0, col1, col2, col3, wishLists);


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return toReturn;
    }

    public static void updateUserName(User user, String newName){

        String query = "UPDATE users SET user_name = " + "'" + newName + "' WHERE user_id = " + "'" + user.getId() + "'";

        executeSqlQuery(query);

    }

    public static void updateUserEmail(User user, String newEmail){

        String query = "UPDATE users SET user_email = " + "'" + newEmail + "' WHERE user_id = " + "'" + user.getId() + "'";
        executeSqlQuery(query);
    }

    public static void updateUserPassword(User user, String newPassword){

        String query = "UPDATE users SET user_password = " + "'" + newPassword + "' WHERE user_id = " + "'" + user.getId() + "'";
        executeSqlQuery(query);

    }


    // WISHLIST============================================================================================

    public static void addWishList(User user, WishList wishList){

        String title = wishList.getTitle();
        String description = wishList.getDescription();

        String query = "INSERT INTO wish_lists (user_id, title, description) " +
                    "VALUES ('" + user.getId() + "', '" + title + "', '" + description + "');";


        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println(title + " got inserted into database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeWishList(User user, WishList wishList){

        String query = "DELETE FROM wish_lists " +
                "WHERE user_id = '" + user.getId() + "' AND " +
                "wish_list_id = '" + wishList.getId() + "';";
        executeSqlQuery(query);

    }

    private static void viewWishLists(){

        String query = "SELECT * FROM wish_lists;";

        try {
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("\t|--------------------------------------------------------------------------------------------------------------------------------|");
        System.out.printf("\t| %-11s | %-11s | %-25s | %-70s |\n", "Wishlist ID", "user ID", "Title", "Description");
        System.out.println("\t|--------------------------------------------------------------------------------------------------------------------------------|");

        while (true){
            try {
                if (!resultSet.next()) {
                    break;
                }
                else{

                    int col0 = resultSet.getInt("wish_list_id");
                    int col1 = resultSet.getInt("user_id");
                    String col2 = resultSet.getString("title");
                    String col3 = resultSet.getString("description");

                    System.out.printf("\t| %-11s | %-11s | %-25s | %-70s |", col0, col1, col2, col3);
                    System.out.println();
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("\t|--------------------------------------------------------------------------------------------------------------------------------|");
    }

    public static ArrayList<WishList> getWishLists(int userId){

        String query = "SELECT * FROM wish_lists WHERE user_id = " + userId + ";";

        ResultSet rs = null;

        try {
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);
            rs = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList<Wish> wishes = new ArrayList<>();
        ArrayList<WishList> wishLists = new ArrayList<>();

        while (true){
            try {
                if (!rs.next()) {
                    break;
                }
                else{

                    int col0 = rs.getInt("wish_list_id");
                    String col1 = rs.getString("title");
                    String col2 = rs.getString("description");

                    wishes = getWishes(col0);

                    WishList wishList = new WishList(col0, col1, col2, wishes);
                    wishLists.add(wishList);
                }

            }
             catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return wishLists;
    }

    public static WishList getWishListByTitle(User user, String wishListTitle){

        int userId = user.getId();
        String query = "SELECT * FROM wish_lists WHERE user_id="+userId+" AND title='"+wishListTitle+"' LIMIT 1;";
        /*
        String query =  "SELECT * " +
                            "FROM wish_lists " +
                            "WHERE " +
                                "user_id = " + userId + " AND " +
                                "title = '" + wishListTitle + "' LIMIT 1;";
         */

        try {
            statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        WishList toReturn = null;
        while (true){
            try {
                if (!resultSet.next()) break;
                int col0 = resultSet.getInt("wish_list_id");
                String col1 = resultSet.getString("title");
                String col2 = resultSet.getString("description");

                toReturn = new WishList(col0, col1, col2);

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        toReturn.setWishes(getWishes(toReturn.getId()));

        return toReturn;
    }

    public static void updateWishListTitle(WishList wishList, String newTitle){

        String query = "UPDATE wish_lists SET title = '" + newTitle + "' WHERE wish_list_id = " + "'" + wishList.getId() + "';";
        executeSqlQuery(query);
    }

    public static void updateWishListDescription(WishList wishList, String newDescription){

        String query = "UPDATE wish_lists SET description = '" + newDescription + "' WHERE wish_list_id = " + "'" + wishList.getId() + "';";
        executeSqlQuery(query);

    }



    // WISH============================================================================================

    public static void addWish(WishList wishList, Wish wish){

        if (wishList == null){
            System.out.println("wishlist is null");
        }
        else{

            String title = wish.getTitle();
            String description = wish.getDescription();
            int price = wish.getPrice();
            String url = wish.getUrlAddress();

            String query =  "INSERT INTO wishes (wish_list_id, title, description, price, url_link) " +
                                "VALUES (" +
                                "'" + wishList.getId() +
                                "', '" + title +
                                "', '" + description +
                                "', '" + price +
                                "', '" + url + "');";

            try {
                statement = connection.createStatement();
                statement.executeUpdate(query);
                System.out.println(title + " got inserted into database " + wishList.getTitle());
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }


    }

    public static void removeWish(WishList wishList, Wish wish){


        int list_id = wishList.getId();
        System.out.println(list_id);
        int wish_id = wish.getId();
        System.out.println(wish_id);

        String query = "DELETE FROM wishes " +
                    "WHERE wish_list_id = " + wishList.getId() + " AND " +
                    "wish_id = " + wish.getId() + ";";

        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();

        }


    }

    private static void viewWishes(){

        String query = "SELECT * FROM wishes;";

        try {
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }





        System.out.println("\t|--------------------------------------------------------------------------------------------------------------------------------|");
        System.out.printf("\t| %-11s | %-11s | %-25s | %-70s |\n", "Wish ID", "Wishlist ID", "Title", "Description");
        System.out.println("\t|--------------------------------------------------------------------------------------------------------------------------------|");


        while (true){
            try {
                if (!resultSet.next()) {
                    break;
                }
                else{

                    int col0 = resultSet.getInt("wish_id");
                    int col1 = resultSet.getInt("wish_list_id");
                    String col2 = resultSet.getString("title");
                    String col3 = resultSet.getString("description");


                    System.out.printf("\t| %-11s | %-11s | %-25s | %-70s |", col0, col1, col2, col3);
                    System.out.println();



                }

            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\t|--------------------------------------------------------------------------------------------------------------------------------|");

    }

    public static Wish getWishByTitle(WishList wishList, String title){

        String query =  "SELECT * " +
                "FROM wishes " +
                "WHERE " +
                "wish_list_id = " + wishList.getId() + " AND " +
                "title = '" + title + "';";

        try {
            statement = connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Wish toReturn = null;

        while(true) {

            try {
                if (!resultSet.next()) break;
                int col0 = resultSet.getInt("wish_id");
                String col1 = resultSet.getString("title");
                String col2 = resultSet.getString("description");
                String col3 = resultSet.getString("url_link");
                int col4 = resultSet.getInt("price");

                ArrayList<WishList> wishLists = new ArrayList<>();

                toReturn = new Wish(col0, col1, col2, col3, col4);


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return toReturn;
    }

    private static ArrayList<Wish> getWishes(int wishListId) {

        String query = "SELECT * FROM wishes WHERE wish_list_id = " + wishListId + ";";
        ResultSet rs = null;


        try {
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);
            rs = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList<Wish> wishes = new ArrayList<>();

        while (true) {
            try {
                if (!rs.next()) {
                    break;
                }

                    int col0 = rs.getInt("wish_id");
                    String col1 = rs.getString("title");
                    String col2 = rs.getString("description");
                    String col3 = rs.getString("url_link");
                    int col4 = rs.getInt("price");

                    Wish wish = new Wish(col0, col1, col2, col3, col4);
                    wishes.add(wish);
                }
             catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return wishes;

    }

    public static void updateWishTitle(Wish wish, String newTitle){
        String query = "UPDATE wishes SET title = " + "'" + newTitle + "' WHERE wish_id = " + "'" + wish.getId() + "'";

        executeSqlQuery(query);
    }

    public static void updateWishDescription(Wish wish, String newDescription){
        String query = "UPDATE wishes SET description = " + "'" + newDescription + "' WHERE wish_id = " + "'" + wish.getId() + "'";

        executeSqlQuery(query);
    }

    public static void updateWishPrice(Wish wish, int newPrice){

        String query = "UPDATE wishes SET price = " + "'" + newPrice + "' WHERE wish_id = " + "'" + wish.getId() + "'";

        executeSqlQuery(query);

    }

    public static void updateWishListUrl(Wish wish, String newURL){

        String query = "UPDATE wishes SET url_link = " + "'" + newURL + "' WHERE wish_id = " + "'" + wish.getId() + "'";

        executeSqlQuery(query);

    }

}
