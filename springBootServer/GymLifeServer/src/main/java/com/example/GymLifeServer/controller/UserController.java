package com.example.GymLifeServer.controller;

import com.example.GymLifeServer.model.User.Users;
import com.example.GymLifeServer.model.User.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class UserController {

    @Autowired
    private UserDAO userDAO;

    @GetMapping("/user/get-all")
    public ArrayList<Users> getAllUsers()
    {
        return userDAO.findAll();
    }

    @PostMapping("/user/save")
    public Users saveUser(@RequestBody Users user)
    {
        return userDAO.save(user);
    }
}
