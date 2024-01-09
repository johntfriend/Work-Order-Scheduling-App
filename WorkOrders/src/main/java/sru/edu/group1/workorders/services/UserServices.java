package sru.edu.group1.workorders.services;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import net.bytebuddy.utility.RandomString;
import sru.edu.group1.workorders.domain.*;
import sru.edu.group1.workorders.repository.RoleRepository;
import sru.edu.group1.workorders.repository.UserRepository;

/**
 * The most populated of all service files, primarily for sending emails and adding specific users with different roles
 * @author Trevor Bell trevorbell030@gmail.com
 * @author Ellie Wurst
 * @author John Friend
 * @author Hunter Minteer
 */

@Service
public class UserServices {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
    private JavaMailSender mailSender;
	@Autowired
	private RoleRepository roleRepo;
	
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    
    /**
     * will find all the users in the system and return them as a List of users
     * @return a List of users
     */
    public List<Users> listAll() {
		return userRepository.findAll();
		}

    /**
     * Adds a brand new user to the system once the hit submit on the registration page, defaults their role to user, and then sends an email asking to verify their account
     * @param user holds the user information to save
     * @param siteURL used for sending the email
     * @throws UnsupportedEncodingException catches character encoding issues
     * @throws MessagingException handles simple message errors
     */
	public void register(Users user, String siteURL)
	        throws UnsupportedEncodingException, MessagingException {
	   
	    sru.edu.group1.workorders.domain.Role roleUser = roleRepo.findByname("User");
        user.addRole(roleUser);
        
        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);
        
