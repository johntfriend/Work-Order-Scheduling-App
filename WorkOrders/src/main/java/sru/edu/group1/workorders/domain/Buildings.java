package sru.edu.group1.workorders.domain;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "buildings")
public class Buildings {	
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
	
	@Column(nullable = false, unique = true, length = 45)
	private String building; 	
	
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	    @JoinColumn(name = "room_id")
	    private Set<Rooms> rooms = new HashSet<>();
        
		@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	    @JoinColumn (name = "department_id")
	    private Set<Departments> departments = new HashSet<>();
        
public Buildings(String building, long id)
{
	super();
	setId(id);
	setBuilding(building);
}		

public Buildings()
{
	this.id = 1;
	this.building = "";
}

public void addRooms(Rooms rooms) {
    this.rooms.add(rooms);
}
public void addRooms(List<Rooms> rooms) {
    this.rooms.addAll(rooms);
}
public void deleteRooms() {
    this.rooms.clear();
}	


public Set<Rooms> getRooms(){
	return rooms;
}
public void setRooms(Set<Rooms> rooms) {
	this.rooms = rooms;
}

public Set<Departments> getDepartment() {
	return departments;
}
public void setDepartment(Set<Departments> departments) {
	this.departments = departments;
}

public void addDepartments(Departments Departments) {
    this.departments.add(Departments);
}
public void addDepartments(List<Departments> Departments) {
    this.departments.addAll(Departments);
}
public void deleteDepartments() {
    this.departments.clear();
}	
public long getId() {
	return id;
}

public void setId(long id) {
	this.id = id;
}

public String getBuilding() {
	return building;
}

public void setBuilding(String building)
{
	this.building = building;
}

public String toString() {
	return "Building [id= " + id + ", building= " + building + "]";
}
}