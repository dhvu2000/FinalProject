package com.example.GymLifeServer.controller;


import com.example.GymLifeServer.model.User.Users;
import com.example.GymLifeServer.model.WorkOutUnit.Routine.Routine;
import com.example.GymLifeServer.model.WorkOutUnit.Routine.RoutineDay;
import com.example.GymLifeServer.model.WorkOutUnit.WorkOutSet.SetExercise;
import com.example.GymLifeServer.model.WorkOutUnit.WorkOutSet.WorkOutSet;
import com.example.GymLifeServer.model.repository.SetExerciseRepository;
import com.example.GymLifeServer.model.repository.WorkOutSetRepository;
import com.example.GymLifeServer.model.repository.WorkOutUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class WorkOutSetController {

    @Autowired
    private WorkOutSetRepository workOutSetRepository;

    @Autowired
    private WorkOutUnitRepository workOutUnitRepository;

    @Autowired
    private SetExerciseRepository setExerciseRepository;

    @GetMapping("/set/get-by-user/{userId}")
    private ArrayList<WorkOutSet> getWorkOutSetByUserId(@PathVariable int userId)
    {
        ArrayList<WorkOutSet> sets = new ArrayList<>();
        ArrayList<WorkOutSet> res = new ArrayList<>();
        Streamable.of(workOutSetRepository.findAll())
                .forEach(sets::add);
        for(WorkOutSet i: sets)
        {
            Users u = i.getCreatedBy();
            if(u.getId() == userId || u.getType().equals("admin"))
            {
                if(i.getType().equals("single"))
                {
                    res.add(i);
                }
            }
        }
        return res;
    }

    @PostMapping("/set/save")
    private WorkOutSet saveWorkOutSet(@RequestBody WorkOutSet workOutSet)
    {
        WorkOutSet w = workOutSetRepository.save(workOutSet);
        ArrayList<SetExercise> setExercises = new ArrayList<>();
        if (workOutSet.getExercises()!= null) setExercises.addAll(workOutSet.getExercises());
        for(SetExercise j : setExercises)
        {
            j.setWorkOutSet(w);
            setExerciseRepository.save(j);
        }
        WorkOutSet response = workOutSetRepository.findById(w.getId()).get();
        return  response;
    }

    @DeleteMapping("/set/delete/{Id}")
    public void deleteWorkOutSet(@PathVariable int Id)
    {
        workOutUnitRepository.deleteById(Id);
    }
}
