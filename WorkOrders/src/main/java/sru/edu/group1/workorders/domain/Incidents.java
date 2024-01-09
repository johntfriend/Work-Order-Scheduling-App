package sru.edu.group1.workorders.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "incidents")
public class Incidents {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//This will automatically create a report ID as reports are added, starting with 1
	private long report_id;
	
	@Column(nullable = false, unique = false, length = 45)

	//an email variable that is not allowed to be left empty, and connects users to their reports so that they can receive report updates
	private String email;
	
	@Column(name = "date", nullable = false)
	//will create the column within a database of when the report was made, also not allowed to be left empty when a incident is made
	private String date;
	
	@Column(name = "severity", nullable = false, length = 25)
	//A column in the database that holds the incidents severity low, medium, high, higher is more severe
	private String severity;
	
	@Column(name = "status", nullable = false, length = 45)
	//Creates a status column that holds the incidents reports current status 
	private String status;
	
	@Column(name = "phone", nullable = false, length = 12)
	//Creates a phone column that holds the users phone number 
	private String phone;
	
	@Column(name = "building", nullable = false, length = 45)
	//Creates a building column that holds the incidents building 
	private String building;
	
	@Column(name = "department", nullable = false, length = 45)
	//Creates a department column that holds the department of an incident if possible
	private String department;
	
	@Column(name = "room", nullable = false, length = 45)
	//Creates a room column that holds the incidents room 
	private String room;
	
	@Column(name = "issue_type", nullable = false, length = 45)
	//Creates a issue_type column that holds the type of issue 
	private String issue_type;
	
	@Column(name = "description", nullable = false, length = 500)
	//Creates a room column that holds the incidents room 
	private String description;
	
	@Column(name = "cc", length = 500)
	//Creates a column of all other emails added, for the sake of emailing everyone involved.
	private String cc;
	
	@Column(name = "assign_email", nullable = false, length = 500)
	//Creates a column of all technicians assigned to the report, defaults to unassigned
	private String assign_email;
	
	@Column(name = "updates", nullable = false, length = 500)
	//Creates a update column that holds the incidents updates 
	private String updates;
	
	
	//Constructors that can be created with some data already put in for seeding purposes, or one to create empty users
	public Incidents(long report_id, String email, String date, String severity, String status, String phone,
			String building, String department, String room, String issue_type, String decription, String cc, String assign_email, String updates) {
		super();
		this.report_id = report_id;
		this.email = email;
		this.date = date;
		this.severity = severity;
		this.status = status;
		this.phone = phone;
		this.building = building;
		this.department = department;
		this.room = room;
		this.issue_type = issue_type;
		this.description = decription;
		this.cc = cc;
		this.assign_email = assign_email;
		this.updates = updates;
	}
	
	//Constructor
	public Incidents()
	{
		this.report_id = 1;
		this.email = "";
		this.building = "";
		this.department = "";
		this.room = "";
		this.description = "";
		this.date = "";
		this.severity = "";
		this.status = "OPEN";
		this.phone = "";
		this.issue_type = "";
		this.assign_email = "UNASSIGNED";
		this.updates = "";
	}
		
	public long getReport_id() {
		return report_id;
	}

	public void setReport_id(long report_id) {
		this.report_id = report_id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getDepartment() {
		return department;
	}
	
	public void setDepartment(String department) {
		this.department = department;
	}
	
	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getIssue_type() {
		return issue_type;
	}

	public void setIssue_type(String issue_type) {
		this.issue_type = issue_type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}
	
	public String getAssign_email() {
		return assign_email;
	}

	public void setAssign_email(String assign_email) {
		this.assign_email = assign_email;
	}
	
	public String getUpdates() {
		return updates;
	}

	public void setUpdates(String updates) {
		this.updates = updates;
	}
	
}

