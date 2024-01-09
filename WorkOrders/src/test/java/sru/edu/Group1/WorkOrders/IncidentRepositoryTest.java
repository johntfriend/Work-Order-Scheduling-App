package sru.edu.Group1.WorkOrders;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import sru.edu.group1.workorders.repository.*;
import sru.edu.group1.workorders.domain.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class IncidentRepositoryTest {

	@Autowired
	IncidentRepository incidentRepo;
	@Autowired
	UserRepository userRepo;
	
	@Test
	public void ensuringIncidentTechnicianAssignment()
	{
		Incidents incident = incidentRepo.findById((long) 1);
		Users user = userRepo.findByEmail("techRole@sru.edu");
		String techName = user.getEmail();
		
		incident.setAssign_email(techName);
	}

}
