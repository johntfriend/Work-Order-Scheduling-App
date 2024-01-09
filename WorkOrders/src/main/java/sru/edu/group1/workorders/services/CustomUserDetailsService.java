package sru.edu.group1.workorders.services;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.mail.MessagingException;
import sru.edu.group1.workorders.domain.CustomUserDetails;
import sru.edu.group1.workorders.domain.Users;
import sru.edu.group1.workorders.repository.UserRepository;

/**
 * This class customizes the UserDetailsService as defined by Maven. Currently, all that it
 * does is search for users and throw exceptions if not found. 
 * 
 * While some of this code was made by Group 1, the majority of it was made by Zach Freilino with his login project
 * */

public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
    private PasswordEncoder passwordEncoder;
	@Autowired
    private JavaMailSender mailSender;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = userRepository.findByEmail(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return new CustomUserDetails(user);
	}
	
	public void register(Users user, String siteURL)
	        throws UnsupportedEncodingException, MessagingException {
	    String encodedPassword = passwordEncoder.encode(user.getPassword());
	    user.setPassword(encodedPassword);
	     
	    userRepository.save(user);
	     
	    sendVerificationEmail(user, siteURL);
	}
	
	private void sendVerificationEmail(Users user, String siteURL)
	        throws MessagingException, UnsupportedEncodingException {
	    String toAddress = user.getEmail();
	    String fromAddress = "thekidwithoutalife@gmail.com";
	    String senderName = "Your company name";
	    String subject = "Please verify your registration";
	    String content = "Dear [[name]],<br>"
	            + "Thank you,<br>"
	            + "Your company name.";
	     
	    jakarta.mail.internet.MimeMessage message = mailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	     
	    helper.setFrom(fromAddress, senderName);
	    helper.setTo(toAddress);
	    helper.setSubject(subject);
	     
	    content = content.replace("[[name]]", user.getFirstName());
	     
	    helper.setText(content, true);
	     
	    mailSender.send(message);
	     
	}
}
