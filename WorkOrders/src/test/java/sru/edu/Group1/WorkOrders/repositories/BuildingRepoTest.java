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
import sru.edu.group1.workorders.domain.Rooms;
import sru.edu.group1.workorders.repository.BuildingRepository;

/**
 * Test class for testing the building repositories
 * @author Ellie Wurst bmw1030@sru.edu
 *
 */
@SpringBootTest
public class BuildingRepoTest {
	
@Autowired
private BuildingRepository testRepo;

private Buildings building;

private Buildings testBuilding;

private Set<Rooms> rooms = new HashSet<>();

private Set<Departments> departments = new HashSet<>();

/**
 * Setups up the false building to test
 */
@BeforeEach
void config() throws Exception {
building = new Buildings();
testBuilding = new Buildings();
building.setId(1);
building.setBuilding("Swope");
building.setDepartment(departments);
building.setRooms(rooms);
testRepo.save(building);
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
	if(testRepo.findById(building.getId()) != null) {
	testBuilding = testRepo.findById(building.getId());
	assertEquals(building,testBuilding);
	}
}
/**
 * Test the find by Id function if the Id does not exist
 */
@Test
void testFindByIdNotExist() {
	testBuilding = testRepo.findById(3);
	assertNull(testBuilding);
}
/**
 * Tests the find by building function
 */
@Test
void testFindByBuilding() {
	if(testRepo.findByBuilding(building.getBuilding()) != null) {
	testBuilding = testRepo.findByBuilding(building.getBuilding());
	assertEquals(building,testBuilding);
	}
}
/**
 * tests find by username function if username doesn't exist
 */
@Test
void testFindByBuildingNotExist() {
	testBuilding = testRepo.findByBuilding("ATS");
	assertNull(testBuilding);
}


}
