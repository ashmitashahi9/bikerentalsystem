package com.system.bike_rental_system.services.impl;

import com.system.bike_rental_system.entity.Bike;
import com.system.bike_rental_system.entity.Booking;
import com.system.bike_rental_system.entity.User;
import com.system.bike_rental_system.pojo.BookingPojo;
import com.system.bike_rental_system.repo.BikeRepo;
import com.system.bike_rental_system.repo.BookingRepo;
import com.system.bike_rental_system.repo.UserRepo;
import com.system.bike_rental_system.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepo bookingRepo;
    private final UserRepo userRepo;
    private final BikeRepo bikeRepo;

    @Override
    public void saveBooking(BookingPojo bookingPojo){
        Booking booking = new Booking();

        booking.setUserId(userRepo.findById(bookingPojo.getUserId()).orElseThrow());
        booking.setBikeId(bikeRepo.findById(bookingPojo.getBikeId()).orElseThrow());
        booking.setBookingDate(Date.valueOf(bookingPojo.getBookingDate()));
        booking.setReleaseDate(Date.valueOf(bookingPojo.getReleaseDate()));
        bookingRepo.save(booking);
    }

    @Override
    public void saveBookingByEntity(Booking booking) {
        bookingRepo.save(booking);
    }

    @Override
    public List<Booking> fetchAll(String date) {

        List<Booking> allBookings = bookingRepo.findBookingByDate(Date.valueOf(date));

        for (Booking each : allBookings) {
            Bike bike = each.getBikeId();

            long diffInMillis = Math.abs(each.getReleaseDate().getTime() - each.getBookingDate().getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);

            Integer price = bike.getPriceDay();

            if (diff == 1) {
                bike.setPriceDay(bike.getPriceDay());
            } else if (diff > 1 && diff <= 7) {
                bike.setPriceDay((int) (price + (0.8 * price * (diff - 1))));
            } else if (diff > 7 && diff <= 30) {
                bike.setPriceDay((int) (price + (0.8 * price * 6) + (0.6 * price * (diff - 7))));
            } else if (diff > 30 && diff <= 122) {
                bike.setPriceDay((int) (price + (0.8 * price * 6) + (0.6 * price * 23) + (0.5 * price * (diff - 30))));
            } else if (diff > 122) {
                bike.setPriceDay((int) (price + (0.8 * price * 6) + (0.6 * price * 23) + (0.5 * price * 92) + (0.4 * price * (diff - 122))));
            }
        }

        return allBookings;
    }

    @Override
    public String deleteBooking(Integer id) {
        bookingRepo.deleteById(id);
        return "Booking Deleted";
    }

    @Override
    public Booking findById(Integer id) {
        return bookingRepo.findById(id).orElseThrow();
    }

    @Override
    public Map<String, List<Booking>> getBookingHistory(User user) {
        List<Booking> allBookings = bookingRepo.findAllByUserId(user);

        Date date = new Date(System.currentTimeMillis());

        List<Booking> upcoming = new ArrayList<>();
        List<Booking> current = new ArrayList<>();
        List<Booking> yesterday = new ArrayList<>();
        List<Booking> earlierThisWeek = new ArrayList<>();
        List<Booking> lastWeek = new ArrayList<>();
        List<Booking> earlierThisMonth = new ArrayList<>();
        List<Booking> lastMonth = new ArrayList<>();
        List<Booking> aLongTimeAgo = new ArrayList<>();

        Map<String, List<Booking>> groupByTime = new HashMap<>();

        for (Booking each : allBookings){
            Bike bike = each.getBikeId();
            bike.setBikeImageBase64(getImageBase64(bike.getBikeImage()));

            long diffInMillis = Math.abs(each.getReleaseDate().getTime() - each.getBookingDate().getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);

            bike.setRentedNumber((int) diff);
            each.setBikeId(bike);

            Integer price = bike.getPriceDay();

            if (diff==1){
                bike.setPriceDay(bike.getPriceDay());
            } else if (diff > 1 && diff <= 7){
                bike.setPriceDay((int) (price + (0.8*price*(diff-1))));
            } else if (diff > 7 && diff <= 30){
                bike.setPriceDay((int) (price + (0.8*price*6) + (0.6*price*(diff-7))));
            } else if (diff > 30 && diff <= 122){
                bike.setPriceDay((int) (price + (0.8*price*6) + (0.6*price*23) + (0.5*price*(diff-30))));
            } else if (diff > 122){
                bike.setPriceDay((int) (price + (0.8*price*6) + (0.6*price*23) + (0.5*price*92) + (0.4*price*(diff-122))));
            }

            if (each.getBookingDate().after(date)) upcoming.add(each);
            else if (each.getBookingDate().before(date) && each.getReleaseDate().after(date)) current.add(each);
            else{
                long difference = ((date.getTime()-each.getReleaseDate().getTime())
                        / (1000 * 60 * 60 * 24))
                        % 365;
                if (difference==1){
                    yesterday.add(each);
                } else if (difference<=7){
                    earlierThisWeek.add(each);
                } else if (difference<=14){
                    lastWeek.add(each);
                } else if (difference<=30){
                    earlierThisMonth.add(each);
                } else if (difference<=60){
                    lastMonth.add(each);
                } else {
                    aLongTimeAgo.add(each);
                }
            }
        }
        groupByTime.put("Upcoming", upcoming);
        groupByTime.put("Current", current);
        groupByTime.put("Yesterday", yesterday);
        groupByTime.put("Earlier This Week", earlierThisWeek);
        groupByTime.put("Last Week", lastWeek);
        groupByTime.put("Earlier This Month", earlierThisMonth);
        groupByTime.put("Last Month", lastMonth);
        groupByTime.put("A Long Time Ago", aLongTimeAgo);
        return groupByTime;
    }

    public String getImageBase64(String fileName) {
        if (fileName!=null) {
            String filePath = System.getProperty("user.dir")+"/images/bikes/";
            File file = new File(filePath + fileName);
            byte[] bytes;
            try {
                bytes = Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return Base64.getEncoder().encodeToString(bytes);
        }
        return null;
    }
}
