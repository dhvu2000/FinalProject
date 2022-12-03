package com.example.GymLifeServer.model.repository;

import com.example.GymLifeServer.model.WorkOutUnit.Routine.RoutineAct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutineActRepository extends CrudRepository<RoutineAct, Integer> {
}
