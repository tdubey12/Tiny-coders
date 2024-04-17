package org.launchcode.library.controllers;

import jakarta.validation.Valid;
import org.launchcode.library.data.RoleRepository;
import org.launchcode.library.data.UserRepository;
import org.launchcode.library.models.Role;
import org.launchcode.library.models.User;
import org.launchcode.library.models.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;

@Controller
public class AuthenticationController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    //interface for encoding password it impl Bcrypt password encoder
    @Autowired
    private PasswordEncoder passwordEncoder;

    // handler method to handle home page request

    @GetMapping("/index")
    public String home() {
        return "user/index";
    }

    // handler method to handle login request

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    // handler method to handle landing page request

    @GetMapping("/landing")
    public String landing() {
        return "user/homepage";
    }//need to change to megha landing page.

    // handler method to handle user registration form request

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // creates model object to store form data
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "user/register";
    }

    // handler method to handle user registration form submit request

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model) {
        //check if user email is not registered already

        User userEmailCheck = userRepository.findByEmail(userDto.getEmail());
        if (userEmailCheck != null && userEmailCheck.getEmail() != null && !userEmailCheck.getEmail().isEmpty()) {
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        //check if user username is not registered already

        User userNameCheck = userRepository.findByUserName(userDto.getUserName());
        if (userNameCheck != null && userNameCheck.getUserName() != null && !userNameCheck.getUserName().isEmpty()) {
            result.rejectValue("userName", null,
                    "Username is already taken");
        }
        //checks error goes back to register page

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "user/register";
        }

        //this save all details to user

        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setUserName(userDto.getUserName());

        // encrypt the password using spring security

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role role = null;
        role = roleRepository.findByName("ROLE_ADMIN");
        if (role == null) {
            role = checkRoleExist("ROLE_ADMIN");
        }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
        return "redirect:/register?success";
    }

    //checks role and save
    private Role checkRoleExist(String roleName) {
        Role role = new Role();
        role.setName(roleName);
        return roleRepository.save(role);
    }
}
