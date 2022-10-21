package com.example.GymLifeServer.model.repository;

import com.example.GymLifeServer.model.WorkOutUnit.WorkOutUnit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkOutUnitRepository extends CrudRepository<WorkOutUnit, Integer> {
}
