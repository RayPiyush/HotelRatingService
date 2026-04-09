package com.pr.user.service.UserService.controllers;

import com.pr.user.service.UserService.entity.User;
import com.pr.user.service.UserService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    //Here we are not using dto, but using dto is good practice
    //create
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User user1=userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }


    //single user get
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId){
        User user=userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){
        List<User> list=userService.getAllUser();
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable String userId){
        userService.deleteUser(userId);
        return ResponseEntity.ok("User Deleted Successfully");
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUserById(@PathVariable String userId,@RequestBody User user){
        User existingUser=userService.updateUser(userId,user);
        //This created when new user is created
        //return ResponseEntity.status(HttpStatus.CREATED).body(existingUser);
        return ResponseEntity.ok(existingUser);
    }


}
