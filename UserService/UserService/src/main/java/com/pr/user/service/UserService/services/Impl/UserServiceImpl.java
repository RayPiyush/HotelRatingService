package com.pr.user.service.UserService.services.Impl;

import com.pr.user.service.UserService.entity.Hotel;
import com.pr.user.service.UserService.entity.Rating;
import com.pr.user.service.UserService.entity.User;
import com.pr.user.service.UserService.exceptions.ResourceNotFoundException;
import com.pr.user.service.UserService.external.service.HotelService;
import com.pr.user.service.UserService.repositories.UserRepository;
import com.pr.user.service.UserService.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    private Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        //it will generate a unique userId
        String randomUserId= UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {

        //return userRepository.findAll();
        List<User> users=userRepository.findAll();

        // loop through each user and fetch their ratings
        List<User> usersWithRatings = users.stream().map(user -> {
            ArrayList<Rating> ratingsOfUser = restTemplate.getForObject(
                    "http://RATINGSERVICE/ratings/users/" + user.getUserId(),
                    ArrayList.class
            );
            logger.info("{} ", ratingsOfUser);
            user.setRatings(ratingsOfUser);
            return user;
        }).collect(Collectors.toList());

        return usersWithRatings;

    }

    @Override
    public User getUser(String userId) {
//        return userRepository.findById(userId)
//                .orElseThrow(()->new ResourceNotFoundException("User with given id is not found on server!!"+userId));

        User user=userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User id is not found with "+userId));

//        ArrayList<Rating> ratingsofUser=restTemplate.getForObject("http://localhost:8083/ratings/users/"+user.getUserId(), ArrayList.class);
//        logger.info("{} ",ratingsofUser);
        Rating[] ratingsOfUser=restTemplate.getForObject("http://RATINGSERVICE/ratings/users/"+user.getUserId(),Rating[].class);

        List<Rating> ratings=Arrays.stream(ratingsOfUser).toList();

        List<Rating> ratingList=ratings.stream().map(rating ->{
                    //ResponseEntity<Hotel> forEntity=restTemplate.getForEntity("http://HOTELSERVICE/hotels/"+rating.getHotelId(), Hotel.class);
                    //Hotel hotel=forEntity.getBody();
                    Hotel hotel=hotelService.getHotel(rating.getHotelId());

                    rating.setHotel(hotel);

                    return  rating;

        }).collect(Collectors.toList());

        user.setRatings(ratingList);


        return  user;
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User updateUser(String userId,User updatedUser) {
        User user=userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("user with given id is not found"+userId));

        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setAbout(updatedUser.getAbout());

        return userRepository.save(user);

    }
}
