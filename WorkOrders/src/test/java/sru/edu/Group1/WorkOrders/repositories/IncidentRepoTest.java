package sru.edu.Group1.WorkOrders.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import sru.edu.group1.workorders.domain.Buildings;
import sru.edu.group1.workorders.domain.Incidents;
import sru.edu.group1.workorders.repository.BuildingRepository;
import sru.edu.group1.workorders.repository.IncidentRepository;

/**
 * Test class for testing the incident repositories
 * @author Ellie Wurst bmw1030@sru.edu
 *
 */
@SpringBootTest
public class IncidentRepoTest {

@Autowired
private IncidentRepository testRepo;

private Incidents incident;

private Incidents testIncident;	

/**
 * Setups up the false building to test
 */
@BeforeEach
void config() throws Exception {
incident = new Incidents();
testIncident = new Incidents();
incident.setEmail("testUser@sru.edu");
incident.setDate("04/15/2023");
incident.setSeverity("high");
incident.setStatus("open");
incident.setPhone("412-262-2432");
incident.setBuilding("Swope");
incident.setDepartment("Music");
incident.setRoom("100");
incident.setIssue_type("technical");
incident.setDescription("it dont work");
testRepo.save(incident);
}
/**
 * Tears down the repo after every test
 */
@AfterEach
void tearDown() {
	testRepo.deleteAll();
}

}
