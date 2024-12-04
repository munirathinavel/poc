package com.design.poc.controller;

import com.design.poc.model.User;
import com.design.poc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User saveUser(@RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping("/{userId}")
    public User findById(@PathVariable("userId") long userId) {
        Optional<User> userOptional =  userService.findById(userId);
        return userOptional.isPresent() ? userOptional.get() : null;
    }

    @GetMapping("/{email}")
    public User findByEmail(String email) {
        Optional<User> userOptional =  userService.findByEmail(email);
        return userOptional.isPresent() ? userOptional.get() : null;
    }

    @GetMapping
    public List<User> findAllUsers() {
        return  userService.findAllUsers();
    }


}
