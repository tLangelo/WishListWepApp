package com.example.wishlistwepapp.controllers;

import com.example.wishlistwepapp.models.User;
import com.example.wishlistwepapp.models.Wish;
import com.example.wishlistwepapp.models.WishList;
import com.example.wishlistwepapp.services.UserService;
import com.example.wishlistwepapp.services.WishService;
import com.example.wishlistwepapp.services.WishlistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class IndexController {
    private final WishlistService wls = WishlistService.getInstance();
    private final WishService ws = WishService.getInstance();
    private final UserService us = UserService.getInstance();


    private User userToDisplay;
    private ArrayList<WishList> currUserWishlist;
    private int count = 0;


    @GetMapping("/")
    public String index(HttpSession session, Model model){
        if(count > 0){
            if((boolean)session.getAttribute("isUserLoggedIn"))
                model.addAttribute("user",(User) session.getAttribute("loggedInUser"));
        }
        count++;

        return "index";
    }


    @GetMapping("/signin")
    public String signIn(){
        return "sign_in";
    }


    @GetMapping("/signup")
    public String signUp(){
        return "sign_up";
    }

    @PostMapping("/signedin")
    public String signedIn(HttpSession session, WebRequest loginCreds, Model model){
        String email = loginCreds.getParameter("emailSignIn");
        String password = loginCreds.getParameter("passwordSignIn");
        if(us.isEmailInDatabase(email))
            userToDisplay = us.getUser(email,password);
        else
            return "redirect:/signin";

        int count = 0;

        if(userToDisplay == null){
            count++;
            return "redirect:/signin";
        }


        currUserWishlist = userToDisplay.getWishlists();

        model.addAttribute("user", userToDisplay);
        model.addAttribute("wishlists", currUserWishlist);
        //setting session
        session.setAttribute("loggedInUser",userToDisplay);
        session.setAttribute("isUserLoggedIn", true);

        return "wishlist";
    }

    @PostMapping("/signedup")
    public String signedUp(WebRequest signUpCreds, Model model){
        //init count var to check for invalid email
        int count = 0;
        //getting params for user
        String username = signUpCreds.getParameter("usernameSignUp");
        String password = signUpCreds.getParameter("passwordSignUp");
        String email = signUpCreds.getParameter("emailSignUp");
        //creating user obj
        User user = us.createUser(username,email,password);

        if (user != null)
            return "redirect:/signin";
        //incr count var to display error
        count++;
        model.addAttribute("count", count);
        return "sign_up";
    }

    @GetMapping("/wishlists")
    public String wishlist(HttpSession session, Model model){
        if((boolean)session.getAttribute("isUserLoggedIn")){
            model.addAttribute("user",(User) session.getAttribute("loggedInUser") );
            model.addAttribute("wishlists", ((User) session.getAttribute("loggedInUser")).getWishlists());
        }

        return "wishlist";
    }

    @PostMapping("/addWishlist")
    public String addWishlist(HttpSession session, WebRequest params, Model model){
        if((boolean)session.getAttribute("isUserLoggedIn")){
            model.addAttribute("user",(User) session.getAttribute("loggedInUser") );
            model.addAttribute("wishlists", ((User) session.getAttribute("loggedInUser")).getWishlists());
        }

        String title = params.getParameter("titleWishlist");
        String desc = params.getParameter("descWishlist");
        WishList wishList = new WishList(title, desc);


        wls.createWishlist(userToDisplay, wishList);


        return "redirect:/wishlists";
    }

    @GetMapping("/wishes/{wishListName}")
    public String wishes(@PathVariable("wishListName") String name, HttpSession session, Model model){
        if((boolean)session.getAttribute("isUserLoggedIn")){
            session.setAttribute("currUserWishlist",name);
            model.addAttribute("user",(User) session.getAttribute("loggedInUser") );
            model.addAttribute("wishlist", wls.getWishlist((User)session.getAttribute("loggedInUser"),name).getWishes());
            model.addAttribute("selectedWishlist",name);
        }

        return "wish";
    }

    @PostMapping("/addWish/{wishListName}")
    public String addAWish(@PathVariable("wishListName") String name, HttpSession session, WebRequest params, Model model){
        if((boolean)session.getAttribute("isUserLoggedIn")){
            model.addAttribute("user",(User) session.getAttribute("loggedInUser") );
        }

        User sessionUser = (User) session.getAttribute("loggedInUser");
        WishList currWishlist = sessionUser.getWishlist(name);

        model.addAttribute("wishlist", wls.getWishlist((User)session.getAttribute("loggedInUser"),name).getWishes());

        String title = params.getParameter("titleWish");
        String desc = params.getParameter("descWish");
        int price;
        try{
            price = Integer.parseInt(params.getParameter("priceWish"));
        }catch(NumberFormatException e){
            price = 0;
        }
        String url = params.getParameter("urlWish");

        Wish wish = new Wish(title,desc,price,url);
        ws.createWish(currWishlist, wish);

        return "redirect:/wishes/{wishListName}";
    }


}
