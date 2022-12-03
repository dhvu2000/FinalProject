package com.example.GymLifeServer.controller;

import com.example.GymLifeServer.model.User.UserAnalysedInfor;
import com.example.GymLifeServer.model.User.Users;
import com.example.GymLifeServer.model.repository.UserAnalysedInforRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAnalysedInforController {

    @Autowired
    UserAnalysedInforRepository userAnalysedInforRepository;

    @PostMapping("/user-infor/save")
    public UserAnalysedInfor saveUserInfor(@RequestBody UserAnalysedInfor infor)
    {
        return userAnalysedInforRepository.save(infor);
    }
}
