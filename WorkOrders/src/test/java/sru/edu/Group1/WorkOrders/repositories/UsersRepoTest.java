package sru.edu.Group1.WorkOrders.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import sru.edu.group1.workorders.domain.Role;
import sru.edu.group1.workorders.domain.Rooms;
import sru.edu.group1.workorders.domain.Users;
import sru.edu.group1.workorders.repository.UserRepository;

public class UsersRepoTest {
	@Autowired
	private UserRepository testRepo;

	private Users user;

	private Users testUser;

	private Set<Role> roles = new HashSet<>();
	/**
	 * Setups up the false building to test
	 */
	@BeforeEach
	void config() throws Exception {
	user = new Users();
	testUser = new Users();
	user.setId(1);
	user.setEmail("testRole@sru.edu");
	user.setFirstName("John");
	user.setLastName("Doe");
	user.setRoles(roles);
	user.setPassword("Password!@");
	testRepo.save(user);
	}
	/**
	 * Tears down the repo after every test
	 */
	@AfterEach
	void tearDown() {
		testRepo.deleteAll();
	}
	/**
	 * Tests the find by Id function
	 */
	@Test
	void testFindById() {
		if(testRepo.findById(user.getId()) != null) {
		testUser = testRepo.findById(user.getId());
		assertEquals(user,testUser);
		}
	}
	/**
	 * Test the find by Id function if the Id does not exist
	 */
	@Test
	void testFindByIdNotExist() {
		testUser = testRepo.findById(3);
		assertNull(testUser);
	}
	/**
	 * tests the find by email function
	 */
	@Test
	void testFindByEmail() {
		if(testRepo.findByEmail(user.getEmail()) !=null) {
		testUser = testRepo.findByEmail(user.getEmail());
		assertEquals(user, testUser);
		}
	}
}
