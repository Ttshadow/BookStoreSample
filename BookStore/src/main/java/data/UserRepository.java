package data;

import model.User;

public interface UserRepository extends Searchable<User> {
	boolean validate(String username, String password);

	User findByUsername(String username);
}
