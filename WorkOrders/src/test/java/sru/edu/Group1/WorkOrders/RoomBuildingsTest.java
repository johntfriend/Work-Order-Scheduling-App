package sru.edu.Group1.WorkOrders;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import sru.edu.group1.workorders.domain.Buildings;
import sru.edu.group1.workorders.domain.Rooms;
import sru.edu.group1.workorders.repository.BuildingRepository;
import sru.edu.group1.workorders.repository.DepartmentsRepository;
import sru.edu.group1.workorders.repository.RoomsRepository;
import sru.edu.group1.workorders.services.BuildingsService;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoomBuildingsTest {

	@Autowired
	private BuildingRepository buildingRepo;
	@Autowired
	private RoomsRepository roomRepo;	
	
	@Test
	public void ensureRoomsAreNotNull()
	{
		assertThat(roomRepo.findAll()).isNotNull();
	}
	
	@Test
	public void AddNewRoomAndLinkBuilding()
	{
		List<Rooms> rooms = roomRepo.findAll();
		Rooms room = roomRepo.findById(1);
		Buildings current = new Buildings();
		
		current = buildingRepo.findByBuilding("Vincent");
		current.addRooms(rooms);
		buildingRepo.save(current);
	}
	
	@Test
	public void returnProperListOfRooms()
	{
		List<Buildings> buildings = buildingRepo.findAll();
		
		System.out.println(buildings.get(4).getRooms());

	}
}
