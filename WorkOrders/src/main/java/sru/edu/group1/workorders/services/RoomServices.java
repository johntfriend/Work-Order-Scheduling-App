package sru.edu.group1.workorders.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sru.edu.group1.workorders.domain.Rooms;
import sru.edu.group1.workorders.repository.RoomsRepository;

@Service
public class RoomServices {

	@Autowired
	private RoomsRepository repo;
	
	public List<Rooms> listAll() {
		return repo.findAll();
	}
	
	public void save(Rooms rooms) {
		repo.save(rooms);
	}
	
	public Rooms get(Long rooms_id) {
		return repo.findById(rooms_id).get();
	}
	
	public void delete(Long rooms_id) {
		repo.deleteById(rooms_id);
	}
		
	}	
