package test_AuthenticationService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import data.UserRepository;
import exception.UserNotFoundException;
import model.User;
import service.AuthenticationService;

@ExtendWith(MockitoExtension.class)
class Test_AuthenticationService {
	AuthenticationService authenticationService;

	@Mock
	UserRepository mockUserRepository;

	@BeforeEach
	void init() {
		authenticationService = new AuthenticationService(mockUserRepository);
	}

	@Test
	void test_authenticate() throws UserNotFoundException {
		User testUser = new User("Stephen", "Star", "u", "p", "s@email.com", new ArrayList<>());

		when(mockUserRepository.validate("username", "password")).thenReturn(true);
		when(mockUserRepository.findByUsername("username")).thenReturn(testUser);

		User user = authenticationService.authenticate("username", "password");

		assertEquals(testUser, user);
	}

	@Test
	void test_findById() throws UserNotFoundException {
		User testUser = new User();
		when(mockUserRepository.findById(105)).thenReturn(testUser);

		User userFound = authenticationService.findById(105);
		assertEquals(testUser, userFound);
	}

}
