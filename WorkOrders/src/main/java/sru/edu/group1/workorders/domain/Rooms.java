package sru.edu.group1.workorders.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "rooms")
public class Rooms{
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
	
	@Column(nullable = true, length = 5)
	private String roomNumbers;	
	
	public Rooms(String rooms, long id)
	{
		super();
		this.id = id;
		this.roomNumbers = rooms;	
	}
	
	public Rooms()
	{
		this.roomNumbers = "";
	}		

	@Override
	public String toString()
	{
		return this.roomNumbers;
	}
	
public String getRoomNumbers() {
	return roomNumbers;
}

public long getId() {
	return id;
}

public void setId(long id) {
	this.id = id;
}

public void setRoomNumbers(String roomNumbers) {
	this.roomNumbers = roomNumbers;
}


}