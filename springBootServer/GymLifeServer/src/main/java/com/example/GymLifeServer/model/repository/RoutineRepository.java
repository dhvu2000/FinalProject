package com.example.GymLifeServer.model.repository;

import com.example.GymLifeServer.model.WorkOutUnit.Routine.Routine;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutineRepository extends CrudRepository<Routine, Integer> {

}
