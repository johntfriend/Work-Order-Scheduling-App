package sru.edu.Group1.WorkOrders;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import sru.edu.group1.workorders.domain.Role;
import sru.edu.group1.workorders.domain.Users;
import sru.edu.group1.workorders.repository.RoleRepository;
import sru.edu.group1.workorders.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepo;
     
    @Autowired
    private RoleRepository roleRepo;
    
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    
    /*
    @Test
    public void testAddTechUser()
    {
    	Role roleTech = roleRepo.findByname("Tech");
        
         Users user = new Users();
         user.setEmail("techRole@sru.edu");
         
         String encodedPassword = encoder.encode("Robotics2");
 		user.setPassword(encodedPassword);
 		
         user.setFirstName("Technical");
         user.setLastName("User");
         user.addRole(roleTech);
          
         Users savedUser = userRepo.save(user);
    }
    
    @Test
    public void testAddRoleToNewUser() {
       Role roleAdmin = roleRepo.findByname("Admin");
       //Role roleUser = roleRepo.findByname("User");
       
        Users user = new Users();
        user.setEmail("adminRole@sru.edu");
        
        String encodedPassword = encoder.encode("Password");
		user.setPassword(encodedPassword);
		
        user.setFirstName("Admin");
        user.setLastName("User");
        user.addRole(roleAdmin);
 
        Users savedUser = userRepo.save(user);
    }
   
    
    @Test
    public void FindAUserByTheirRole()
    {
    	Role role = roleRepo.findByname("User");
    	List<Users> user = userRepo.findByRoles(role);
    	int i = 0;
    	while(user.get(i) != null)
    	{
    	System.out.println(user.get(i).getEmail());
    	i++;
    	}
    	
    }
     */
    
    @Test
    public void ListUserRoles()
    {
    	List<Users> user = userRepo.findAll();
    	
    	//System.out.println(user.get(0).getRoles());
    }
    
    @Test
    public void getRoleFromUsername()
    {
    	String email = "tlb1024@sru.edu";
    	
    	Users user = userRepo.findByEmail(email);
    	
    	System.out.println(user.getRoles());
    }
}
