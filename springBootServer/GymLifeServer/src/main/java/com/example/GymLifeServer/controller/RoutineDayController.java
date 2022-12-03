package com.example.GymLifeServer.controller;

import com.example.GymLifeServer.model.WorkOutUnit.Exercise;
import com.example.GymLifeServer.model.WorkOutUnit.Routine.RoutineDay;
import com.example.GymLifeServer.model.WorkOutUnit.WorkOutSet.SetExercise;
import com.example.GymLifeServer.model.WorkOutUnit.WorkOutSet.WorkOutRecord;
import com.example.GymLifeServer.model.repository.RoutineDayRepository;
import com.example.GymLifeServer.model.repository.SetExerciseRepository;
import com.example.GymLifeServer.model.repository.WorkOutRecordRepository;
import com.example.GymLifeServer.model.repository.WorkOutSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Set;

@RestController
public class RoutineDayController {

    @Autowired
    RoutineDayRepository routineDayRepository;

    @Autowired
    SetExerciseRepository setExerciseRepository;

    @Autowired
    WorkOutSetRepository workOutSetRepository;

    @Autowired
    WorkOutRecordRepository workOutRecordRepository;

    @PostMapping("/routine-day/save")
    private RoutineDay saveRoutineDay(@RequestBody RoutineDay routineDay)
    {
        RoutineDay rd = routineDayRepository.save(routineDay);
        ArrayList<SetExercise> setExercises = new ArrayList<>();
        if (routineDay.getExercises()!= null) setExercises.addAll(routineDay.getExercises());
        for(SetExercise j : setExercises)
        {
            j.setWorkOutSet(rd);
            setExerciseRepository.save(j);
        }
        RoutineDay response = routineDayRepository.findById(rd.getId()).get();
        return  response;

    }

    @PostMapping("/routine-day/save-all")
    private ArrayList<RoutineDay> saveAllRoutineDays(@RequestBody ArrayList<RoutineDay> routineDays)
    {
        ArrayList<Integer> ids = new ArrayList<>();
        for(RoutineDay i : routineDays)
        {
            ArrayList<SetExercise> setExercises = new ArrayList<>();
            if (i.getExercises()!= null) setExercises.addAll(i.getExercises());
            RoutineDay rd = routineDayRepository.save(i);
            ids.add(rd.getId());
            for(SetExercise j : setExercises)
            {
                j.setWorkOutSet(rd);
                setExerciseRepository.save(j);
            }
        }

        ArrayList<RoutineDay> response = new ArrayList<>();
        for(int i: ids)
        {
            RoutineDay rd = routineDayRepository.findById(i).get();
            response.add(rd);
        }
        return response;
    }


    @DeleteMapping("/routine-day/delete/{Id}")
    public void deleteRoutineDayById(@PathVariable int Id)
    {
        workOutSetRepository.deleteById(Id);
    }
}
