package com.example.GymLifeServer.model.repository;

import com.example.GymLifeServer.model.WorkOutUnit.Routine.RoutineDay;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutineDayRepository extends CrudRepository<RoutineDay, Integer> {

}
