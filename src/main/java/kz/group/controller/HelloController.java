package kz.group.controller;

import jakarta.validation.Valid;
import kz.group.Security.PersonDetails;
import kz.group.entity.Person;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class HelloController {
//    @GetMapping("/hello")
//    public String sayHello() {
//            return "hello";
//    }

    @GetMapping("/hello")
    public String currentUser(@ModelAttribute("user") @Valid Person person, BindingResult result, Model model) {

        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) loggedInUser.getPrincipal();
        String username = personDetails.getUsername();
        model.addAttribute("username", username);
        return "hello";
    }

    @GetMapping("/showUserInfo")
    public String showUserInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) auth.getPrincipal();
        System.out.println(personDetails.getPerson());
        return "hello";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }

}
