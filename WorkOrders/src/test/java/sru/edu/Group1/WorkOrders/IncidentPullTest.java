package sru.edu.Group1.WorkOrders;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.mail.MessagingException;
import sru.edu.group1.workorders.domain.Incidents;
import sru.edu.group1.workorders.domain.Users;
import sru.edu.group1.workorders.repository.IncidentRepository;
import sru.edu.group1.workorders.repository.RoleRepository;
import sru.edu.group1.workorders.repository.UserRepository;
import sru.edu.group1.workorders.services.UserServices;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class IncidentPullTest {
	
	@Autowired	
	private IncidentRepository incidentRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserServices userService;
	
	@Test
	public void incidentPull() {
		Incidents test = incidentRepository.findById((long) 1);
		Users user = userRepository.findByEmail("hgm1005@sru.edu");
		String id = user.getEmail();
		assertThat(id).isEqualTo(test.getAssign_email());
	}
	
	@Test
	public void incidentUpdateEmail() throws UnsupportedEncodingException, MessagingException {
		Incidents test = incidentRepository.findById((long) 1);
		Users user = userRepository.findByEmail("hgm1005@sru.edu");
		String id = user.getEmail();
		String update = "your printer was fixed";
		
		userService.sendUpdateIncident(test.getEmail(), update);
		assertThat(id).isEqualTo(test.getEmail());
	}
		
}
