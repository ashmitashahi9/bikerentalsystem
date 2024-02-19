package com.system.bike_rental_system.controller;

import com.system.bike_rental_system.entity.Bike;
import com.system.bike_rental_system.entity.Category;
import com.system.bike_rental_system.entity.User;
import com.system.bike_rental_system.pojo.BikePojo;
import com.system.bike_rental_system.services.BikeService;
import com.system.bike_rental_system.services.BookingService;
import com.system.bike_rental_system.services.CategoryService;
import com.system.bike_rental_system.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AdminDashboardController {

    private final UserService userService;
    private final CategoryService categoryService;
    private final BikeService bikeService;
    private final BookingService bookingService;

    @GetMapping("/bookings/{date}")
    public String bookingPage(@PathVariable String date, Model model){
        model.addAttribute("booking", bookingService.fetchAll(date));
        model.addAttribute(date);
        return "/admin/dashboard";
    }

    @GetMapping("/bikes")
    public String bike(Model model){
        List<Category> category = categoryService.fetchAll();

        Map<Category, List<Bike>> map = new HashMap<>();
        for (Category value:category) {
            map.put(value, getAllBikeList(value.getId()));
        }

        List<Bike> mostRented = bikeService.fetchMostRented();
        model.addAttribute("mostRented", mostRented);

        model.addAttribute("categoryBike", map);
        return "admin/bikes";
    }

    public List<Bike> getAllBikeList(@PathVariable("id") Integer categoryId){
        return bikeService.fetchAllByCategory(categoryId);
    }

    @GetMapping("/bike/new")
    public String bikes(Model model){
        model.addAttribute("allCategory", categoryService.fetchAll());
        model.addAttribute("bike", new BikePojo());
        return "admin/eachBike";
    }

    @GetMapping("/analytics")
    public String analytics(){
        return "admin/analytics";
    }

    @GetMapping("/customers")
    public String getUserList(Model model){
        List<User> users = userService.fetchAll();
        model.addAttribute("userList", users);
        return "admin/customers";
    }

    @GetMapping("/customers/{id}")
    public String userDetails(@PathVariable Integer id, Model model){
        User user = userService.fetchById(id);
        model.addAttribute("user", user);
        return "admin/eachCustomer";
    }

    @GetMapping("/customers/{id}/{verdict}")
    public String userApproval(@PathVariable Integer id, @PathVariable Boolean verdict){
        User user = userService.fetchById(id);
        if (verdict){
            user.setStatus("Approved");
        } else {
            user.setStatus("Rejected");
        }
        userService.saveUser(user);
        return "redirect:/customers";
    }
}
