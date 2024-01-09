package sru.edu.group1.workorders.services;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

import jakarta.mail.MessagingException;
import sru.edu.group1.workorders.domain.Incidents;
import sru.edu.group1.workorders.domain.Users;
import sru.edu.group1.workorders.repository.IncidentRepository;

@Service
public class IncidentService {
	@Autowired
	private IncidentRepository repo;
	@Autowired
    private JavaMailSender mailSender;	
	
	public List<Incidents> listAll() {
		return repo.findAll();
	}
	
	public void saveWithEmail(Incidents incidents, String email, String siteURL) throws UnsupportedEncodingException, MessagingException {
		sendIncidentEmail(email, siteURL);
		incidents.setEmail(email);
		repo.save(incidents);
	}
	
	public void resaveIncident(Incidents incidents)
	{
		repo.save(incidents);
	}
	
	public void saveWithTech(Incidents incidents, Users user)
	{
		String techName = user.getEmail();
		
		incidents.setAssign_email(techName);
		repo.save(incidents);
	}
	
	public void saveAssignEmail(Incidents incidents, String assign_email, String siteURL) throws UnsupportedEncodingException, MessagingException
	{
		
		incidents.setAssign_email(assign_email);
		repo.save(incidents);
		sendAssignEmail(incidents, siteURL);
		
	}
	
	public Incidents get(Long report_id) {
		return repo.findById(report_id).get();
	}
	
	public void delete(Long report_id) {
		repo.deleteById(report_id);
	}

	public void updateStatusClosed(Incidents incidents, String siteURL) throws UnsupportedEncodingException, MessagingException {
		incidents.setStatus("CLOSED");
		sendCompletedEmail(incidents, siteURL);
	}

	public void updateStatusActive(Incidents incidents, String siteURL) throws UnsupportedEncodingException, MessagingException {
		incidents.setStatus("ACTIVE");
		sendActiveEmail(incidents, siteURL);
	}

	private void sendIncidentEmail(String email, String siteURL)
	        throws MessagingException, UnsupportedEncodingException {
	    String toAddress = email;
	    String fromAddress = "thekidwithoutalife@gmail.com";
	    String senderName = "Group 1 - Work Order System";
	    String subject = "Thanks for your issue!";

	    String htmlContent = "<h1>Thank you for Registering an Issue </h1>"
	    		+ "<p>We will begin working on your issue ASAP, and click the link below to see your current incidents! </p>"
	    		+ "<a href = http://localhost:8080/group/status> Click here to see your incidnets! </a>";
	    
	     
	    jakarta.mail.internet.MimeMessage message = mailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	     
	    helper.setFrom(fromAddress, senderName);
	    helper.setTo(toAddress);
	    helper.setSubject(subject);
	 
	    message.setContent(htmlContent, "text/html; charset=utf-8");   
	     
	    mailSender.send(message);
	     
	}
	private void sendCompletedEmail(Incidents incident, String siteURL)
			throws MessagingException, UnsupportedEncodingException {
	    String toAddress = incident.getEmail();
	    String fromAddress = "thekidwithoutalife@gmail.com";
	    String senderName = "Group 1 - Work Order System";
	    String subject = "Thanks for your issue!";

	    String htmlContent = "<h1>Your Issue has been completed!</h1>";
	   
	    jakarta.mail.internet.MimeMessage message = mailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	     
	    helper.setFrom(fromAddress, senderName);
	    helper.setTo(toAddress);
	    helper.setSubject(subject);
	 
	 message.setContent(htmlContent, "text/html; charset=utf-8");   
	     
	    mailSender.send(message);
	}
	
	private void sendActiveEmail(Incidents incident, String siteURL)
			throws MessagingException, UnsupportedEncodingException {
	    String toAddress = incident.getEmail();
	    String fromAddress = "thekidwithoutalife@gmail.com";
	    String senderName = "Group 1 - Work Order System";
	    String subject = "Thanks for your issue!";

	    String htmlContent = "<h1>Your Issue is Now Active!</h1>";
	   
	    jakarta.mail.internet.MimeMessage message = mailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	     
	    helper.setFrom(fromAddress, senderName);
	    helper.setTo(toAddress);
	    helper.setSubject(subject);
	 
	 message.setContent(htmlContent, "text/html; charset=utf-8");   
	     
	    mailSender.send(message);
	}
	
	private void sendAssignEmail(Incidents incident, String siteURL)
			throws MessagingException, UnsupportedEncodingException {
	    String toAddress = incident.getAssign_email();
	    String fromAddress = "thekidwithoutalife@gmail.com";
	    String senderName = "Group 1 - Work Order System";
	    String subject = "Assignment!";

	    String htmlContent = "<h1>You Have Been Assigned A Incident!</h1>";
	   
	    jakarta.mail.internet.MimeMessage message = mailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	     
	    helper.setFrom(fromAddress, senderName);
	    helper.setTo(toAddress);
	    helper.setSubject(subject);
	 
	 message.setContent(htmlContent, "text/html; charset=utf-8");   
	     
	    mailSender.send(message);
	}

}