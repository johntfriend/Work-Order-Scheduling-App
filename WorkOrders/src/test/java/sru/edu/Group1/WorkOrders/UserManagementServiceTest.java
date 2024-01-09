package sru.edu.Group1.WorkOrders;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import sru.edu.group1.workorders.domain.Role;
import sru.edu.group1.workorders.domain.Users;
import sru.edu.group1.workorders.repository.UserRepository;
import sru.edu.group1.workorders.services.UserManagementService;
import sru.edu.group1.workorders.repository.RoleRepository;

@DataJpaTest
@Rollback(true)
public class UserManagementServiceTest {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private UserManagementService userService;
	
    
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
    @BeforeAll
    public void addRoleToTest()
    {
    	Role roleTech = roleRepo.findByname("User");
        
        Users user = new Users();
        user.setEmail("test@sru.edu");
         
        String encodedPassword = encoder.encode("test");
 		user.setPassword(encodedPassword);
 		
        user.setFirstName("test");
        user.setLastName("test");
        user.addRole(roleTech);
          
        Users savedUser = userRepo.save(user);
    }
    
	/* This currently deletes everything associated
	 *  with the role including the role with in the roles table 
	public void deleteUser(Long id) {
		repo.deleteById(id);
	}
	*/
    
    
	//Deletes the selected accounts current role then changes it to Frozen
	@Test
	public void testSetFrozen(Users user) {
      	
    }
	
	/*
	//Deletes the selected accounts current role then changes it to Admin
	public void setAdmin(Users user) {
      	Role roleAdmin = roleRepo.findByname("Admin");
      	user.deleteRole();
      	user.addRole(roleAdmin);
      	userRepo.save(user);
    }
	
	//Deletes the selected accounts current role then changes it to Tech
	public void setTech(Users user) {
      	Role roleTech = roleRepo.findByname("Tech");
      	user.deleteRole();
      	user.addRole(roleTech);
      	userRepo.save(user);
    }
	
	//Deletes the selected accounts current role then changes it to User
	public void setUser(Users user) {
      	Role roleUser = roleRepo.findByname("User");
      	user.deleteRole();
      	user.addRole(roleUser);
      	userRepo.save(user);
    }
    */
}