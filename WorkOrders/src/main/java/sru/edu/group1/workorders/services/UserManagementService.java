package sru.edu.group1.workorders.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sru.edu.group1.workorders.domain.Role;
import sru.edu.group1.workorders.domain.Users;
import sru.edu.group1.workorders.repository.UserRepository;
import sru.edu.group1.workorders.repository.RoleRepository;

@Service
public class UserManagementService {
	@Autowired
	private UserRepository repo;
	@Autowired
	private RoleRepository roleRepo;
	
	public List<Users> listAllUser() {
		return repo.findAll();
	}

	public void saveUser(Users users) {
		repo.save(users);
	}

	public Users getUser(Long id) {
		return repo.findById(id).get();
	}
	
	public Long getUserId(String email) {
		return repo.findByEmail(email).getId();
	}
	
	//Deletes the selected accounts current role then changes it to Admin
	public void setAdmin(Users user) {
      	Role roleAdmin = roleRepo.findByname("Admin");
      	user.deleteRole();
      	user.addRole(roleAdmin);
      	repo.save(user);
    }
	
	//Deletes the selected accounts current role then changes it to Tech
	public void setTech(Users user) {
      	Role roleTech = roleRepo.findByname("Tech");
      	user.deleteRole();
      	user.addRole(roleTech);
      	repo.save(user);
    }
	
	//Deletes the selected accounts current role then changes it to User
	public void setUser(Users user) {
      	Role roleUser = roleRepo.findByname("User");
      	user.deleteRole();
      	user.addRole(roleUser);
      	repo.save(user);
    }
	
	//Deletes the selected accounts current role then changes it to Manager
		public void setManager(Users user) {
	      	Role roleManager = roleRepo.findByname("Manager");
	      	user.deleteRole();
	      	user.addRole(roleManager);
	      	repo.save(user);
	    }
}