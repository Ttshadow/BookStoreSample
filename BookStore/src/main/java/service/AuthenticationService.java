package service;

import java.util.ArrayList;
import java.util.List;

import data.UserRepository;
import exception.UserNotFoundException;
import model.User;

public class AuthenticationService {
	private UserRepository userRepository;

	public AuthenticationService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	public User authenticate(String username, String password) throws UserNotFoundException {
		if (userRepository.validate(username, password)) {
			return userRepository.findByUsername(username);
		} else {
			throw new UserNotFoundException("User Not Found.");
		}
	}

	public User findById(int id) throws UserNotFoundException {
		User findById = userRepository.findById(id);
		if (findById != null) {
			return findById;
		} else {
			throw new UserNotFoundException("User Not Found.");
		}
	}

	public List<User> findUserSameLastName(String lastname) {
		List<User> output = new ArrayList<>();

		List<User> users = userRepository.findAll();

		users.forEach(x -> {
			if (x.getLastName().equals(lastname)) {
				output.add(x);
			}
		});
		return output;
	}
}
