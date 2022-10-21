package com.example.GymLifeServer.model.repository;

import com.example.GymLifeServer.model.WorkOutUnit.Exercise;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends CrudRepository<Exercise, Integer> {

}
