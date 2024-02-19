package com.system.bike_rental_system.pojo;

import com.system.bike_rental_system.entity.Booking;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingPojo {
    private Integer id;
    private Integer bikeId;
    private Integer userId;
    private String bookingDate;
    private String releaseDate;
    private String status;

    public BookingPojo(Booking booking) {
        this.id = booking.getId();
        this.bikeId = booking.getBikeId().getId();
        this.userId = booking.getUserId().getId();
        this.bookingDate = String.valueOf(booking.getBookingDate());
        this.releaseDate = String.valueOf(booking.getReleaseDate());
        this.status = booking.getStatus();
    }
}
