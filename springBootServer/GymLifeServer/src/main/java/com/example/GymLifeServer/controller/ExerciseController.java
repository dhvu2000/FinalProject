package com.example.GymLifeServer.controller;

import com.example.GymLifeServer.model.User.Users;
import com.example.GymLifeServer.model.WorkOutUnit.Exercise;
import com.example.GymLifeServer.model.repository.ExerciseRepository;
import com.example.GymLifeServer.model.repository.WorkOutUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class ExerciseController {

    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private WorkOutUnitRepository workOutUnitRepository;


    @GetMapping("/exercise/get-by-user/{userId}")
    private ArrayList<Exercise> getExercisesByUserId(@PathVariable int userId)
    {
        ArrayList<Exercise> exercises = new ArrayList<>();
        ArrayList<Exercise> res = new ArrayList<>();
        Streamable.of(exerciseRepository.findAll())
                .forEach(exercises::add);
        for(Exercise i: exercises)
        {
            Users u = i.getCreatedBy();
            if(u.getId() == userId || i.getType().equals("admin"))
            {
                res.add(i);
            }
        }
        return res;
    }

    @GetMapping("/exercise/get-all")
    private ArrayList<Exercise> getAllExercise()
    {
        ArrayList<Exercise> exercises = new ArrayList<>();
        Streamable.of(exerciseRepository.findAll())
                .forEach(exercises::add);
        return exercises;
    }


    @PostMapping("/exercise/save")
    private Exercise saveExercise(@RequestBody Exercise exercise)
    {
        return exerciseRepository.save(exercise);
    }

    @DeleteMapping("/exercise/delete/{exerciseId}")
    public void deleteUserById(@PathVariable int exerciseId)
    {
         workOutUnitRepository.deleteById(exerciseId);
    }
}
