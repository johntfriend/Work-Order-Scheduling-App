package sru.edu.group1.workorders.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import sru.edu.group1.workorders.domain.Incidents;


public interface IncidentRepository extends JpaRepository<Incidents, Long>{

	public Incidents findById(long value);
}
