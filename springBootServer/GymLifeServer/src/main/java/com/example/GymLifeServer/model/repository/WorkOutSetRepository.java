package com.example.GymLifeServer.model.repository;

import com.example.GymLifeServer.model.WorkOutUnit.WorkOutSet.WorkOutSet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkOutSetRepository extends CrudRepository<WorkOutSet, Integer> {
}
