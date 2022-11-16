package com.example.GymLifeServer.model.repository;

import com.example.GymLifeServer.model.WorkOutUnit.WorkOutSet.SetExercise;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SetExerciseRepository extends CrudRepository<SetExercise, Integer> {
}
