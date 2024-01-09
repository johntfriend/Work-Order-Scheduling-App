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
 * This test file is for testing the functionality of the room entity
 * @author Ellie Wurst bmw1030@sru.edu
 *
 */

@SpringBootTest
public class RoomsTest {
Rooms room;

Rooms trueRooms;

Buildings buildings;

@Mock
BuildingRepository buildRepo;

@Mock
RoomsRepository roomRepo;

private String roomNumber;


/**
 * Runs before tests to preconfigure the test data	
 * @throws Exception
 */
@BeforeEach
void config() throws Exception {
	room = new Rooms();
	trueRooms = new Rooms();
	Buildings building = new Buildings();
	building.setId(1);	
	building.setBuilding("testBuild");
	buildRepo.save(building);
	roomNumber = "100";
	room.setId(1);
	room.setRoomNumbers(roomNumber);
	roomRepo.save(room);
	
}
/**
 * tests the setId function
 */
@Test
void testSetId() {
	trueRooms = room;
	trueRooms.setId(2);
	roomRepo.save(trueRooms);
		
	assertEquals(2, trueRooms.getId());
}
/**
 * tests the getId function
 */
@Test
void testGetId() {
	assertNotEquals(2, trueRooms.getId());
}
/**
 * tests the setRoom function
 */
@Test
void testSetRoom() {
	trueRooms = room;
	trueRooms.setRoomNumbers("105");
	roomRepo.save(trueRooms);
	
	assertEquals("105", trueRooms.getRoomNumbers());
}
/**
 * tests the getRoom function
 */
@Test
void testGetRoom() {
	assertNotEquals(roomNumber, trueRooms.getRoomNumbers());
}

}
	