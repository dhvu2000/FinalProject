package com.example.GymLifeServer.controller;

import com.example.GymLifeServer.model.User.Users;
import com.example.GymLifeServer.model.WorkOutUnit.Routine.RoutineAct;
import com.example.GymLifeServer.model.repository.RoutineActRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class RoutineActController {

    @Autowired
    private RoutineActRepository routineActRepository;

    @GetMapping("/routine-act/get-by-user/{userId}")
    private ArrayList<RoutineAct> getRoutineActByUser(@PathVariable int userId)
    {
        ArrayList<RoutineAct> routineActs = new ArrayList<>();
        ArrayList<RoutineAct> res = new ArrayList<>();
        Streamable.of(routineActRepository.findAll())
                .forEach(routineActs::add);
        for(RoutineAct i: routineActs)
        {
            Users u = i.getUser();
            if(u.getId() == userId)
            {
                res.add(i);
            }
        }
        return res;
    }

    @PostMapping("/routine-act/save")
    private RoutineAct saveRoutineAct(@RequestBody RoutineAct routineAct)
    {
        return routineActRepository.save(routineAct);
    }
}
