package sru.edu.group1.workorders.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "departments")
public class Departments {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
	
	@Column(nullable = true, length = 30)
	private String department;

	public Departments(String departments, long id)
	{
		super();
		this.id = id;
		this.department = departments;
		
	}

	public Departments()
	{
		this.department = "";
	
	}

	@Override
	public String toString()
	{
		return this.department;
	}
	
public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

public String getDepartment() {
	return department;
}

public void setDepartment(String departments) {
	department = departments;
}

}