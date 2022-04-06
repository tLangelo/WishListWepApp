package com.example.wishlistwepapp.services;

import com.example.wishlistwepapp.models.User;
import com.example.wishlistwepapp.models.Wish;
import com.example.wishlistwepapp.models.WishList;
import com.example.wishlistwepapp.repositories.DataBase;

import java.util.ArrayList;
import java.util.Arrays;

public class WishlistService {
    //Singleton
    private static WishlistService single_instance = null;

    public static WishlistService getInstance(){
        if(single_instance == null)
            single_instance = new WishlistService();
        return single_instance;
    }

    public void createWishlist(User user, WishList wishList){
        DataBase.connectToDB();
        DataBase.addWishList(user, wishList);
        DataBase.closeConnection();
    }

    public WishList getWishlist(User user, String title){
        DataBase.connectToDB();
        WishList wl = DataBase.getWishListByTitle(user, title);
        DataBase.closeConnection();

        return wl;
    }

    public void deleteWishlist(WishList wishlist){
        //placeholder
        ArrayList<WishList> placeholder = new ArrayList<>();

        placeholder.removeIf(wishlist::equals);
    }

    protected void addWish(Wish wish){

    }

}
