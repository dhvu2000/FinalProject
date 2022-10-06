package com.example.GymLifeServer;

import com.example.GymLifeServer.model.User.UserDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GymLifeServerApplicationTests {

	@Autowired
	private UserDAO userDAO;
	@Test
	void getAllUsers() {
		System.out.println(userDAO.findAll());
	}

}
