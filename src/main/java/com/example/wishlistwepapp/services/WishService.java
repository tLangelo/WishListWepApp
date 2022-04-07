package com.example.wishlistwepapp.services;

import com.example.wishlistwepapp.models.User;
import com.example.wishlistwepapp.models.Wish;
import com.example.wishlistwepapp.models.WishList;
import com.example.wishlistwepapp.repositories.DataBase;

public class WishService {
    //Singleton
    private static WishService single_instance = null;

    public static WishService getInstance(){
        if(single_instance == null)
            single_instance = new WishService();
        return single_instance;
    }


    public void createWish(WishList wishList, Wish wish){

        DataBase.connectToDB();
        DataBase.addWish(wishList, wish);
        DataBase.closeConnection();

    }

    /*
    public void deleteWish(Wish wish){
        wl.getWishes().removeIf(wish::equals);
    }*/

    public void editWish(Wish wish){

    }

}
