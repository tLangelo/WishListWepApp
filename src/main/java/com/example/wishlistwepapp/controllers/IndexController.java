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


    @GetMapping("/")
    public String index(HttpSession session, Model model){


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
        userToDisplay = us.getUser(email,password);

        if(userToDisplay == null)
            return "redirect:/signin";

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

    @GetMapping("/wishes")
    public String wishes(Model model){
        model.addAttribute(currUserWishlist);
        return "wish";
    }

    @PostMapping("/wishes/{wishListName}")
    public String addWish(@PathVariable("wishListName") String name, WebRequest params, Model model){
        model.addAttribute("wlsName", currUserWishlist);
        String wishlistTitle = params.getParameter("titleWishlist");
        String title = params.getParameter("titleWish");
        String desc = params.getParameter("descWish");
        int price;
        try{
            price = Integer.parseInt(params.getParameter("priceWish"));
        }catch(NumberFormatException e){
            price = 0;
        }

        String url = params.getParameter("urlWish");
        Wish wish = new Wish(title, desc, price, url);

        ws.createWish(wls.getWishlist(userToDisplay, wishlistTitle), wish);

        model.addAttribute("title", title);
        model.addAttribute("description", desc);
        model.addAttribute("user", userToDisplay);
        model.addAttribute("wishlists", userToDisplay.getWishlists());
        System.out.println(userToDisplay.getWishlists());
        return "wishlist";
    }




    //work in progress :)
    /*
    @GetMapping("/register")
    @ResponseBody
    public String showSignUp(HttpServletRequest req, Model model){
        //setSession(req);

        model.addAttribute("user", req.getSession());

        return "sign_in";
    }

    @GetMapping("/sign-in")
    @ResponseBody
    public String setSignInSession(HttpServletRequest request, @RequestParam String name, String email, String password){
        HttpSession session = request.getSession();
        User currUser = us.createUser(name, email, password);
        session.setAttribute("currUser", currUser);

        return "sign_in";
    }

    @GetMapping("/sign-up")
    @ResponseBody
    public String setSignUpSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        User currUser = us.createUser("John", "generic@dog.com", "123");
        session.setAttribute("currUser", currUser);

        return "sign_in";
    }



    @GetMapping("/get-session")
    @ResponseBody
    public String getSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("currUser");

        return user.toString();
    }
     */




}
