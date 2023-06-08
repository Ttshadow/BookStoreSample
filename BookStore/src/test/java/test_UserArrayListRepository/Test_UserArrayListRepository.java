package test_UserArrayListRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import data.UserArrayListRepository;
import model.User;

class Test_UserArrayListRepository {

	User user1;
	User user2;
	User user3;

	UserArrayListRepository userArrayListRepo;

	@BeforeEach
	void init() {
		user1 = new User("Harry", "Potter", "harryPotter", "hp123456", "hp@email.com", null);
		user2 = new User("Hermione", "Granger", "hermioneGranger", "hg123456", "hg@email.com", null);
		user3 = new User("Ron", "Weasley", "ronWeasley", "rw123456", "rw@email.com", null);
		userArrayListRepo = new UserArrayListRepository();
		userArrayListRepo.save(user1);
		userArrayListRepo.save(user2);
		userArrayListRepo.save(user3);
	}

	@Test
	void test_validate() {
		assertTrue(userArrayListRepo.validate("harryPotter", "hp123456"));
		assertFalse(userArrayListRepo.validate("harryPo", "hp123456"));
	}

	@Test
	void test_findByUsername() {
		assertEquals(user2, userArrayListRepo.findByUsername("hermioneGranger"));
		assertEquals(null, userArrayListRepo.findByUsername("abc"));
	}

	@Test
	void test_save() {
		// save new user
		User user4 = new User("Sirius", "Snape", "siriusSnape", "ss123456", "ss@email.com", null);
		assertEquals(3, userArrayListRepo.findAll().size());
		assertEquals(user4, userArrayListRepo.save(user4));
		assertEquals(4, userArrayListRepo.findAll().size());

		// save existing user
		user1.setFirstName("Stephen");
		userArrayListRepo.save(user1);
		assertEquals("Stephen", userArrayListRepo.findById(user1.getUserId()).getFirstName());
		assertEquals(4, userArrayListRepo.findAll().size());
	}

	@Test
	void test_delete() {
		assertEquals(3, userArrayListRepo.findAll().size());
		assertEquals(user3, userArrayListRepo.delete(user3));
		assertEquals(2, userArrayListRepo.findAll().size());
	}

	@Test
	void test_generateId() {
		int currentId = UserArrayListRepository.id;
		int newId = UserArrayListRepository.generateId();
		assertEquals(newId, currentId + 1);
	}

	@Test
	void test_findById() {
		assertEquals(user1, userArrayListRepo.findById(user1.getUserId()));
	}

	@Test
	void test_findAll() {
		assertTrue(userArrayListRepo.findAll() instanceof List<User>);
		assertEquals(3, userArrayListRepo.findAll().size());
	}
}
