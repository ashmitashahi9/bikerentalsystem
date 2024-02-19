package com.system.bike_rental_system.services;

import com.system.bike_rental_system.entity.Bike;
import com.system.bike_rental_system.pojo.BikePojo;

import java.io.IOException;
import java.util.List;

public interface BikeService {
    String saveBike(BikePojo bikePojo) throws IOException;
    String saveBikeByEntity(Bike bike);
    Bike fetchById(Integer id);
    List<Bike> fetchByCategory(Integer categoryId);
    List<Bike> similarBikes(Integer categoryId, Integer bikeId);
    List<Bike> fetchAllByCategory(Integer categoryId);
    List<Bike> fetchMostRented();
}
