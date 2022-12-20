package com.example.GymLifeServer.controller;

import com.example.GymLifeServer.model.User.Users;
import com.example.GymLifeServer.model.WorkOutUnit.WorkOutSet.WorkOutRecord;
import com.example.GymLifeServer.model.repository.WorkOutRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class WorkOutRecordController {

    @Autowired
    private WorkOutRecordRepository workOutRecordRepository;

    @GetMapping("/work-out-record/get-by-user/{userId}")
    private ArrayList<WorkOutRecord> getWorkOutRecordByUser(@PathVariable int userId)
    {
        ArrayList<WorkOutRecord> records = new ArrayList<>();
        ArrayList<WorkOutRecord> res = new ArrayList<>();
        Streamable.of(workOutRecordRepository.findAll())
                .forEach(records::add);
        for(WorkOutRecord i: records)
        {
            Users u = i.getUser();
            if(u.getId() == userId)
            {
                res.add(i);
            }
        }
        return res;
    }

    @PostMapping("/work-out-record/save")
    private WorkOutRecord saveWorkOutRecord(@RequestBody WorkOutRecord routineAct)
    {
        return workOutRecordRepository.save(routineAct);
    }
}
