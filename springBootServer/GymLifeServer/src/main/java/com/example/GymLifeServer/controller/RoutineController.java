package com.example.GymLifeServer.controller;

import com.example.GymLifeServer.model.User.Users;
import com.example.GymLifeServer.model.WorkOutUnit.Routine.Routine;
import com.example.GymLifeServer.model.WorkOutUnit.Routine.RoutineDay;
import com.example.GymLifeServer.model.repository.RoutineDayRepository;
import com.example.GymLifeServer.model.repository.RoutineRepository;
import com.example.GymLifeServer.model.repository.WorkOutUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RoutineController {

    @Autowired
    private WorkOutUnitRepository workOutUnitRepository;
    @Autowired
    private RoutineRepository routineRepository;

    @Autowired
    private RoutineDayRepository routineDayRepository;

    @GetMapping("/routine/get-by-user/{userId}")
    private ArrayList<Routine> getRoutineByUser(@PathVariable int userId)
    {
        ArrayList<Routine> routines = new ArrayList<>();
        ArrayList<Routine> res = new ArrayList<>();
        Streamable.of(routineRepository.findAll())
                .forEach(routines::add);
        for(Routine i: routines)
        {
            Users u = i.getCreatedBy();
            if(u.getId() == userId || u.getType().equals("admin"))
            {
                res.add(i);
            }
        }
        return res;
    }

    @PostMapping("/routine/save")
    private Routine saveExercise(@RequestBody Routine routine)
    {
        return routineRepository.save(routine);
    }

    @DeleteMapping("/routine/delete/{Id}")
    public void deleteRoutineById(@PathVariable int Id)
    {
        workOutUnitRepository.deleteById(Id);
    }

}
