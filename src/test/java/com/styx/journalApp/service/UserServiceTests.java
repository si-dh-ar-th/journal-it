package com.styx.journalApp.service;

import com.styx.journalApp.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class UserServiceTests {

	@Autowired
	private UserService userService;

	@Test
	void getAllEntriesTest() {
		List<User> allEntries = userService.getAllEntries();
		Assertions.assertNotNull(allEntries);
	}

	@ParameterizedTest
	@CsvSource({
			"sidhjain",
			"john_doe"
	})

//	Another way is using ArgumentSource. Watch JUnit video for that

// Todo: pass a proper enum in this
//	@EnumSource({
//			"sidhjain",
//			"john_doe"
//	})

//	@ValueSource(strings = {
//			"sidhjain",
//			"john_doe"
//	})

	void findByUserNameTest(String username) {
		User user = userService.findByUserName(username);
		Assertions.assertNotNull(user);
	}

}
