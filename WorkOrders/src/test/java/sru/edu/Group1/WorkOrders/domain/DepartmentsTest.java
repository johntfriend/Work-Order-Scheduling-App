package sru.edu.Group1.WorkOrders.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import sru.edu.group1.workorders.domain.Buildings;
import sru.edu.group1.workorders.domain.Departments;
import sru.edu.group1.workorders.domain.Rooms;
import sru.edu.group1.workorders.repository.BuildingRepository;
import sru.edu.group1.workorders.repository.DepartmentsRepository;
import sru.edu.group1.workorders.repository.RoomsRepository;

/**
 * This test file is for testing the functionality of the departments entity
 * @author Ellie Wurst bmw1030@sru.edu
 *
 */

@SpringBootTest
public class DepartmentsTest {

Departments departments;	

Departments trueDepartments;

Buildings buildings;

Rooms room;

@Mock
BuildingRepository buildRepo;

@Mock
DepartmentsRepository departmentRepo;

@Mock
RoomsRepository roomRepo;

private String department; 	


/**
 * Runs before tests to preconfigure the test data	
 * @throws Exception
 */
	@BeforeEach
	void config() throws Exception {
		departments = new Departments();
		trueDepartments = new Departments();
		Buildings building = new Buildings();
		
		building.setId(1);
		building.setBuilding("Swope");
		buildRepo.save(buildings);
		
		departments.setId(1);
		department = "test";		
		departmentRepo.save(departments);
	}
/**
 * Tests the SetId function
 */
	@Test
	void testSetId() {
		trueDepartments = departments;
		trueDepartments.setId(2);
		departmentRepo.save(trueDepartments);
		
		assertEquals(2, trueDepartments.getId());
	}
/**
 * tests the GetId function	
 */
	@Test
	void testGetId() {
		assertNotEquals(2, departments.getId());
	}
/**
 * tests the SetDepartment function		
 */
	@Test
	void testSetDepartment() {
		trueDepartments = departments;
		trueDepartments.setDepartment("test");
		departmentRepo.save(trueDepartments);
		
		assertEquals("test", trueDepartments.getDepartment());
	}
/**
 * tests the GetDepartment function	
 */
	@Test
	void testGetDepartment() {
		assertNotEquals("test", trueDepartments.getDepartment());
	}
}
