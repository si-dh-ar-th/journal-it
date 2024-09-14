package com.styx.journalApp.service;

import com.styx.journalApp.entity.User;
import com.styx.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Optional;

class UserServiceTests {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

//	@Test
//	void getAllEntriesTest() {
//		List<User> allEntries = userService.getAllEntries();
//		Assertions.assertNotNull(allEntries);
//	}

//  there is also @CsvFileSource that takes path of csv
//	@ParameterizedTest
//	@CsvSource({
//			"sidhjain",
//			"john_doe"
//	})

//	Another way is using ArgumentSource. Watch JUnit video for that
//  Todo: pass a proper enum in this
//	@EnumSource({
//			"sidhjain",
//			"john_doe"
//	})

//	@ValueSource(strings = {
//			"sidhjain",
//			"john_doe"
//	})

	@BeforeEach
	void initialize(){
		MockitoAnnotations.initMocks(this);
	}

	@Test
    void findByUserNameTest() {
		when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(
				User.builder().userName("hakuna_matata").password("ebiebijabajaba").roles(new ArrayList<>()).build()
		);
		User user = userService.findByUserName(ArgumentMatchers.anyString());
		Assertions.assertNotNull(user);
	}

}
