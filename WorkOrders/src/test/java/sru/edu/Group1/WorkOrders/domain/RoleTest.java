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
import sru.edu.group1.workorders.repository.RoleRepository;
import sru.edu.group1.workorders.repository.RoomsRepository;

/**
 * This test file is for testing the functionality of the roles entity
 * @author Ellie Wurst bmw1030@sru.edu
 *
 */

@SpringBootTest
public class RoleTest {

Role roles;

Role trueRoles;
	
@Mock
RoleRepository roleRepo;

private int Id;
private String name;

/**
 * Runs before tests to preconfigure the test data	
 * @throws Exception
 */
@BeforeEach
	void config() throws Exception {
	roles = new Role();
	trueRoles = new Role();
	Id = 1;
	name = "User";
	roleRepo.save(roles);
}
/**
 * tests the setId function
 */
@Test
void testSetId() {
	trueRoles = roles;
	trueRoles.setId(1);
	roleRepo.save(trueRoles);
	
	assertEquals(1, trueRoles.getId());
}
/**
 * tests the getId function
 */
@Test
void testGetId() {
	assertNotEquals(Id, roles.getId());
}
/**
 * tests the setName function
 */
@Test
void testSetName() {
	trueRoles = roles;
	trueRoles.setName("User");
	roleRepo.save(trueRoles);
	
	assertEquals("User", trueRoles.getName());
}
/**
 * tests the getName function
 */
@Test
void testGetName() {
	assertNotEquals(name, roles.getName());
}
}
