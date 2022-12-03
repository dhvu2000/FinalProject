package com.example.GymLifeServer.controller;

import com.example.GymLifeServer.model.User.Users;
import com.example.GymLifeServer.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class UserController {

    private String EMAIL_EXIST = "Email has already existed";
    private String USERNAME_EXIST = "Username has already existed";
    private int EMAIL_EXIST_CODE = -2;
    private int USERNAME_EXIST_CODE = -1;
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

    private boolean checkName(@PathVariable String name)
    {
        ArrayList<Users> users = new ArrayList<>();
        Streamable.of(userRepository.findAll())
                .forEach(users::add);
        for(Users i: users)
        {
            if(i.getUsername().equals(name))
                return false;
        }
        return true;

    }

    private boolean checkEmail(@PathVariable String email)
    {
        ArrayList<Users> users = new ArrayList<>();
        Streamable.of(userRepository.findAll())
                .forEach(users::add);
        for(Users i: users)
        {
            if(i.getEmail().equals(email))
               return false;
        }
        return true;

    }

    @DeleteMapping("/user/delete/{userId}")
    public void deleteUserById(@PathVariable int userId)
    {
        userRepository.deleteById(userId);
    }

    @PostMapping("/user/save")
    public Users saveUser(@RequestBody Users user)
    {
        if (!checkName(user.getUsername()))
        {
            Users res = new Users();
            res.setId(USERNAME_EXIST_CODE);
            res.setUsername(USERNAME_EXIST);
            return  res;
        }
        if(!checkEmail(user.getEmail()))
        {
            Users res = new Users();
            res.setId(EMAIL_EXIST_CODE);
            res.setUsername(EMAIL_EXIST);
            return  res;
        }
        return userRepository.save(user);
    }

    @PostMapping("/user/log_in")
    public Users logInUser(@RequestBody Users u)
    {
        String username = u.getUsername();
        String password = u.getPassword();
        ArrayList<Users> users = new ArrayList<>();
        Users res = new Users();
        Streamable.of(userRepository.findAll())
                .forEach(users::add);
        for(Users i: users)
        {
            if((i.getUsername().equals(username) || i.getEmail().equals(username)) && (i.getPassword().equals(password)))
            {
                res = i;
            }
        }
        return res;
    }




}
