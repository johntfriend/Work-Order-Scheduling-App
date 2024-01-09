 package sru.edu.group1.workorders.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sru.edu.group1.workorders.domain.Buildings;
import sru.edu.group1.workorders.domain.Departments;
import sru.edu.group1.workorders.domain.Rooms;
import sru.edu.group1.workorders.repository.BuildingRepository;
import sru.edu.group1.workorders.repository.DepartmentsRepository;
import sru.edu.group1.workorders.repository.RoomsRepository;

/**
 * These are some services avaliable to the building Object, some can be done with the repository but not all of them
 * @author Trevor Bell 
 * @author Ellie Wurst
 * @author John Friend
 * @author Hunter Minteer
 */
@Service
public class BuildingsService {

	@Autowired
	private BuildingRepository repo;
	private DepartmentsRepository deptRepo;
	private RoomsRepository roomsRepo;
	
	public List<Buildings> listAll() {
		return repo.findAll();
	}
	
	public void save(Buildings buildings) {
		repo.save(buildings);
	}
	
	public Buildings get(Long buildings_id) {
		return repo.findById(buildings_id).get();
	}
	
	public void delete(Long buildings_id) {
		repo.deleteById(buildings_id);
	}

	/**
	 * This will create two new buildings and link all departments to them, can be altered to include what you want, even linking currently exisitng buildings.
	 */
	public void AddNewDeptAndLinkBuilding()
	{
		List<Departments> departments = deptRepo.findAll();
		Buildings current = new Buildings();
		current.setBuilding("Swope");
		current.addDepartments(departments);
		repo.save(current);
		
		current.setBuilding("Eisenburg");
		current.addDepartments(departments);
		repo.save(current);
	}
	
	/**
	 * Adds 2 new buildings and links them to all current rooms in the system, can be changed or alterd, uncalled in the program currently
	 */
	public void AddNewRoomAndLinkBuilding()
	{
		List<Rooms> rooms = roomsRepo.findAll();
		Buildings current = new Buildings();
		current.setBuilding("Swope");
		current.addRooms(rooms);
		repo.save(current);
		
		current.setBuilding("Eisenburg");
		current.addRooms(rooms);
		repo.save(current);
	}
	
	}	
	

