package com.system.bike_rental_system;

import com.system.bike_rental_system.entity.Bike;
import com.system.bike_rental_system.entity.User;
import com.system.bike_rental_system.repo.BikeRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BikeRepoTest {
    @Autowired
    private BikeRepo bikeRepo;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveBikeTest(){
        Bike bike = Bike.builder()
                .bikeName("TestBike")
                .build();

        bikeRepo.save(bike);
        Assertions.assertThat(bike.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getBikeTest(){
        Bike bikeCreated=bikeRepo.findById(50).get();
        Assertions.assertThat(bikeCreated.getId()).isEqualTo(50);
    }
    @Test
    @Order(3)
    public void getListOfBikeTest(){
        List<Bike> bikes = bikeRepo.findAll();
        Assertions.assertThat(bikes.size()).isGreaterThan(0);
    }
    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateBikeTest(){
        Bike bike = bikeRepo.findById(50).get();
        bike.setBikeName("Avenis 125");
        Bike bikeUpdated = bikeRepo.save(bike);
        Assertions.assertThat(bikeUpdated.getBikeName()).isEqualTo("Avenis 125");
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteBikeTest(){
        Bike bike = bikeRepo.findById(50).get();
        bikeRepo.delete(bike);

        Bike bike1 = null;
        Optional<Bike> optionalBike = bikeRepo.findById(50);
        if (optionalBike.isPresent()){
            bike1 = optionalBike.get();
        }
        Assertions.assertThat(bike1).isNull();
    }
}
