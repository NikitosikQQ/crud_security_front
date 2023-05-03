package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/index")
    public String index(){
        return "/index";
    }

    @GetMapping("/indexForUser")
    public String indexForUser(){
        return "/indexForUser";
    }

    @GetMapping("/user-info")
    public String userInfo(){
        return "/user-info";
    }

    @GetMapping("/users-table")
    public String usersTable(){
        return "/users-table";
    }

    @GetMapping("/user-infoForUser")
    public String userInfoForUser(){
        return "/user-infoForUser";
    }
}
