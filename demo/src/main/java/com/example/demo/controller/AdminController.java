package com.example.demo.controller;

import com.example.demo.user.User;
import com.example.demo.service.UserService;
import com.example.demo.util.UserErrorResponse;
import com.example.demo.util.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<HttpStatus> createUser(@RequestBody @Valid User user,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg
                        .append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new UserException(errorMsg.toString());
        }
        userService.saveUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
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
    public ResponseEntity<HttpStatus> updateUser(@RequestBody @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg
                        .append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";")
                        .append("\n");
            }
            throw new UserException(errorMsg.toString());
        }
        userService.updateUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> userException(UserException e) {
        UserErrorResponse response = new UserErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
