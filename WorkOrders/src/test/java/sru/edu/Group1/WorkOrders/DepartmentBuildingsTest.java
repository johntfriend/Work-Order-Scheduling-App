package sru.edu.Group1.WorkOrders;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import sru.edu.group1.workorders.domain.*;
import sru.edu.group1.workorders.repository.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class DepartmentBuildingsTest {

	@Autowired
	private BuildingRepository buildingRepo;
	
	@Autowired
	private DepartmentsRepository deptRepo;
	
	
	@Test
	public void ensureDeptsAreNotNull()
	{
		assertThat(deptRepo.findAll()).isNotNull();
	}
	
	@Test
	public void AddNewDeptAndLinkBuilding()
	{
		List<Departments> departments = deptRepo.findAll();
		Buildings current = new Buildings();
		current.setBuilding("ARC");
		current.addDepartments(departments);
		buildingRepo.save(current);
		
		current.setBuilding("Bailey Library");
		current.addDepartments(departments);
		buildingRepo.save(current);
	}
}
