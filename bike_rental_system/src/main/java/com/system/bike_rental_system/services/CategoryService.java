package com.system.bike_rental_system.services;

import com.system.bike_rental_system.entity.Category;
import com.system.bike_rental_system.entity.User;

import java.util.List;

public interface CategoryService {
    List<Category> fetchAll();
    Category fetchById(Integer id);
}
