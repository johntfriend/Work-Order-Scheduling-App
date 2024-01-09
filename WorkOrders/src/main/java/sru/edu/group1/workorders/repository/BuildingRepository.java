package sru.edu.group1.workorders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sru.edu.group1.workorders.domain.Buildings;

public interface BuildingRepository extends JpaRepository<Buildings, Long>{
	@Query("SELECT u FROM Buildings u WHERE u.building = ?1")
	public Buildings findByBuilding(String building);
	
	Boolean existsByBuilding(String building);

	public Buildings findById(long id);
}
