package sru.edu.Group1.WorkOrders;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import sru.edu.group1.workorders.domain.Role;
import sru.edu.group1.workorders.repository.RoleRepository;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoyTest {
	
	@Autowired 
	private RoleRepository repo;
	
	@Test
	public void testRolesLoaded()
	{
		List<Role> listRoles = repo.findAll();
		assertThat(listRoles.size()).isEqualTo(4);
	}
	
	@Test
	public void testCreateRoles()
	{
		Role user = new Role("User");
		Role admin = new Role("Admin");
		Role tech = new Role("Tech");
		
		repo.saveAll(List.of(user, admin, tech));
		List<Role> listRoles = repo.findAll();
		assertThat(listRoles.size()).isEqualTo(7);
	}
	
}
