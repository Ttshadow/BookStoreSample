package data;

import java.util.ArrayList;
import java.util.List;

import model.User;

public class UserArrayListRepository implements UserRepository {
	private List<User> users;
	public static int id;

	public UserArrayListRepository() {
		users = new ArrayList<>();
	}

	public UserArrayListRepository(List<User> users) {
		super();
		this.users = users;
	}

	public static int generateId() {
		return ++id;
	}

	public User save(User user) {

		User userFound = findByUsername(user.getUsername());
		if (userFound == null) {
			user.setUserId(generateId());
			users.add(user);
		} else {
			User existUser = findById(user.getUserId());
			existUser.setFirstName(user.getFirstName());
			existUser.setLastName(user.getLastName());
			existUser.setUsername(user.getUsername());
			existUser.setPassword(user.getPassword());
			existUser.setEmail(user.getEmail());
			existUser.setOrders(user.getOrders());
		}

		return user;
	}

	public User delete(User user) {
		// TODO Auto-generated method stub
		User deleteUser = user;
		users.remove(user);
		return deleteUser;
	}

	@Override
	public User findById(int id) {
		// TODO Auto-generated method stub
		for (User user : users) {
			if (user.getUserId() == id) {
				return user;
			}
		}
		return null;
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return users;
	}

	@Override
	public boolean validate(String username, String password) {
		// TODO Auto-generated method stub
		for (User user : users) {
			if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		for (User user : users) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
