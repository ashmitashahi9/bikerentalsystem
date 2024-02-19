package com.system.bike_rental_system.controller;

import com.system.bike_rental_system.entity.Bike;
import com.system.bike_rental_system.entity.Booking;
import com.system.bike_rental_system.entity.User;
import com.system.bike_rental_system.pojo.BookingPojo;
import com.system.bike_rental_system.services.BikeService;
import com.system.bike_rental_system.services.BookingService;
import com.system.bike_rental_system.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class BookingController {
    private final BookingService bookingService;

    private final UserService userService;

    private final BikeService bikeService;

    @PostMapping("/book/{bikeId}")
    public String bookBike(@PathVariable Integer bikeId, @Valid BookingPojo bookingPojo, Principal principal, RedirectAttributes redirectAttributes) throws ParseException {
        Integer id = userService.findByEmail(principal.getName()).getId();
        bookingPojo.setUserId(id);
        bookingPojo.setBikeId(bikeId);
        bookingPojo.setStatus("Booked");
        bookingService.saveBooking(bookingPojo);

        Bike updatedBike = bikeService.fetchById(bikeId);
        updatedBike.setAvailableNo(updatedBike.getAvailableNo() - 1);
        bikeService.saveBikeByEntity(updatedBike);

        redirectAttributes.addFlashAttribute("message", "Your booking was placed. Visit us at (some place) to collect your bike on the selected date.") ;
        return "redirect:/home";
    }

    @GetMapping("/my-bookings")
    public String getBookedHistory(Principal principal, Model model){
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("loggedUser", user);
        model.addAttribute("reservations", bookingService.getBookingHistory(user));
        return "/myReservations";
    }

    @GetMapping("/booking/cancel/{id}")
    public String cancelBooking(@PathVariable Integer id, RedirectAttributes redirectAttributes, Authentication authentication){
        Bike updatedBike = bikeService.fetchById(bookingService.findById(id).getBikeId().getId());
        updatedBike.setAvailableNo(updatedBike.getAvailableNo() + 1);
        bikeService.saveBikeByEntity(updatedBike);

        bookingService.deleteBooking(id);

        redirectAttributes.addFlashAttribute("errorMessage", "Your Booking was Cancelled!");
        return "redirect:/my-bookings";
    }

    @GetMapping("booking/{id}/{status}")
    public String updateStatus(@PathVariable Integer id, @PathVariable String status) {
        Booking booking = bookingService.findById(id);
        booking.setStatus(status);
        bookingService.saveBookingByEntity(booking);

        if (Objects.equals(status, "Taken")){
            Bike updatedBike = bikeService.fetchById(booking.getBikeId().getId());
            updatedBike.setRentedNumber(updatedBike.getRentedNumber() + 1);
            bikeService.saveBikeByEntity(updatedBike);
        }

        if (Objects.equals(status, "Returned")){
            Bike updatedBike = bikeService.fetchById(booking.getBikeId().getId());
            updatedBike.setAvailableNo(updatedBike.getAvailableNo() + 1);
            bikeService.saveBikeByEntity(updatedBike);
        }

        return "redirect:/bookings/"+LocalDate.now();
    }
}
