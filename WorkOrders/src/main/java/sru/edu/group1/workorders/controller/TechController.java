package sru.edu.group1.workorders.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import sru.edu.group1.workorders.domain.Incidents;
import sru.edu.group1.workorders.domain.Users;
import sru.edu.group1.workorders.repository.IncidentRepository;
import sru.edu.group1.workorders.services.IncidentService;
import sru.edu.group1.workorders.services.UserServices;


/**This is the controllers for the "worker" role of the project; Technicians. 
 * They can be assigned too, or take on any open work order, they are also able to update and close incidents they are assigned too.
 * 
 * @author Trevor Bell
 * @author Ellie Wurst
 * @author John Friend
 * @author Hunter Minteer
 */
@Controller
@RequestMapping("/tech")
public class TechController {

	@Autowired	
	private IncidentRepository incidentRepository;	
	@Autowired
	private UserServices userService;
	@Autowired
	private IncidentService incidentService;
	
	/**
	 * The tech landing page, where they can see their profile, see a list of incidents, and a number of other options
	 * @param username Holds the name of the person logged in to display in the corner
	 * @param model holds information to send to the HTML pages, such as the username
	 * @return the tech landing page
	 */
	@GetMapping("/Landing")
	public String viewTechPage(@CurrentSecurityContext(expression="authentication?.name")
    String username, Model model)
	{
		model.addAttribute("username", username);
		return "techLanding";
	}
	
	/**
	 * A list of all incidents in the DB, for the tech to look at, there are options such as self-assigning avaliable to the techs as well
	 * @param username holds a username from the logged in user
	 * @param model holds information to pass to the HTML page
	 * @return a list of all the incidents with certain options avaliable
	 */
	@GetMapping("/techIncidents")
	public String asignedIncidents(@CurrentSecurityContext(expression="authentication?.name") String username, Model model) {
		List<Incidents> listIncident = incidentService.listAll();
		model.addAttribute("incidents", listIncident);
		model.addAttribute("username", username);
	return "techIncidentList";
	}
	
	/**
	 * When a tech assigns themself to an incident, it will attache their email to the incident, as well as resaving the incident in question
	 * @param username holds username to apply the tech email to the incident
	 * @param model holds the username to give to the HTML page to display it
	 * @param report_id holds the incidents ID that is in question
	 * @param user will hold the information about the logged in user
	 * @param request holds information for use with HTTP services, such as sending an email
	 * @return the tech back to the incident list, and will move the incident to the top of the page
	 * @throws UnsupportedEncodingException holds error for encoding methods
	 * @throws MessagingException holds errors for messaging methods
	 */
	@RequestMapping("/selfAssign/{report_id}")
	public String selfAssign(@CurrentSecurityContext(expression="authentication?.name") String username, Model model, @PathVariable(name = "report_id") Long report_id, Users user, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
		Incidents incidents = incidentService.get(report_id);
		model.addAttribute("username", username);
		incidents.setAssign_email(username);
		incidentService.resaveIncident(incidents);
	return "redirect:/tech/techIncidents";
	}
	
	/**
	 * once assigned, a technician will then gain the option to close an incident, or list that they it has been completed
	 * After that the user who submitted the incident will get an email saying it has been completed
	 * @param report_id holds the id of the report in quetion
	 * @param incident holds all incident information
	 * @param request holds information for HTTP requests such as sending an email
	 * @return the tech will return to the incident page, with the incident now marked as closed
	 * @throws UnsupportedEncodingException holds error infromation for encoding methods
	 * @throws MessagingException holds error information for basic messaging methods
	 */
	@RequestMapping("/closeIncident/{report_id}")
	public String closeIncident(@PathVariable(name = "report_id") Long report_id, Model incident, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
		Incidents incidents = incidentService.get(report_id);
		incidentService.updateStatusClosed(incidents, getSiteURL(request));
		incidentService.resaveIncident(incidents);
	return "redirect:/tech/techIncidents";
	}
	
	/**
	 * Brings up a form for the tech to update the user 
	 * @param report_id holds the report's id that is being updated
	 * @param update holds the email contents sent by the tech
	 * @param incident holds the information related to the specific incident
	 * @param model passes the update and incident values into the html
	 * @return returns the update incident form
	 */
	@GetMapping("/updateIncident/{report_id}")
	public String updateIncident(@PathVariable(name = "report_id") Long report_id, String update,String incident, Model model) {
		model.addAttribute("update", update);
		Incidents incidents = incidentRepository.findById(report_id).get();
		model.addAttribute("incident", incidents);
		return "updateIncident";
	}
	
	/**
	 * Takes the data from the update incident form and sends a email to update the user on the incident
	 * @param report_id holds the report's id that is being updated
	 * @param request holds information for HTTP requests such as sending an email
	 * @param incident holds the incidents information
	 * @return returns the tech to the tech landing page
	 * @throws UnsupportedEncodingException holds error infromation for encoding methods
	 * @throws MessagingException holds error information for basic messaging methods
	 */
	@PostMapping("/processUpdateIncident/{report_id}")
	public String processUpdateIncident(@PathVariable(name = "report_id") Long report_id, HttpServletRequest request, Model incident) throws UnsupportedEncodingException, MessagingException {
		String update = request.getParameter("update");
		Incidents incidents = incidentRepository.findById(report_id).get();
		userService.sendUpdateIncident(incidents.getEmail(), update);
		incidents.setUpdates(update);
		incidentRepository.save(incidents);
		return "techLanding";
	}

	
	/**
	 * once assigned, a technician will then gain the option to mark an incident as active
	 * After that the user who submitted the incident will get an email saying it is being worked on
	 * @param report_id holds the id of the report that was selected
	 * @param incident holds all incident information
	 * @param request holds information for HTTP requests such as sending an email
	 * @return the tech will return to the incident page, with the incident now marked as active
	 * @throws UnsupportedEncodingException holds error information for encoding methods
	 * @throws MessagingException holds error information for basic messaging methods
	 */
	@RequestMapping("/activeIncident/{report_id}")
	public String activeIncident(@PathVariable(name = "report_id") Long report_id, Model incident, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
		Incidents incidents = incidentService.get(report_id);
		incidentService.updateStatusActive(incidents, getSiteURL(request));
		incidentService.resaveIncident(incidents);
	return "redirect:/tech/techIncidents";
	}
	
	/**
	 * This is a mehtod that's fairly common in the project, only used for email purposes
	 * @param request holds request information for HTTP requests for email
	 * @return a string value to be used with emails
	 */
	private String getSiteURL(HttpServletRequest request)
	{
		String siteURL = request.getRequestURL().toString(); 
		return siteURL.replace(request.getServletPath(), "");
	}
}
