package com.pr.user.service.UserService.controllers;

import com.pr.user.service.UserService.entity.User;
import com.pr.user.service.UserService.services.Impl.UserServiceImpl;
import com.pr.user.service.UserService.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger= LoggerFactory.getLogger(UserController.class);
    //Here we are not using dto, but using dto is good practice
    //create
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User user1=userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    //int retryCount=1;
    //single user get
    @GetMapping("/{userId}")
    //@Retry(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallbackRetry")
    @CircuitBreaker(name = "ratingHotelBreaker",fallbackMethod = "ratingHotelFallbackBreaker")
    @Retry(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallbackRetry")
    public ResponseEntity<User> getUserById(@PathVariable String userId){
        logger.info("Get Single User Handler : UserController");
        //logger.info("Retry Count: {}",retryCount++);
        User user=userService.getUser(userId);
        return ResponseEntity.ok(user);
    }


    //creating fall back method for circuit breaker
    public ResponseEntity<User> ratingHotelFallbackBreaker(String userId,Exception ex){
        logger.info("Circuit Breaker Fallback is executed because server is down: {}",ex.getMessage());
        User user=User.builder()
                .email("dummy@gmail.com")
                .name("Dummy")
                .about("This user is created dummy because some service is down")
                .userId("121234")
                .build();
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    //creating fall back method for retry
    public ResponseEntity<User> ratingHotelFallbackRetry(String userId,Exception ex){
        logger.info("Retry Fallback is executed because server is down: {}",ex.getMessage());
        User user=User.builder()
                .email("dummy@gmail.com")
                .name("Dummy")
                .about("This user is created dummy because some service is down")
                .userId("121234")
                .build();
        return new ResponseEntity<>(user,HttpStatus.OK);
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
