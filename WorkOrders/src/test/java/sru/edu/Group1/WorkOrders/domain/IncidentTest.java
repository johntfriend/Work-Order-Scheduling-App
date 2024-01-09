package sru.edu.Group1.WorkOrders.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import sru.edu.group1.workorders.domain.Buildings;
import sru.edu.group1.workorders.domain.Departments;
import sru.edu.group1.workorders.domain.Incidents;
import sru.edu.group1.workorders.domain.Rooms;
import sru.edu.group1.workorders.repository.*;

/**
 * This test file is for testing the functionality of the incident entity
 * @author Ellie Wurst bmw1030@sru.edu
 *
 */

@SpringBootTest
public class IncidentTest {

	Incidents incidents;	

	Incidents trueIncidents;

	Buildings buildings;

	Departments departments;
	
	Rooms rooms;

	@Mock
	BuildingRepository buildRepo;

	@Mock
	DepartmentsRepository departmentRepo;

	@Mock
	RoomsRepository roomRepo;	
	
	@Mock
	IncidentRepository incidentRepo;
	
	private String email;
	private String date;
	private String severity;
	private String status;
	private String phone;
	private String building;
	private String department;
	private String room;
	private String issue_type;
	private String description;
	private String cc;
	private String assign_email;
	
	
	/**
	 * Runs before tests to preconfigure the test data	
	 * @throws Exception
	 */	
	@BeforeEach
	void config() throws Exception {
		incidents = new Incidents();
		trueIncidents = new Incidents();
		Rooms rooms = new Rooms();
		Buildings buildings = new Buildings();
		Departments departments = new Departments();
		
		email = "test-email@sru.edu";
		date = "02/05/2023";
		severity = "3";
		status = "open";
		phone = "412-273-2402";
		building = "Swope";
		department = "Music";
		room = "130";
		issue_type = "technical";
		description = "aaa";
		cc = "";
		assign_email = "";
		incidentRepo.save(incidents);
		
		rooms.setId(1);
		rooms.setRoomNumbers(room);
		roomRepo.save(rooms);
		
		buildings.setId(1);
		buildings.setBuilding(building);
		buildRepo.save(buildings);
		
		departments.setId(1);
		departments.setDepartment(department);
		departmentRepo.save(departments);
	}
	/**
	 * tests the setEmail function
	 */
	@Test
	void testSetEmail() {
		trueIncidents = incidents;
		trueIncidents.setEmail("test-email@sru.edu");
		incidentRepo.save(trueIncidents);
		
		assertEquals("test-email@sru.edu", trueIncidents.getEmail());
	}
	/**
	 * tests the getEmail function
	 */
	@Test
	void testGetEmail() {
		assertNotEquals(email, trueIncidents.getEmail());
	}
	/**
	 * tests the setDate function
	 */
	@Test
	void testSetDate() {
		trueIncidents = incidents;
		trueIncidents.setDate("02/05/2023");
		incidentRepo.save(trueIncidents);
		
		assertEquals("02/05/2023", trueIncidents.getDate());
	}
	/**
	 * tests the getDate function
	 */
	@Test
	void testGetDate() {
		assertNotEquals(date, trueIncidents.getDate());
	}
	/**
	 * tests the setSeverity function
	 */
	@Test
	void testSetSeverity() {
		trueIncidents = incidents;
		trueIncidents.setSeverity("3");
		incidentRepo.save(trueIncidents);
		
		assertEquals("3", trueIncidents.getSeverity());
	}
	/**
	 * tests the getSeverity function
	 */
	@Test
	void testGetSeverity() {
		assertNotEquals(severity, trueIncidents.getSeverity());
	}
	/**
	 * tests the setStatus function
	 */
	@Test
	void testSetStatus() {
		trueIncidents = incidents;
		trueIncidents.setStatus("open");
		incidentRepo.save(trueIncidents);
		
		assertEquals("open", trueIncidents.getStatus());
	}
	/**
	 * tests the getStatus function
	 */
	@Test
	void testGetStatus() {
		assertNotEquals(status, trueIncidents.getStatus());
	}
	/**
	 * tests the setPhone function
	 */
	@Test
	void testSetPhone() {
		trueIncidents = incidents;
		trueIncidents.setPhone("412-273-2402");
		incidentRepo.save(trueIncidents);
		
		assertEquals("412-273-2402", trueIncidents.getPhone());
	}
	/**
	 * tests the getPhone function
	 */
	@Test
	void testGetPhone() {
		assertNotEquals(phone, trueIncidents.getPhone());
	}
	/**
	 * tests the setBuilding function
	 */
	@Test
	void testSetBuilding() {
		trueIncidents = incidents;
		trueIncidents.setBuilding("Swope");
		incidentRepo.save(trueIncidents);
		
		assertEquals("Swope", trueIncidents.getBuilding());
	}
	/**
	 * tests the getBuilding function
	 */
	@Test
	void testGetBuilding() {
		assertNotEquals(building, trueIncidents.getBuilding());
	}
	/**
	 * tests the setDepartment function
	 */
	@Test
	void testSetDepartment() {
		trueIncidents = incidents;
		trueIncidents.setDepartment("Music");
		incidentRepo.save(trueIncidents);
		
		assertEquals("Music", trueIncidents.getDepartment());
	}
	/**
	 * tests the getDepartment function
	 */
	@Test
	void testGetDepartment() {
		assertNotEquals(department, trueIncidents.getDepartment());
	}
	/**
	 * tests the setRoom function
	 */
	@Test
	void testSetRoom() {
		trueIncidents = incidents;
		trueIncidents.setRoom("130");
		incidentRepo.save(trueIncidents);
		
		assertEquals("130", trueIncidents.getRoom());
	}
	/**
	 * tests the getRoom function
	 */
	@Test
	void testGetRoom() {
		assertNotEquals(room, trueIncidents.getRoom());
	}
	/**
	 * tests the setIssueType function
	 */
	@Test
	void testSetIssueType() {
		trueIncidents = incidents;
		trueIncidents.setIssue_type("technical");
		incidentRepo.save(trueIncidents);
		
		assertEquals("technical", trueIncidents.getIssue_type());
	}
	/**
	 * tests the getIssueType function
	 */
	@Test
	void testGetIssueType() {
		assertNotEquals(issue_type, trueIncidents.getIssue_type());
	}
	/**
	 * tests the setDescription function
	 */
	@Test
	void testSetDescription() {
		trueIncidents = incidents;
		trueIncidents.setDescription("aaa");
		incidentRepo.save(trueIncidents);
		
		assertEquals("aaa", trueIncidents.getDescription());
	}
	/**
	 * tests the getDescription function
	 */
	@Test
	void testGetDescription() {
		assertNotEquals(description, trueIncidents.getDescription());
	}
	/**
	 * tests the setCC function
	 */
	@Test
	void testSetCC() {
		trueIncidents = incidents;
		trueIncidents.setCc("amw1030@sru.edu");
		incidentRepo.save(trueIncidents);
		
		assertEquals("amw1030@sru.edu", trueIncidents.getCc());
	}
	/**
	 * tests the getCC function
	 */
	@Test
	void testGetCC() {
		assertNotEquals(cc, trueIncidents.getCc());
	}
	/**
	 * tests the setAssignEmail function
	 */
	@Test
	void testSetAssignEmail() {
		trueIncidents = incidents;
		trueIncidents.setAssign_email("techRole@sru.edu");
		incidentRepo.save(trueIncidents);
		
		assertEquals("techRole@sru.edu", trueIncidents.getAssign_email());
	}
	/**
	 * tests the getAssignEmail function
	 */
	@Test
	void testGetAssignEmail() {
		assertNotEquals(assign_email, trueIncidents.getAssign_email());
	}
}
