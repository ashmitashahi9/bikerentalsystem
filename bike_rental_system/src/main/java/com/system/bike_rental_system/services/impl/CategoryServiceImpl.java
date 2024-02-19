package com.system.bike_rental_system.services.impl;

import com.system.bike_rental_system.entity.Category;
import com.system.bike_rental_system.repo.CategoryRepo;
import com.system.bike_rental_system.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepo categoryRepo;
    @Override
    public List<Category> fetchAll() {
        return categoryRepo.findAll();
    }

    @Override
    public Category fetchById(Integer id) {
        return categoryRepo.findById(id).orElseThrow(()->new RuntimeException("Not Found"));
    }
}