	    sendVerificationEmail(user, siteURL);
	    userRepository.save(user);
	}
	
	/**
	 * Adds an admin user into the system, called a single time when you hit the login page, and will error if this function is ever ran again without checking for it's existance first
	 */
	 public void addAdminUser() {
	      	Role roleAdmin = roleRepo.findByname("Admin");
	       
	        Users user = new Users();
	        user.setEmail("adminRole@sru.edu");
	        
	        String encodedPassword = encoder.encode("Password");
			user.setPassword(encodedPassword);
			
	        user.setFirstName("Admin");
	        user.setLastName("User");
	        user.addRole(roleAdmin);
	        user.setEnabled(true);
	 
	        userRepository.save(user);
	    }
	 /**
	  * adds a manager, much like the admin gets called a single time once you hit the login page, then cannot be ran again or it will crash for the unique key
	  */
	 public void addManagerUser() {
	      	Role roleManager = roleRepo.findByname("Manager");
	       
	        Users user = new Users();
	        user.setEmail("managerRole@sru.edu");
	        user.setId(4);
	        
	        String encodedPassword = encoder.encode("Passw0rd");
			user.setPassword(encodedPassword);
			
	        user.setFirstName("Manager");
	        user.setLastName("User");
	        user.addRole(roleManager);
	        user.setEnabled(true);
	 
	        userRepository.save(user);
	    }
	/**
	 * adds a user with the normal user role, same rules as the manager and Admin additions apply
	 */
	 public void addBasicUser() {
	      	Role roleUser = roleRepo.findByname("User");
	       
	        Users user = new Users();
	        user.setEmail("userRole@sru.edu");
	        user.setId(2);
	        
	        String encodedPassword = encoder.encode("Wordpass");
			user.setPassword(encodedPassword);
			
	        user.setFirstName("Normal");
	        user.setLastName("User");
	        user.addRole(roleUser);
	        user.setEnabled(true);
	 
	        userRepository.save(user);
	    }
	 /**
	  * Adds a tech user, cannot be ran again and ran a single time at login page if it doesn't already exist
	  */
	 public void addTechUser()
	    {
	    	Role roleTech = roleRepo.findByname("Tech");
	        
	         Users user = new Users();
	         user.setEmail("techRole@sru.edu");
	         user.setId(3);
	         
	         String encodedPassword = encoder.encode("Robotics2");
	 		 user.setPassword(encodedPassword);
	 		
	         user.setFirstName("Technical");
	         user.setLastName("User");
	         user.addRole(roleTech);
	         user.setEnabled(true);
	         
	         userRepository.save(user);
	    }
	 
	 /**
	  * If a users' account is not enabled, they can request a new verification email to reverify their account
	  * @param user holds the info of the user requesting the 
	  * @param siteURL used for sending the email
	  * @throws UnsupportedEncodingException character encoding errors
	  * @throws MessagingException simple messaging errors
	  */
	 public void reSendVerificationEmail(Users user, String siteURL) throws UnsupportedEncodingException, MessagingException
	 {
		 sendVerificationEmail(user, siteURL);
	 }
	 
	 /**
	  * This will send the original verification email when a user first registers or when they request a new one
	  * @param user holds the user info to send the email to
	  * @param siteURL holds info for emails
	  * @throws MessagingException simple messaging errors
	  * @throws UnsupportedEncodingException character encoding errors
	  */
	private void sendVerificationEmail(Users user, String siteURL)
	        throws MessagingException, UnsupportedEncodingException {
	    String toAddress = user.getEmail();
	    String fromAddress = "thekidwithoutalife@gmail.com";
	    String senderName = "Group 1 - Work Order System";
	    String subject = "Thank you for registering with us!";
	    String content = "Dear [[name]],<br>"
	            + "Please click on the link below to verify your account. <br>"
	    		+ "<h3> <a href=\"[[URL]]\" > Verify </a></h3>"
	            + "Group 1 - Work Order System";
	     
	    jakarta.mail.internet.MimeMessage message = mailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	     
	    helper.setFrom(fromAddress, senderName);
	    helper.setTo(toAddress);
	    helper.setSubject(subject);
	     
	    content = content.replace("[[name]]", user.getFirstName());
	    String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();
	    content = content.replace("[[URL]]",verifyURL);
	     
	    helper.setText(content, true);
	     
	    mailSender.send(message);
	     
	}
	/**
	 * whenever an incident gets updated, the user who's email is associated with the incident will get an email
	 * @param email
	 * @param update
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	public void sendUpdateIncident(String email, String update)
	        throws MessagingException, UnsupportedEncodingException {
	    String toAddress = email;
	    String fromAddress = "thekidwithoutalife@gmail.com";
	    String senderName = "Group 1 - Work Order System";
	    String subject = "Update In your project";
	    String content = "Your incident has been updated, check below for all updates from are techs <br>"
	            + update + "<br>"
	            + "Group 1 - Work Order System";
	     
	    jakarta.mail.internet.MimeMessage message = mailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	     
	    helper.setFrom(fromAddress, senderName);
	    helper.setTo(toAddress);
	    helper.setSubject(subject);
	 
	     
	    helper.setText(content, true);
	     
	    mailSender.send(message);
	}
	/**
	 * method that updates the password rest token on a user whenever they request one to change their forgotten password
	 * @param token sets a users's token to this value
	 * @param email has the email of the user in question to change the token of
	 */
	public void updateResetPasswordToken(String token, String email) {
        Users customer = userRepository.findByEmail(email);
        if (customer != null) {
            customer.setResetPasswordToken(token);
            userRepository.save(customer);
        } 
    }
    /**
     * method to find a user by their password token
     * @param token the token itself which is used to find the user
     * @return
     */
    public Users getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }
    /**
     * Once a user gets to the page to change their password, this gets called to properly update the password
     * @param user holds the information for the user in question
     * @param newPassword holds the new password information
     */
    public void updatePassword(Users user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
         
        user.setResetPasswordToken(null);
        userRepository.save(user);
    }
    /**
     * finds user to update the reset pasword token, will return an error if the user is not found with that specific email
     * @param token holds the token to update the users' with
     * @param email the email the user put in to try and change the password of
     * @throws UserNotFoundService a service that throws an error if the user is not found properly
     */
	public void updateResetPassword(String token, String email) throws UserNotFoundService
	   {
		   Users user = userRepository.findByEmail(email);
		   
		   if(user != null) {
			   user.setResetPasswordToken(token);
			   userRepository.save(user);
		   } else {
			   throw new UserNotFoundService("Could not find any user with email " + email);
		   }
	   }
	   //To get reset password token
	   public Users get(String resetPasswordToken)
	   {
		   return userRepository.findByResetPasswordToken(resetPasswordToken);
	   }
	   
	   /**
	    * when a user hits the link in their verification email, their account will be verified as long as code of the email is the same as the one associated with the user
	    * @param verificationCode finds a user by the verification code passed in, if not, returns an error, otherwise will enable them
	    * @return true or false on if the verification code is correct or not
	    */
	   public boolean verify(String verificationCode)
	   {
		   Users user = userRepository.findByVerificationCode(verificationCode);
		   if(user == null || user.isEnabled())
		   {
			   return false;
		   }
		   else
		   {
			   user.setVerificationCode(null);
			   user.setEnabled(true);
			   userRepository.save(user);
			   return true;
		   }
	   }
}



