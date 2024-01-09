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
import sru.edu.group1.workorders.domain.Rooms;
import sru.edu.group1.workorders.repository.RoomsRepository;

/**
 * Test class for testing the rooms repositories
 * @author Ellie Wurst bmw1030@sru.edu
 *
 */
@SpringBootTest
public class RoomRepoTest {

@Autowired
private RoomsRepository testRepo;

private Rooms room;

private Rooms testRoom;

/**
 * Setups up the false room to test
 */
@BeforeEach
void config() throws Exception {
room = new Rooms();
testRoom = new Rooms();
room.setId(1);
room.setRoomNumbers("100");
testRepo.save(room);
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
	if(testRepo.findById(room.getId()) != null) {
	testRoom = testRepo.findById(room.getId());
	assertEquals(room,testRoom);
	}
}
/**
 * Test the find by Id function if the Id does not exist
 */
@Test
void testFindByIdNotExist() {
	testRoom = testRepo.findById(3);
	assertNull(testRoom);
}
}
