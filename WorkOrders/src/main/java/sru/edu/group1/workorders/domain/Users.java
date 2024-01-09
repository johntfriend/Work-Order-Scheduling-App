package sru.edu.group1.workorders.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import sru.edu.group1.workorders.security.ValidPassword;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "users")
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//This will automatically create a user ID as users are added, starting with 1
	private long id;
	
	@Column(name = "email", nullable = false, unique = true, length = 45)
	//an email variable that is not allowed to be left empty, nor can it be the same as another users
	private String email;
	
	@Column(name = "first_name", nullable = false, length = 45)
	//will create the column within a database, also not allowed to be left empty when a new user is made
	private String firstName;
	
	@Column(name = "last_name", nullable = false, length = 45)
	//same as first name, just for their last name
	private String lastName;
  
	@Column(name = "user_role", nullable = true, length = 45)
	//holds a user role, defaults to USER, will be able to change to or have seeded as ADMIN or others, depending on what needs done.
	private String userRole;
	 
	@Column(name = "reset_password_token")
	//Holds the value if a token is given to a user
	private String resetPasswordToken;

	@Column(name = "verificationCode", length = 64)
	private String verificationCode;
	
	private boolean enabled;
	
	@Column(nullable = false, length = 200)
	@ValidPassword
	//will store a password, will also check if it is valid from the security package
	private String password;
	    
	 @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	    @JoinTable(
	            name = "users_roles",
	            joinColumns = @JoinColumn(name = "user_id"),
	            inverseJoinColumns = @JoinColumn(name = "role_id")
	    )
	    private Set<Role> roles = new HashSet<>();
	 
	    public void addRole(Role role) {
	        this.roles.add(role);
	}
	    public void deleteRole() {
	        this.roles.clear();
	}
	
	//Constructors that can be created with some data already put in for seeding purposes, or one to create empty users
	public Users(long id, String email, String password, boolean enabled) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
	}
	
	//Basic setters and getters for the variables
	public Users()
	{
		this.id = 1;
		this.email = "";
		this.password = "";
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
}
