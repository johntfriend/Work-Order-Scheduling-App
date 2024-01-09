package sru.edu.Group1.WorkOrders;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import sru.edu.group1.workorders.domain.Buildings;
import sru.edu.group1.workorders.domain.Rooms;
import sru.edu.group1.workorders.repository.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoomRepositoryTest {

	@Autowired
	private RoomsRepository roomRepo;
	@Autowired
	private BuildingRepository buildingRepo;
	
	@Test
	public void TestForDataInRoomsAndBuildingTable()
	{
		assertThat(roomRepo.findAll()).isNotNull();
		assertThat(buildingRepo.findAll()).isNotNull();
	}
	
	/*
	@Test
	public void LinkBuildingsAndRooms()
	{
		Buildings current = new Buildings();
		Rooms newRoom = new Rooms("300");
		roomRepo.save(newRoom);
		current.addRooms(newRoom);
	}
	*/
	@Test
	public void AddNewBuildingConnectedToRoom()
	{
		List<Rooms> room = roomRepo.findAll();
		Buildings current = new Buildings();
		current.setBuilding("Weisenfluh");
		current.setId(6);
		
		current.addRooms(room);
		
		buildingRepo.save(current);
	}
	
}
