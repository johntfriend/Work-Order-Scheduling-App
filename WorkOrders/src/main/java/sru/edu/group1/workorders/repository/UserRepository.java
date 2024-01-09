package sru.edu.group1.workorders.repository;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sru.edu.group1.workorders.domain.*;

@Configuration
public interface UserRepository extends JpaRepository<Users, Long>{
	@Query("SELECT u FROM Users u WHERE u.email = ?1")
	public Users findByEmail(String email);
	
	Boolean existsByEmail(String email);
	
	//method to find using token
	public Users findByResetPasswordToken(String token);
	
    public Users findByVerificationCode(String code);

    public List<Users> findByRoles(Role role);
    
    public Users findById(long id);
} 