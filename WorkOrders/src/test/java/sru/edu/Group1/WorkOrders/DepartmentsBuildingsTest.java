package sru.edu.Group1.WorkOrders;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import sru.edu.group1.workorders.domain.Buildings;
import sru.edu.group1.workorders.domain.Departments;
import sru.edu.group1.workorders.repository.BuildingRepository;
import sru.edu.group1.workorders.repository.DepartmentsRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class DepartmentsBuildingsTest {

	@Autowired
	private BuildingRepository buildingRepo;
	@Autowired
	private DepartmentsRepository deptRepo;
	
	@Test
	public void pullDeptInfo()
	{
		
		List<Departments> departments = deptRepo.findAll();
		Buildings current = buildingRepo.findByBuilding("SPOTTS");
		/*
		current.addDepartments(departments);
		buildingRepo.save(current);
		*/
		System.out.println(current.getDepartment());
	}
}
