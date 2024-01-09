package sru.edu.Group1.WorkOrders;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import sru.edu.group1.workorders.domain.Users;
import sru.edu.group1.workorders.repository.RoleRepository;
import sru.edu.group1.workorders.repository.UserRepository;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ForgotPasswordTest {

    @Test
    public void tokenCreation()
    {
    	String testEmail;
    	String testToken;
    	Users user = new Users();
        user.setEmail("test@sru.edu");
        user.setResetPasswordToken("123");
        
        testEmail = String.valueOf(user.getEmail());
        testToken = String.valueOf(user.getResetPasswordToken());
        
        //Assert.assertEquals(testEmail, "test@sru.edu");
        //Assert.assertEquals(testToken, "123");
        
    }
	    
}
