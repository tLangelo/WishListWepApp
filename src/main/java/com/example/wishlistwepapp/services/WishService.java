package com.example.wishlistwepapp.services;

import com.example.wishlistwepapp.models.Wish;
import com.example.wishlistwepapp.models.WishList;

public class WishService {
    //Singleton
    private static WishService single_instance = null;

    public static WishService getInstance(){
        if(single_instance == null)
            single_instance = new WishService();
        return single_instance;
    }

    //obj
    WishList wl = new WishList("Cykel", "Rød cykel");

    /*public Wish createWish(){
        return new Wish();
    }
     */

    public void deleteWish(Wish wish){
        wl.getWishes().removeIf(wish::equals);
    }

    public void editWish(Wish wish){

    }

}
