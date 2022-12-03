package com.example.GymLifeServer.model.repository;

import com.example.GymLifeServer.model.User.UserSchema;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSchemaRepository extends CrudRepository<UserSchema, Integer> {
}
