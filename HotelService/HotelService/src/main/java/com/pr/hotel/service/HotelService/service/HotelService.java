package com.pr.hotel.service.HotelService.service;

import com.pr.hotel.service.HotelService.entity.Hotel;

import java.util.List;

public interface HotelService {
    //create
    Hotel create(Hotel hotel);

    //getAll
    List<Hotel> getAll();

    //getSingle

    Hotel get(String id);
}
