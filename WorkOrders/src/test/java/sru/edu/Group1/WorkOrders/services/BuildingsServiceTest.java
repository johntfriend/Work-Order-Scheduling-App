package sru.edu.Group1.WorkOrders.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import sru.edu.group1.workorders.domain.Buildings;
import sru.edu.group1.workorders.domain.Departments;
import sru.edu.group1.workorders.domain.Rooms;
import sru.edu.group1.workorders.repository.BuildingRepository;
import sru.edu.group1.workorders.repository.DepartmentsRepository;
import sru.edu.group1.workorders.repository.RoomsRepository;
import sru.edu.group1.workorders.services.BuildingsService;

/**
 * Test class for our BuildingsService functionality
 * @author Ellie Wurst bmw1030@sru.edu
 */
@SpringBootTest
public class BuildingsServiceTest {

@Mock
BuildingRepository buildingRepo;

@Mock
DepartmentsRepository deptRepo;

@Mock
RoomsRepository roomsRepo; 

private Buildings building;

private BuildingsService buildService;

private Buildings testBuilding;

Departments department;

Rooms room;

private Set<Rooms> rooms = new HashSet<>();

private Set<Departments> departments = new HashSet<>();

/**
 * Sets up values for BuildingsService test class
 * @throws Exception
 */
@BeforeEach
void config() throws Exception {
buildingRepo = mock(BuildingRepository.class);
buildService = new BuildingsService();
building = new Buildings();
Rooms room = new Rooms();
Departments department = new Departments();

room.setId(1);
room.setRoomNumbers("100");
roomsRepo.save(room);

department.setId(1);
department.setDepartment("Music");
deptRepo.save(department);

building.setId(1);
building.setBuilding("Swope");
building.setDepartment(departments);
building.setRooms(rooms);
buildingRepo.save(building);
}
/**
 * tests the save function
 */
@Test
void testSave() {
	buildService.save(building);
	verify(buildingRepo).save(building);
}

}
