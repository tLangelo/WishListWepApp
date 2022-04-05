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
    private static String sqlString;


    public static void main(String[] args) {
        connectToDB();


        User john = getUserByEmail("johnNLarsen@mail.dk");
        User lars = getUserByEmail("larslarsen@jyskmail.dk");
        User peter = getUserByEmail("peterpan@mail.dk");
        WishList list = getWishListByTitle(john, "Min føzdag");
        WishList list1 = getWishListByTitle(john, "julegaver");
        WishList list2 = getWishListByTitle(peter, "Bryllups gaver");

        //Wish wish = getWishByTitle(list2, "Beer");



        //removeWish(list2, wish);


        closeConnection();
    }

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

    private static void executeSqlString(String sqlString){
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sqlString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // USER============================================================================================

    public static void addUser(User user){

        String userName = user.getName();
        String userEmail = user.getEmail();
        String userPassword = user.getPassword();

        sqlString = "INSERT INTO users (`user_name`, `user_email`, `user_password`) VALUES ('" + userName + "', '" + userEmail + "', '" + userPassword + "');";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sqlString);
            System.out.println(userName + " got inserted into database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeUser(User user){

        sqlString = "DELETE FROM users WHERE user_email = " + "'" + user.getEmail() + "'";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sqlString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static User getUserByEmail(String userEmail){

        sqlString = "SELECT * FROM users WHERE user_email = '" + userEmail + "';";

        try {
            statement = connection.createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery(sqlString);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        User toReturn = null;

        System.out.println("\t|----------------------------------------------------|");
        while(true) {

            try {
                if (!resultSet.next()) break;
                int col0 = resultSet.getInt("user_id");
                String col1 = resultSet.getString("user_name");
                String col2 = resultSet.getString("user_email");
                String col3 = resultSet.getString("user_password");

                ArrayList<WishList> wishLists = getWishLists(col0);



                toReturn = new User(col0, col1, col2, col3, wishLists);

                System.out.printf("\t| %-4s | %-7s | %-33s | %-55s |", col0, col1, col2, col3);
                System.out.println();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }//end while loop
        System.out.println("\t|----------------------------------------------------|");

        return toReturn;
    }

    public static void updateUserName(User user, String newName){

        sqlString = "UPDATE users SET user_name = " + "'" + newName + "' WHERE user_id = " + "'" + user.getId() + "'";

        executeSqlString(sqlString);

    }

    public static void updateUserEmail(User user, String newEmail){

        sqlString = "UPDATE users SET user_email = " + "'" + newEmail + "' WHERE user_id = " + "'" + user.getId() + "'";
        executeSqlString(sqlString);
    }

    public static void updateUserPassword(User user, String newPassword){

        sqlString = "UPDATE users SET user_password = " + "'" + newPassword + "' WHERE user_id = " + "'" + user.getId() + "'";
        executeSqlString(sqlString);

    }


    // WISHLIST============================================================================================

    public static void addWishList(User user, WishList wishList){

        /*
        if (wishList.getTitle()){

        }

         */

        String title = wishList.getTitle();
        String description = wishList.getDescription();

        sqlString = "INSERT INTO wish_lists (user_id, title, description) " +
                    "VALUES ('" + user.getId() + "', '" + title + "', '" + description + "');";


        try {
            statement = connection.createStatement();
            statement.executeUpdate(sqlString);
            System.out.println(title + " got inserted into database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeWishList(){

    }

    private static ArrayList<WishList> getWishLists(int userId){


        String sqlString = "SELECT * FROM wish_lists WHERE user_id = " + userId + ";";

        try {
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery(sqlString);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList<WishList> wishLists = new ArrayList<>();

        while (true){
            try {
                if (!resultSet.next()) {
                    break;
                }

                    int col0 = resultSet.getInt("wish_list_id");
                    String col1 = resultSet.getString("title");
                    String col2 = resultSet.getString("description");

                    ArrayList<Wish> wishes = getWishes(col0);

                    System.out.println(col0 + ", " + col1 + ", " + col2);

                    wishLists.add(new WishList(col0, col1, col2, wishes));
                }
             catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return wishLists;
    }

    public static WishList getWishListByTitle(User user, String wishListTitle){

        int userId = user.getId();

        sqlString =  "SELECT * " +
                            "FROM wish_lists " +
                            "WHERE " +
                                "user_id = " + userId + " AND " +
                                "title = '" + wishListTitle + "';";

        try {
            statement = connection.createStatement(
                        ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery(sqlString);
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

        return toReturn;
    }

    public static void updateWishListTitle(WishList wishList, String newTitle){

        sqlString = "UPDATE wish_lists SET title = '" + newTitle + "' WHERE wish_list_id = " + "'" + wishList.getId() + "';";
        executeSqlString(sqlString);
    }

    public static void updateWishListDescription(WishList wishList, String newDescription){

        sqlString = "UPDATE wish_lists SET description = '" + newDescription + "' WHERE wish_list_id = " + "'" + wishList.getId() + "';";
        executeSqlString(sqlString);

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

            sqlString =  "INSERT INTO wishes (wish_list_id, title, description, price, url_link) " +
                                "VALUES (" +
                                "'" + wishList.getId() +
                                "', '" + title +
                                "', '" + description +
                                "', '" + price +
                                "', '" + url + "');";

            try {
                statement = connection.createStatement();
                statement.executeUpdate(sqlString);
                System.out.println(title + " got inserted into database " + wishList.getTitle());
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }


    }

    public static void removeWish(WishList wishList, Wish wish){

        sqlString = "DELETE FROM wishes " +
                    "WHERE wish_list_id = '" + wishList.getId() + "' AND " +
                    "wish_title = '" + wish.getTitle() + "';";
        executeSqlString(sqlString);

    }

    public static Wish getWishByTitle(WishList wishList, String title){

        sqlString =  "SELECT * " +
                "FROM wishes " +
                "WHERE " +
                "wish_list_id = '" + wishList.getId() + "' AND " +
                "title = '" + title + "';";

        try {
            statement = connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery(sqlString);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Wish toReturn = null;

        while(true) {

            try {
                if (!resultSet.next()) break;
                int col0 = resultSet.getInt("wish_list_id");
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

        System.out.println("INSIDE GET WISHES FOR WISHLIST_ID: " + wishListId);

        String sqlString = "SELECT * FROM wishes WHERE wish_list_id = " + wishListId + ";";

        try {
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery(sqlString);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList<Wish> wishes = new ArrayList<>();

        while (true) {
            try {
                if (!resultSet.next()) {
                    break;
                }


                    int col0 = resultSet.getInt("wish_id");
                    String col1 = resultSet.getString("title");
                    String col2 = resultSet.getString("description");
                    String col3 = resultSet.getString("url_link");
                    int col4 = resultSet.getInt("price");

                    Wish wish = new Wish(col0, col1, col2, col3, col4);
                    System.out.println(wish);
                    wishes.add(wish);
                }
             catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return wishes;

    }

    public static void updateWishTitle(Wish wish, String newTitle){

    }

    public static void updateWishDescription(Wish wish, String newDescription){

    }

    public static void updateWishPrice(Wish wish, String newPrice){

        int price = Integer.valueOf(newPrice);

    }

    public static void updateWishListUrl(Wish wish, String newURL){

    }

}
