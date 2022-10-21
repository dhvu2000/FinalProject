package com.example.GymLifeServer;

import com.example.GymLifeServer.controller.UserController;
import com.example.GymLifeServer.model.User.Users;
import com.example.GymLifeServer.model.WorkOutUnit.Exercise;
import com.example.GymLifeServer.model.repository.ExerciseRepository;
import com.example.GymLifeServer.model.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GymLifeServerApplicationTests {

	@Autowired
	private ExerciseRepository exerciseRepository;
	@Autowired
	private UserRepository userRepository;
	@Test
	void Test() {
		Users u = userRepository.findById(1).get();
		Exercise exercise = new Exercise("push up",u,null,"introduction","guide",null);
		exerciseRepository.save(exercise);
	}

}
