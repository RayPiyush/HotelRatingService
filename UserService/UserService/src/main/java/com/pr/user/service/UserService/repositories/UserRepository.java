package com.pr.user.service.UserService.repositories;

import com.pr.user.service.UserService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
    //if we want to implement any custom method then we can
}
