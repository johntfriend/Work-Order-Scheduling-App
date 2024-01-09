package sru.edu.group1.workorders.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import sru.edu.group1.workorders.domain.Buildings;
import sru.edu.group1.workorders.domain.Incidents;
import sru.edu.group1.workorders.domain.Role;
import sru.edu.group1.workorders.domain.Users;
import sru.edu.group1.workorders.repository.BuildingRepository;
import sru.edu.group1.workorders.repository.DepartmentsRepository;
import sru.edu.group1.workorders.repository.RoleRepository;
import sru.edu.group1.workorders.repository.RoomsRepository;
import sru.edu.group1.workorders.repository.UserRepository;
import sru.edu.group1.workorders.services.IncidentService;

/**This is the controller for the Manager role of the project. Managers are able to control the incident side of the project.
 * Able to create, edit, delete, and assign technicians to any incident. 
 * 
 * @author Trevor Bell
 * @author Ellie Wurst
 * @author John Friend
 * @author Hunter Minteer
 *
 */
@Controller
@RequestMapping("/manager")
public class ManagerController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BuildingRepository buildingRepository;
	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private IncidentService incidentService;
	@Autowired
	private BuildingRepository buildingRepo;
	@Autowired
	private DepartmentsRepository departmentRepo;
	@Autowired
	private RoomsRepository roomRepo;

	
	/**
	 * The landing page for the Manager roles, has a list of buttons for all of their options,
	 * including excel file uploading and the connection of data
	 * @param username holds the logged in user to display
	 * @param model passes information to the HTML page.
	 * @return
	 */
	@GetMapping("/Landing")
	public String viewManagerPage(@CurrentSecurityContext(expression="authentication?.name")
    String username, Model model)
	{
		model.addAttribute("username", username);
		return "managerLanding";
	}
	
	
	/**
	 * A list of all incidents in the DB, for the manager to look at, there are options such as assign technician as well
	 * @param username holds a username from the logged in user
	 * @param model holds the username to pass to the HTML page
	 * @param model2 holds the incidents to pass to the HTML page
	 * @param techUsers holds all accounts with the tech role
	 * @return a list of all the incidents with certain options avaliable
	 */
	@GetMapping("/managerIncidentList")
	public String managerIncidents(@CurrentSecurityContext(expression="authentication?.name") String username, Model model, Model model2, Model techUsers) {
		Role techRole = roleRepo.findByname("Tech");

		List<Users> listTechs = userRepository.findByRoles(techRole);
		List<Incidents> listIncident = incidentService.listAll();
		
		techUsers.addAttribute("allTechs", listTechs);
		model2.addAttribute("incidents", listIncident);
		model.addAttribute("username", username);
	return "managerIncidentList";
	}
	
	/**
	 * when a manager needs to submit a report form, instead of using the FAQ, this is the page they will go to.
	 * @param incident holds the information to save to the DB
	 * @param building pulls the building information to show in the HTML page
	 * @return a page to submit a new incident
	 */
	@GetMapping("/incidentReport")
	public String newManagerIncidentForm(@CurrentSecurityContext(expression="authentication?.name") String username,Model incident, Model building, Model model) {
		Incidents incidents = new Incidents();
				
		incident.addAttribute("incidents", incidents); //is used for creating and adding in a new incident
		building.addAttribute("buildings", buildingRepository.findAll()); //accessing building information
		model.addAttribute("currentEmail", username);

		incidents.setEmail(username);
	return "managerNewIncident";
	}
	
	/**
	 * this is what happens when a manager goes to submit an incident, it saves it in the database
	 * @param incidents holds incident information to save to the DB
	 * @param bindingResult holds errors about validation
	 * @param user holds user information, to get an email to send to the user once they submit the issue
	 * @param request holds data for HTTP services
	 * @return an incident success page that will let them return to the landing page or see their incidents
	 * @throws UnsupportedEncodingException holds error information for encoding methods
	 * @throws MessagingException holds error information for basic messaging methods
	 */
	@PostMapping("/managerIncidentSave")
	public String saveManagerIncident(@ModelAttribute("incidents") Incidents incidents, @CurrentSecurityContext(expression="authentication?.name")String username, BindingResult bindingResult, Users user, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
		if (bindingResult.hasErrors()) {
			return "managerNewIncident";
		}
		long buildingid = Long.valueOf(incidents.getBuilding());
		Buildings realBuilding = buildingRepository.findById(buildingid);
		incidents.setBuilding(realBuilding.getBuilding());
		//incidents.setEmail(username);
		incidentService.resaveIncident(incidents);
		
		return "redirect:/manager/managerIncidentList";
	}
	
	/**
	 * Located on the managerIncidentList page that allows managers to assign technicians to reports
	 * @param report_id holds the id of the report that was selected
	 * @param assign hold the email of the tech that was selected for assignment
	 * @param incident
	 * @param request holds data for HTTP services
	 * @param incident holds all incident information
	 * @throws UnsupportedEncodingException holds error information for encoding methods
	 * @throws MessagingException holds error information for basic messaging methods
	 */
	@RequestMapping("/managerIncidentAssign/{report_id}")
	public String saveManagerAssign(@PathVariable(name = "report_id") Long report_id,@RequestParam(value = "assign") String assign, Model incident, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
		
		Incidents incidents = incidentService.get(report_id);
		assign = assign.replace(",", "");
		incidentService.saveAssignEmail(incidents, assign, getSiteURL(request));
		
		return "redirect:/manager/managerIncidentList";
	}
	
	/**
	 * once assigned, a manager will then gain the option to close an incident, or list that they it has been completed
	 * After that the user who submitted the incident will get an email saying it has been completed
	 * @param report_id holds the id of the report that was selected
	 * @param incident holds all incident information
	 * @param request holds information for HTTP requests such as sending an email
	 * @return the manger will return to the incident page, with the incident now marked as closed
	 * @throws UnsupportedEncodingException holds error information for encoding methods
	 * @throws MessagingException holds error information for basic messaging methods
	 */
	@RequestMapping("/closeIncident/{report_id}")
	public String managerCloseIncident(@PathVariable(name = "report_id") Long report_id, Model incident, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
		Incidents incidents = incidentService.get(report_id);
		incidentService.updateStatusClosed(incidents, getSiteURL(request));
		incidentService.resaveIncident(incidents);
	return "redirect:/manager/managerIncidentList";
	}

	/**
	 * This will show all current information regarding buildings, departments, and rooms in the Database,
	 * And allows a manager to connect a buildling with Rooms and Departments. 
	 * @param model passes all needed information to the HTML page.
	 * @return the dataconnect page, this one is specific to manager, could be made universal by swapping it to the excel controller.
	 */
	@GetMapping("/dataConnect")
	public String connectData(Model model)
	{
		model.addAttribute("Buildings", buildingRepo.findAll());
		model.addAttribute("Departments", departmentRepo.findAll());
		model.addAttribute("Rooms", roomRepo.findAll());
		return "dataConnect";
	}
	
    /**
     * This is for getting the proper request data for emails, found in many places in the project
     * @param request holds information for HTTP requests such as sending an email
     * @return returns the request data for emails
     */
	private String getSiteURL(HttpServletRequest request)
	{
		String siteURL = request.getRequestURL().toString(); 
		return siteURL.replace(request.getServletPath(), "");
	}
	
}
