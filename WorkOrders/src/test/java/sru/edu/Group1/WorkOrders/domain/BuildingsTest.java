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
 * This test file is for testing the functionality of the building entity
 * @author Ellie Wurst bmw1030@sru.edu
 *
 */

@SpringBootTest
public class BuildingsTest {

Buildings buildings;	

Buildings trueBuildings;

Departments department;

Rooms room;

@Mock
BuildingRepository buildRepo;

@Mock
DepartmentsRepository departmentRepo;

@Mock
RoomsRepository roomRepo;

private String building; 	

private Set<Rooms> rooms = new HashSet<>();
    
private Set<Departments> departments = new HashSet<>();

/**
 * Runs before tests to preconfigure the test data	
 * @throws Exception
 */	
	@BeforeEach
	void config() throws Exception {
		buildings = new Buildings();
		trueBuildings = new Buildings();
		Rooms room = new Rooms();
		Departments department = new Departments();
		
		room.setId(1);
		room.setRoomNumbers("100");
		roomRepo.save(room);
		
		department.setId(1);
		department.setDepartment("Basketweaving");
		departmentRepo.save(department);
		
		buildings.setId(1);
		building = "testBuild";		
		buildings.setBuilding("testBuild");
		buildings.setDepartment(departments);
		buildings.setRooms(rooms);
		buildRepo.save(buildings);
	}
	/**
	 * tests the SetId function
	 */
	@Test
	void testSetId() {
		trueBuildings = buildings;
		trueBuildings.setId(2);
		buildRepo.save(trueBuildings);
		
		assertEquals(2, trueBuildings.getId());
	}
	/**
	 * tests the GetId function
	 */
	@Test
	void testGetId() {
		assertNotEquals(2, buildings.getId());
	}
	/**
	 * tests the SetBuilding function
	 */
	@Test
	void testSetBuilding() {
		trueBuildings = buildings;
		trueBuildings.setBuilding("test-build");
		buildRepo.save(trueBuildings);
		
		assertEquals("test-build", trueBuildings.getBuilding());
	}
	/**
	 * tests the getBuilding function
	 */
	@Test
	void testGetBuilding() {
		assertNotEquals(building, trueBuildings.getBuilding());
	}
}
