package org.launchcode.library.controllers;

import org.launchcode.library.models.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {

    // handler method to handle home page request

    @GetMapping("/index")
    public String home(){
        return "user/index";
    }

    // handler method to handle login request

    @GetMapping("/login")
    public String login(){

        return "user/login";
    }

    // handler method to handle landing page request

    @GetMapping("/landing")
    public String landing(){
        return "user/homepage";
    }

    // handler method to handle user registration form request

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        // creates model object to store form data
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "user/register";
    }


}
