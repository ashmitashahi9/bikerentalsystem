package com.system.bike_rental_system.repo;

import com.system.bike_rental_system.entity.Booking;
import com.system.bike_rental_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Integer> {
    void deleteBookingsByUserId(User id);

    List<Booking> findAllByUserId(User user);

    @Query(value = "SELECT * from booking where booking_date=?1 or release_date=?1", nativeQuery = true)
    List<Booking> findBookingByDate(Date date);
}
