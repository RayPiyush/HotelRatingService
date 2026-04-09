package com.pr.hotel.service.HotelService.controller;

import com.pr.hotel.service.HotelService.entity.Hotel;
import com.pr.hotel.service.HotelService.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    //create
    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel){
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.create(hotel));
    }

    //getAll
    @GetMapping
    public  ResponseEntity<List<Hotel>> getAllHotel(){
        List<Hotel> list=hotelService.getAll();
        return ResponseEntity.ok(list);
    }

    //get
    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotel(@PathVariable String id){
        Hotel hotel=hotelService.get(id);
        return ResponseEntity.ok(hotel);
    }

}
