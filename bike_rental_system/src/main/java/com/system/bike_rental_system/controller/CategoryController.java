package com.system.bike_rental_system.controller;

import com.system.bike_rental_system.entity.Bike;
import com.system.bike_rental_system.entity.Category;
import com.system.bike_rental_system.pojo.CategoryPojo;
import com.system.bike_rental_system.services.BikeService;
import com.system.bike_rental_system.services.BookingService;
import com.system.bike_rental_system.services.CategoryService;
import com.system.bike_rental_system.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class CategoryController {

    private final CategoryService categoryService;
    private final BikeService bikeService;
    private final UserService userService;
    private final BookingService bookingService;

    public List<Bike> getBikeList(@PathVariable("id") Integer categoryId){
        return bikeService.fetchByCategory(categoryId);
    }

    public List<Bike> getAllBikeList(@PathVariable("id") Integer categoryId){
        return bikeService.fetchAllByCategory(categoryId);
    }

    @GetMapping("/home")
    public String getCategoryList(Model model, Principal principal, Authentication authentication){
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ADMIN")) {
                return "redirect:/bookings/"+LocalDate.now();
            }
        }

        model.addAttribute("loggedUser", userService.findByEmail(principal.getName()));
        List<Category> category = categoryService.fetchAll();

        Map<Category, List<Bike>> map = new HashMap<>();
        for (Category value:category) {
            map.put(value, getBikeList(value.getId()));
        }

        List<Bike> mostRented = bikeService.fetchMostRented();
        model.addAttribute("mostRented", mostRented);

        model.addAttribute("categoryBike", map);

        return "/home";
    }

    @GetMapping("/category/{id}")
    public String getIndividualPage(@PathVariable("id") Integer id, Model model, Principal principal){
        model.addAttribute("loggedUser", userService.findByEmail(principal.getName()));
        Category category = categoryService.fetchById(id);
        model.addAttribute("category", new CategoryPojo(category));

        List<Bike> bikeList = getAllBikeList(id);
        model.addAttribute("bikeList", bikeList);
        return "category";
    }

    @GetMapping("/search/{searchTerm}")
    public String searchResults(@PathVariable String searchTerm, Model model, Principal principal, Authentication authentication){
        model.addAttribute("loggedUser", userService.findByEmail(principal.getName()));
        model.addAttribute(searchTerm);

        List<Category> category = categoryService.fetchAll();
        List<Bike> allBikes = new ArrayList<>();
        for (Category value:category) {
            allBikes.addAll(getAllBikeList(value.getId()));
        }

        for (int i=0; i<allBikes.size();){
            try {
                if (!allBikes.get(i).getBikeName().substring(0, searchTerm.length()).toLowerCase().equalsIgnoreCase(searchTerm) && !allBikes.get(i).getBrandName().substring(0, searchTerm.length()).equalsIgnoreCase(searchTerm)) {
                    allBikes.remove(i);
                } else {
                    i++;
                }
            } catch (StringIndexOutOfBoundsException ex){
                allBikes.remove(i);
            }
        }

        model.addAttribute("bikeList", allBikes);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ADMIN")) {
                return "/admin/search";
            }
        }

        return "search";
    }
}
