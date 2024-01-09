package sru.edu.Group1.WorkOrders;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import sru.edu.group1.workorders.domain.Incidents;
import sru.edu.group1.workorders.domain.Users;
import sru.edu.group1.workorders.services.IncidentService;

public class IncidentEmailTest {

	@Autowired
	private IncidentService incidentService;
	
	
	private String getSiteURL(HttpServletRequest request)
	{
		String siteURL = request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(), "");
	}
	
	@Test
	void incidentSendEmail() throws UnsupportedEncodingException, MessagingException
	{
		
	}
}
