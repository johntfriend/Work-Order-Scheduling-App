package sru.edu.Group1.WorkOrders.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import sru.edu.group1.workorders.domain.Buildings;
import sru.edu.group1.workorders.domain.Role;
import sru.edu.group1.workorders.repository.RoleRepository;

/**
 * Test class for testing the role repositories
 * @author Ellie Wurst bmw1030@sru.edu
 *
 */
@SpringBootTest

public class RoleRepoTest {

@Autowired
private RoleRepository testRepo;

private Role role;

private Role testRole;

/**
 * Setups up the false role to test
 */
@BeforeEach
void config() throws Exception {
role = new Role();
testRole = new Role();
role.setId(0);
role.setName("User");
testRepo.save(role);
}
/**
 * Tears down the repo after every test
 */
@AfterEach
void tearDown() {
	testRepo.deleteAll();
}
/**
 * Tests the find by Name function
 */
@Test
void testFindByName() {
	if(testRepo.findByname(role.getName()) != null) {
	testRole = testRepo.findByname(role.getName());
	assertEquals(role,testRole);
	}
}
/**
 * Test the find by Name function if the name does not exist
 */
@Test
void testFindByIdNotExist() {
	testRole = testRepo.findByname("Player");
	assertNull(testRole);
}
}
