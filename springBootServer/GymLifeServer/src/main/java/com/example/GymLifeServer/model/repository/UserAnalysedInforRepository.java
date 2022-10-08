package com.example.GymLifeServer.model.repository;

import com.example.GymLifeServer.model.User.UserAnalysedInfor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAnalysedInforRepository extends CrudRepository<UserAnalysedInfor, Integer> {
}
