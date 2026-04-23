package com.pr.user.service.UserService.external.service;

import com.pr.user.service.UserService.entity.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "RATINGSERVICE")
public interface RatingService {

    //get

    //post
    @PostMapping("/ratings")
    public Rating createRating(Rating values);

    //put
    @PutMapping("/ratings/{ratingId}")
    public Rating updateRating(Rating values,@PathVariable String ratingId);
    //we can also use responseentity

    //delete
    @DeleteMapping("ratings/{ratingId}")
    public void deleteRating(@PathVariable String ratingId);

}
