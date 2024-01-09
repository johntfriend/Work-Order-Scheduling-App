package sru.edu.group1.workorders.repository;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

import sru.edu.group1.workorders.domain.Role;

@Configuration
public interface RoleRepository extends JpaRepository<Role, Long> {
	public Role findByname(String name);
}
