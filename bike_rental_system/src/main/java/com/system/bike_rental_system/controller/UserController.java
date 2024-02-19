package com.system.bike_rental_system.controller;

import com.system.bike_rental_system.entity.User;
import com.system.bike_rental_system.pojo.PasswordChangePojo;
import com.system.bike_rental_system.pojo.UserPojo;
import com.system.bike_rental_system.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String getLoginPage(Model model, @RequestParam(value = "error", required = false) String error){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password!");
        }
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("user", new UserPojo());
            return "/login";
        }
        return "redirect:/home";
    }

    @PostMapping("/save")
    public String saveUser(@Valid UserPojo userPojo, RedirectAttributes redirectAttributes){
        userService.saveUser(userPojo);
        redirectAttributes.addFlashAttribute("message", "Account Created Successfully!");
        return "redirect:/login";
    }

    @PostMapping("/updateGeneral/{id}")
    public String updateGeneral(@Valid UserPojo userPojo, RedirectAttributes redirectAttributes) throws IOException {
        userService.updateGeneral(userPojo);
        redirectAttributes.addFlashAttribute("message", "Account Details Updated Successfully!");

        return "redirect:/account";
    }

    @PostMapping("/updateDocs/{id}")
    public String updateDocs(@Valid UserPojo userPojo, RedirectAttributes redirectAttributes) throws IOException {
        userService.updateDocs(userPojo);
        redirectAttributes.addFlashAttribute("message", "Documents Updated Successfully!");
        return "redirect:/account";
    }

    @GetMapping("/account")
    public String getPage(Model model, Principal principal, Authentication authentication){
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("loggedUser", user);
        model.addAttribute("passwordChangePojo", new PasswordChangePojo());
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ADMIN")) {
                return "admin/settings";
            }
        }
        return "accountDetails";
    }

    @GetMapping("/logout")
    public String logout(Authentication authentication, Model model){
        if (authentication.isAuthenticated()) {
            SecurityContextHolder.clearContext();
        }
        model.addAttribute("message", "Logged Out Successfully!");
        return "/login";
    }

    @PostMapping("/change")
    public String changePassword(@Valid @ModelAttribute PasswordChangePojo passwordChangePojo, BindingResult bindingResult,
                             Authentication authentication, RedirectAttributes redirectAttributes) {
        if (authentication.isAuthenticated()) {
            passwordChangePojo.setEmail(authentication.getName());
            if (bindingResult.hasErrors()) {
                return "redirect:/account";
            }
            userService.changePassword(passwordChangePojo);
            SecurityContextHolder.clearContext();
        }
        redirectAttributes.addFlashAttribute("message", "Your Password was Updated!");

        return "redirect:/account";
    }

    @PostMapping("/delete")
    public String deleteUser(PasswordChangePojo passwordChangePojo, Authentication authentication){
        if (authentication.isAuthenticated()){
            passwordChangePojo.setEmail(authentication.getName());
            userService.deleteAccount(passwordChangePojo);
            return "redirect:/logout";
        }

        return "redirect:/account";
    }

    @PostMapping("/change-password")
    public String changePassword(Model model, @Valid UserPojo userPojo){
        userService.processPasswordResetRequest(userPojo.getEmail());
        model.addAttribute("message","Your new password has been sent to your email Please " +
                "check your inbox");
        return "redirect:/login";
    }
}
