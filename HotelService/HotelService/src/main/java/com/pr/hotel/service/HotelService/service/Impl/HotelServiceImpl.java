package com.pr.hotel.service.HotelService.service.Impl;

import com.pr.hotel.service.HotelService.entity.Hotel;
import com.pr.hotel.service.HotelService.repository.HotelRepository;
import com.pr.hotel.service.HotelService.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public Hotel create(Hotel hotel){
        String hotelId= UUID.randomUUID().toString();
        hotel.setId(hotelId);
        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getAll(){
        return hotelRepository.findAll();
    }

    @Override
    public Hotel get(String id){
        return hotelRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Hotel Not found with id:"+id));
    }
}
