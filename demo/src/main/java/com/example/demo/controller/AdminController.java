package com.example.demo.controller;

import com.example.demo.user.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getListOfCars() {
        return userService.getListOfUsers();
    }

    @PostMapping("/user-create")
    public ResponseEntity<HttpStatus> createUser(@RequestBody User user) {
        HttpStatus status = userService.saveUser(user);
        return ResponseEntity.ok(status);
    }

    @DeleteMapping("/user-delete/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
        HttpStatus status = userService.deleteUserById(id);
        return ResponseEntity.ok(status);
    }

    @GetMapping("/user/{id}")
    public User findUserById(@PathVariable("id") Long id) {
        return userService.findUserById(id);
    }


    @PostMapping("/user-update")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody User user) {
        HttpStatus status = userService.updateUser(user);
        return ResponseEntity.ok(status);
    }
}

