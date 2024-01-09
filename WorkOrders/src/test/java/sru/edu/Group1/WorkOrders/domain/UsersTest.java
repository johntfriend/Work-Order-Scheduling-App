package sru.edu.Group1.WorkOrders.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.persistence.Column;
import sru.edu.group1.workorders.domain.Buildings;
import sru.edu.group1.workorders.domain.Users;
import sru.edu.group1.workorders.domain.Departments;
import sru.edu.group1.workorders.domain.Role;
import sru.edu.group1.workorders.domain.Rooms;
import sru.edu.group1.workorders.repository.BuildingRepository;
import sru.edu.group1.workorders.repository.UserRepository;
import sru.edu.group1.workorders.security.ValidPassword;
import sru.edu.group1.workorders.repository.DepartmentsRepository;
import sru.edu.group1.workorders.repository.RoomsRepository;

/**
 * This test file is for testing the functionality of the users entity
 * @author Ellie Wurst bmw1030@sru.edu
 *
 */

@SpringBootTest
public class UsersTest {

Users users;

Users trueUsers;

Role role;

@Mock
UserRepository userRepo;

private String email;
private String firstName;
private String lastName;
private String userRole;
private String resetPasswordToken;
private String verificationCode;
private boolean enabled;
private String password;
private Set<Role> roles = new HashSet<>();

/**
 * Runs before tests to preconfigure the test data	
 * @throws Exception
 */
@BeforeEach
	void config() throws Exception {
		users = new Users();
		trueUsers = new Users();
		Role role = new Role();
		role.setId(1);
		role.setName("user");
		roles.add(role);
		email = "testEmail@sru.edu";
		firstName = "John";
		lastName = "Doe";
		userRole = "user";
		password = "password!";
		enabled = true;
		users.setRoles(roles);
		userRepo.save(users);
}
/**
 * tests the setEmail function
 */
@Test
void testSetEmail() {
	trueUsers = users;
	trueUsers.setEmail("testEmail@sru.edu");
	userRepo.save(trueUsers);
	
	assertEquals("testEmail@sru.edu", trueUsers.getEmail());
}
/**
 * tests the getEmail function 
 */
@Test
void testGetEmail() {
	assertNotEquals(email, users.getEmail());
}
/**
 * tests the SetFirstName function
 */
@Test
void testSetFirstName() {
	trueUsers = users;
	trueUsers.setFirstName("John");
	userRepo.save(trueUsers);
	
	assertEquals("John", trueUsers.getFirstName());
}
/**
 * tests the getFirstName function
 */
@Test
void testGetFirstName() {
	assertNotEquals(firstName, users.getFirstName());
}
/**
 * tests the setLastName function
 */
@Test
void testSetLastName() {
	trueUsers = users;
	trueUsers.setLastName("Doe");
	userRepo.save(trueUsers);
	
	assertEquals("Doe", trueUsers.getLastName());
}
/**
 * tests the getLastName function
 */
@Test
void testGetLastName() {
	assertNotEquals(lastName, users.getLastName());
}
/**
 * tests the getUserRole function
 */
@Test
void testGetUserRole() {
	assertNotEquals(role, users.getRoles());
}
/**
 * tests the setPassword function
 */
@Test
void testSetPassword() {
	trueUsers = users;
	trueUsers.setPassword("password!");
	userRepo.save(trueUsers);
	
	assertEquals("password!", trueUsers.getPassword());
}
/**
 * tests the getPassword function
 */
@Test
void testGetPassword() {
	assertNotEquals(password, users.getPassword());
}
}








