package com.example.GymLifeServer.controller;

import com.example.GymLifeServer.model.User.Users;
import com.example.GymLifeServer.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/user/get-all")
    public ArrayList<Users> getAllUsers()
    {
        ArrayList<Users> users = new ArrayList<>();
        Streamable.of(userRepository.findAll())
                .forEach(users::add);
        return users;
    }

    @GetMapping("/user/get/{userId}")
    public Users getUserById(@PathVariable int userId)
    {
        return userRepository.findById(userId).get();

    }

    @DeleteMapping("/user/delete/{userId}")
    public void deleteUserById(@PathVariable int userId)
    {
        userRepository.deleteById(userId);
    }

    @PostMapping("/user/save")
    public Users saveUser(@RequestBody Users user)
    {
        return userRepository.save(user);
    }
}
