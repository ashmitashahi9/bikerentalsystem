package com.system.bike_rental_system.repo;

import com.system.bike_rental_system.entity.Bike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BikeRepo extends JpaRepository<Bike, Integer> {
    @Query(value = "SELECT * FROM bike WHERE category_id = ?1", nativeQuery = true)
    Optional<List<Bike>> findAllByCategory(Integer id);

    @Query(value = "SELECT * FROM bike order by rented_number desc limit 4", nativeQuery = true)
    Optional<List<Bike>> findMostRented();
}
