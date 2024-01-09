package sru.edu.group1.workorders.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = true, length = 30)
	private String name;

	public Role() {}
	
	public Role(String name) 
	{
		this.name = name;
	}

	public Role(int id, String name) 
	{
		this.id = id;
		this.name = name;
	}

	public Role(int id) 
	{
		this.id = id;
	}

	@Override
	public String toString()
	{
		return this.name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
