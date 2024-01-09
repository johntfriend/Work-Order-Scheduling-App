package sru.edu.group1.workorders.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sru.edu.group1.workorders.domain.Rooms;

public interface RoomsRepository extends JpaRepository<Rooms, Long>{
	public Rooms findById(long id);
	
	@Query("SELECT u from Rooms u WHERE u.roomNumbers = ?1")
	public List<Rooms> findByBuilding(@Param("id") long id);
	
	Boolean existsByRoomNumbers(String roomNumbers);
}
