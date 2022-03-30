package com.example.wishlistwepapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model myModel){
        myModel.addAttribute("sign_in", "jdfkjdk");
        return "index";
    }

    @PostMapping("sign_in")
    public String redirectSignIN(){
        return "redirect:/logIn";
    }

    @GetMapping("/logIn")
    public String signIn(){
        return "signIn";
    }

}
