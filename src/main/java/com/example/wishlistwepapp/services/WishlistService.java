package com.example.wishlistwepapp.services;

import com.example.wishlistwepapp.models.User;
import com.example.wishlistwepapp.models.Wish;
import com.example.wishlistwepapp.models.WishList;

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

    public WishList createWishlist(String title, String desc){
        return new WishList(title, desc);
    }

    public void deleteWishlist(WishList wishlist){
        //placeholder
        ArrayList<WishList> placeholder = new ArrayList<>();

        placeholder.removeIf(wishlist::equals);
    }

    protected void addWish(Wish wish){

    }

}
