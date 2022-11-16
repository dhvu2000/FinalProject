package com.example.GymLifeServer.controller;

import com.example.GymLifeServer.model.WorkOutUnit.Routine.Routine;
import com.example.GymLifeServer.model.WorkOutUnit.WorkOutSet.SetExercise;
import com.example.GymLifeServer.model.repository.SetExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Set;

@RestController
public class SetExerciseController {

    @Autowired
    SetExerciseRepository setExerciseRepository;


    @PostMapping("/set-exercise/save")
    private SetExercise saveExercise(@RequestBody SetExercise setExercise)
    {
        return setExerciseRepository.save(setExercise);
    }

    @PostMapping("/set-exercise/save-all")
    private ArrayList<SetExercise> saveAllExercise(@RequestBody ArrayList<SetExercise> setExercises)
    {
         return (ArrayList<SetExercise>) setExerciseRepository.saveAll(setExercises);
    }

    @DeleteMapping("/set-exercise/delete/{Id}")
    public void deleteSetExerciseById(@PathVariable int Id)
    {
        setExerciseRepository.deleteById(Id);
    }

    @DeleteMapping("/set-exercise/delete-all/{ids}")
    public void deleteAllSetExerciseById(@PathVariable String ids)
    {
        ArrayList<Integer> deletedList =  new ArrayList<>();
        String[] ss = ids.split(",");
        for(int i = 0; i < ss.length; i++)
        {
            int num = Integer.parseInt(ss[i]);
            deletedList.add(num);
        }
        setExerciseRepository.deleteAllById(deletedList);
    }
}
