package com.pr.user.service.UserService.services;

import com.pr.user.service.UserService.entity.User;

import java.util.List;

public interface UserService {
    //user operation


    //create user
    User saveUser(User user);

    //get all user
    List<User> getAllUser();

    //get single user
    User getUser(String userId);

    //delete user
    void deleteUser(String userId);

    //update user

    User updateUser(String userId,User userId1);
}
