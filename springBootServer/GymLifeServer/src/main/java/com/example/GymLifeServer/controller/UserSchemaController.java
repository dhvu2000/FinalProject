package com.example.GymLifeServer.controller;

import com.example.GymLifeServer.model.User.UserAnalysedInfor;
import com.example.GymLifeServer.model.User.UserSchema;
import com.example.GymLifeServer.model.repository.UserAnalysedInforRepository;
import com.example.GymLifeServer.model.repository.UserSchemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserSchemaController {

    @Autowired
    UserSchemaRepository userSchemaRepository;

    @PostMapping("/user-schema/save")
    public UserSchema saveUserSchema(@RequestBody UserSchema schema)
    {
        return userSchemaRepository.save(schema);
    }

    @DeleteMapping("/user-schema/delete/{id}")
    public void deleteSchema(@PathVariable int id)
    {
         userSchemaRepository.deleteById(id);
    }
}
