package com.Rating.RatingService.services;

import com.Rating.RatingService.entity.Rating;
import com.Rating.RatingService.repository.RatingRepository;

import java.util.List;

public interface RatingService {


    //create
    Rating create(Rating rating);

    //get all rating
    List<Rating> getAllRating();

    //get all by userid
    List<Rating> getRatingByUserId(String userId);

    //get all by hotel
    List<Rating> getAllByHotelId(String hotelId);
}
