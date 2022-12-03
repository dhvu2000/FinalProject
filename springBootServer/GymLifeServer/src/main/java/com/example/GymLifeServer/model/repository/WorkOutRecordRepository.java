package com.example.GymLifeServer.model.repository;

import com.example.GymLifeServer.model.WorkOutUnit.WorkOutSet.WorkOutRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkOutRecordRepository extends CrudRepository<WorkOutRecord, Integer> {
}
