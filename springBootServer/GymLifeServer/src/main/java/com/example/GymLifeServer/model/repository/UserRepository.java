package com.example.GymLifeServer.model.repository;

import com.example.GymLifeServer.model.User.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<Users, Integer> {
}
