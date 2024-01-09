package sru.edu.group1.workorders.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sru.edu.group1.workorders.domain.Departments;
import sru.edu.group1.workorders.domain.Rooms;

public interface DepartmentsRepository extends JpaRepository<Departments, Long>{
 public Departments findById(long id);
	
 @Query("SELECT u from Departments u WHERE u.department = ?1")
 public List<Rooms> findByBuilding(@Param("id") long id);
 
 Boolean existsByDepartment(String Department);
}
