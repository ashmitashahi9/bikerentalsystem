package com.system.bike_rental_system;

import com.system.bike_rental_system.entity.Booking;
import com.system.bike_rental_system.repo.BikeRepo;
import com.system.bike_rental_system.repo.BookingRepo;
import com.system.bike_rental_system.repo.UserRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookingRepoTest {
    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BikeRepo bikeRepo;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveBookingTest(){
        Booking booking = Booking.builder()
                .userId(userRepo.findById(37).get())
                .bikeId(bikeRepo.findById(50).get())
            .build();

        bookingRepo.save(booking);
        Assertions.assertThat(booking.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getBookingTest(){
        Booking bookingsCreated=bookingRepo.findById(50).get();
        Assertions.assertThat(bookingsCreated.getId()).isEqualTo(50);
    }
    @Test
    @Order(3)
    public void getListOfBookingsTest(){
        List<Booking> bookings = bookingRepo.findAll();
        Assertions.assertThat(bookings.size()).isGreaterThan(0);
    }
    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateBookingTest(){
        Booking booking = bookingRepo.findById(50).get();
        booking.setBookingDate(Date.valueOf("2023-02-20"));
        Booking bookingUpdated = bookingRepo.save(booking);
        Assertions.assertThat(bookingUpdated.getBookingDate()).isEqualTo("2023-02-20");
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteBookingTest(){
        Booking booking = bookingRepo.findById(50).get();
        bookingRepo.delete(booking);

        Booking booking1 = null;
        Optional<Booking> optionalBooking = bookingRepo.findById(50);
        if (optionalBooking.isPresent()){
            booking1 = optionalBooking.get();
        }
        Assertions.assertThat(booking1).isNull();
    }
}
