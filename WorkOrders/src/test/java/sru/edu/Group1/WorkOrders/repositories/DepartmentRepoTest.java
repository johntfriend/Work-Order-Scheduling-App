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
import sru.edu.group1.workorders.domain.Departments;
import sru.edu.group1.workorders.repository.BuildingRepository;
import sru.edu.group1.workorders.repository.DepartmentsRepository;

/**
 * Test class for testing the departments repositories
 * @author Ellie Wurst bmw1030@sru.edu
 *
 */
@SpringBootTest
public class DepartmentRepoTest {

	@Autowired
	private DepartmentsRepository testRepo;

	private Departments department;

	private Departments testDepartment;
	
	/**
	 * Setups up the false department to test on
	 */
	@BeforeEach
	void config() throws Exception {
	department = new Departments();
	testDepartment = new Departments();
	department.setId(1);
	department.setDepartment("Music");
	testRepo.save(department);
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
		if(testRepo.findById(department.getId()) != null) {
		testDepartment = testRepo.findById(department.getId());
		assertEquals(department,testDepartment);
		}
	}
	/**
	 * Test the find by Id function if the Id does not exist
	 */
	@Test
	void testFindByIdNotExist() {
		testDepartment = testRepo.findById(3);
		assertNull(testDepartment);
	}

}
