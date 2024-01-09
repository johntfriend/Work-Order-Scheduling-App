package sru.edu.group1.workorders.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import sru.edu.group1.workorders.domain.Buildings;
import sru.edu.group1.workorders.domain.Departments;
import sru.edu.group1.workorders.domain.Incidents;
import sru.edu.group1.workorders.domain.Users;
import sru.edu.group1.workorders.repository.BuildingRepository;
import sru.edu.group1.workorders.repository.DepartmentsRepository;
import sru.edu.group1.workorders.repository.IncidentRepository;
import sru.edu.group1.workorders.repository.UserRepository;
import sru.edu.group1.workorders.services.*;
import sru.edu.group1.workorders.domain.Rooms;
import sru.edu.group1.workorders.repository.RoomsRepository;

/**This is the main controller for the project, includes all user, admin, manager, and technician links.
 * Although those may get separated later with the project.
 * 
 * @author Trevor Bell
 * @author Ellie Wurst
 * @author John Friend
 * @author Hunter Minteer
 *
 */
@Controller
public class WorkOrdersController {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BuildingRepository buildingRepository;
	@Autowired
	private DepartmentsRepository departmentsRepository;
	@Autowired
	private IncidentRepository incidentRepository;
	@Autowired
	private RoomsRepository roomsRepositry;
	@Autowired
	private UserServices userService;
	@Autowired
	private IncidentService incidentService;

	/**
	 * A function to adds an admin, manager, technician, and basic user so no registration has to be done to access the project
	 * their passwords can be found within userService.java
	 */
	public void addUsers()
	{
		Users adminUser = userRepository.findByEmail("adminRole@sru.edu");
		Users managerUser = userRepository.findByEmail("managerRole@sru.edu");
		Users normalUser = userRepository.findByEmail("userRole@sru.edu");
		Users techUser = userRepository.findByEmail("techRole@sru.edu");
		if(adminUser == null && managerUser == null && normalUser == null && techUser == null)
		{
		userService.addAdminUser();
		userService.addManagerUser();
		userService.addBasicUser();	
		userService.addTechUser();
		}
		if (normalUser == null)
		{
		userService.addBasicUser();	
		}
		if (adminUser == null)
		{
		userService.addAdminUser();
		}
		if (managerUser == null)
		{
		userService.addManagerUser();
		}
		if (techUser == null)
		{
		userService.addTechUser();
		}
	}
	
	/**
	 * Populates the buildings table with starting data and connects them to department and rooms
	 */
	public void populateBuildings()
	{
		Buildings ATS = buildingRepository.findByBuilding("ATS");
		Buildings Eisenburg = buildingRepository.findByBuilding("Eisenburg");
		Buildings studentCenter = buildingRepository.findByBuilding("Student Center");
		Buildings SPOTTS = buildingRepository.findByBuilding("SPOTTS");
		
		List<Rooms> rooms = roomsRepositry.findAll();
		List<Departments> departments = departmentsRepository.findAll();
		if(ATS.getRooms().isEmpty())
		{
			ATS.addRooms(rooms.get(0));
			ATS.addDepartments(departments.get(0));
			buildingRepository.save(ATS);
		}
		if(SPOTTS.getRooms().isEmpty())
		{
			SPOTTS.addRooms(rooms.get(1));
			SPOTTS.addDepartments(departments.get(1));
			buildingRepository.save(SPOTTS);
		}
		if(Eisenburg.getRooms().isEmpty())
		{
			Eisenburg.addRooms(rooms.get(2));
			Eisenburg.addDepartments(departments.get(1));
			buildingRepository.save(Eisenburg);
		}
		if(studentCenter.getRooms().isEmpty())
		{
			studentCenter.addRooms(rooms.get(3));
			studentCenter.addDepartments(departments.get(2));
			buildingRepository.save(studentCenter);
		}
		
	}
	
	/**
	 * default login page when anyone enters "localhost:8080" into their serach engine's search bar
	 * shows a login page and allows people to login
	 * @param user holds the information about the user
	 * @param bindingResult holds information about data errors
	 * @return either a login page, or sends them to userlanding, or a different landing dependent on role
	 */
    @GetMapping("/")
	public String homepage(@ModelAttribute Users user, BindingResult bindingResult)
	{
    	addUsers();
    	populateBuildings();
    	if (bindingResult.hasErrors()) {
			return "login";
		}
    	String email = user.getEmail();
    	String pass = user.getPassword();
    	if(email == user.getEmail() && pass == user.getPassword())
    	{
    		return "login";
    	}
    	else
    	{
    		return "userlanding";
    	}
	}

    /**
     * when someone hits the register button on the login screen, this
     * is where they go to make a new user
     * @param model holds information to pass to the HTML page
     * @return the signup form 
     */
	@GetMapping("/login/register")
	public String userRegistration(Model model)
	{
		model.addAttribute("user", new Users());
		return "signupForm";
	}
	
	/**
	 * This is where a user gets sent once they hit the create user button on the registration page
	 * @param user holds the info of the user to be stored
	 * @param request holds information for the email to send properly, for HTTP purposes
	 * @param bindingResult holds information about data errors
	 * @return can return a success page, the signup form, or two different error pages depending on the error
	 * @throws UnsupportedEncodingException holds errors for encoding methods
	 * @throws MessagingException holds errors for basic messaging methods
	 */
	@PostMapping("/login/process_register") // happens when user submits the registration form
	public String processRegister(@ModelAttribute Users user, HttpServletRequest request, BindingResult bindingResult) throws UnsupportedEncodingException, MessagingException {
		if (bindingResult.hasErrors()) {
			return "signupForm";
		}
		
		String pass = user.getPassword();
		String email = user.getEmail();
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		// stores encoded password instead of plain text
		String encodedPassword = encoder.encode(pass);
		
		//The below method is to check for failed passwords and then if accounts already exist with the specific email
		if(checkPassword(pass) == false)
		{
			return "registerFailure";
		}
		else if (userRepository.existsByEmail(email)) {
			return "registrationDupeFailure";
		}
		else
		{
			user.setPassword(encodedPassword);
		
			if (checkEmail(user) == false) {
				return "registerFailure";
			}
			else{
				userService.register(user, getSiteURL(request));
				return "registrationSuccess";
			}
		}
	}

	/**
	 * This is to make sure that while a person is registering an email, it ends with '@sru.edu' 
	 * @param user passes in the user data
	 * @return if the email is a sru email or not
	 */
	public Boolean checkEmail(Users user){
		String userEmail = user.getEmail();
		
		String segments[] = userEmail.split("@");
		String lastSegment = segments[segments.length - 1];
		
		if(lastSegment.equals("sru.edu")){
			return true;
		}
		else{
			return false;
		}
	}
		
    /**
     * This will check the users password to make sure it's a certain length and return false if not
     * @param password is the password the user enters for registration
     * @return returns if the password is the proper length or not
     */
	public boolean checkPassword(String password)
    {
    	if (password.length() < 6)
    	{
    		return false;
    	}
    	else if (password.length() > 15)
    	{
    		return false;
    	}
    	else
    	{
    		return true;
    	}
    }
	

    /**
     * This is for getting the proper request data for emails
     * @param request holds information for HTTP requests such as sending an email
     * @return returns the request data for emails
     */
	private String getSiteURL(HttpServletRequest request)
	{
		String siteURL = request.getRequestURL().toString(); 
		return siteURL.replace(request.getServletPath(), "");
	}
	
	/**
	 * when a user goes to verify their account with the email, if it succeeds it'll bring them to a success page, and a fail otherwise
	 * @param code this will hold the verification code within a user
	 * @return either a success or failure page
	 */
	@GetMapping("/verify")
	public String verifyUser(@Param("code") String code)
	{
		if(userService.verify(code))
		{
			return "verifySuccess";
		}
		else
		{
			return "verifyFailure";
		}
	}
	
	
	/**
	 * The user landing page, where they can see their profile, see incidents, and a number of other options
	 * @param username Holds the name of the person logged in to display in the corner
	 * @param model holds information to send to the HTML pages, such as the username
	 * @return the userlanding page
	 */
	@GetMapping("/group/userLanding")
	public String viewHomePage(@CurrentSecurityContext(expression="authentication?.name")
    String username, Model model) 
	{
		model.addAttribute("username", username);
		return "userLanding";
	}

	/**
	 * Basically an FAQ page, with a bunch of different sub-pages where there is some information about that issue and then a link to the incident report page
	 * @param username holds name of logged in user to display
	 * @param model holds information like username to pass to the HTML pages
	 * @return returns a new incident report page
	 */
	@GetMapping("/group/newIncident")
	public String newInc(@CurrentSecurityContext(expression="authentication?.name")
			String username, Model model) 
	{
		model.addAttribute("username", username);
		return "incidentPage";
	}

	/**
	 * Where a user goes to see their incidents, can only see incidents they have reported, allows them to see the current status and who is assigned if anyone
	 * @param username as always holds the username to display
	 * @param model holds information, specifically a username to pass to html
	 * @param model2 holds all the information for incidents, and will display only those with the same email as the user
	 * @return a userIncident page
	 */
	@GetMapping("/group/status")
	public String statusList(@CurrentSecurityContext(expression="authentication?.name")
		String username, Model model, Model model2) 
	{
		List<Incidents> listIncident = incidentService.listAll();
		model2.addAttribute("incidents", listIncident);
		model.addAttribute("username", username);
		return "userIncidents";
	}
	
	/**
	 * a profile page for the different roles, each role can have access to different buttons, but mostly the concept to change a password are the same between
	 * @param authentication hold role to check against others
	 * @param username holds the current user's email
	 * @param model passes the user's information into the html
	 * @param model2 passes the user's email into the html
	 * @return an admin, user, manager, or technician profile page
	 */
	@GetMapping("/group/profile")
	public String userProfile(Authentication authentication,@CurrentSecurityContext(expression="authentication?.name") String username, Model model, Model model2)
	{
		Users user = userRepository.findByEmail(username);
		model2.addAttribute("userDetails", user);
		model.addAttribute("username", username);
		return "userProfile";
	}
	
	/**
	 * located on the login page and used to get another verification email if needed
	 * @return returns the resend verification page
	 */
	@GetMapping("/resendVerificationEmail")
	public String sendUserVerificationEmail()
	{
		return "resendVerificationEmail";
	}
	
	/**
	 * checks the email and sends the verification email
	 * @param request holds information for HTTP requests such as sending an email
	 * @return can return the userNullError page, verificationError page, or resendVerification page with the verification email
	 * @throws UnsupportedEncodingException holds errors for encoding methods
	 * @throws MessagingException holds errors for basic messaging methods
	 */
	@PostMapping("/resendVerificationEmail")
	public String processResentVerificationEmail(HttpServletRequest request) throws UnsupportedEncodingException, MessagingException
	{
		String currentEmail = request.getParameter("email");
		
		Users user = userRepository.findByEmail(currentEmail);
		
		if(user == null)
		{
			return "userNullError";
		}
		else if(user.isEnabled() == true)
		{
			return "verificationError";
		}
		else
		{
			userService.reSendVerificationEmail(user, getSiteURL(request));
			return "resendVerificationEmail";
		}
		
	}
	
	/**
	 * email FAQ page
	 * @param username holds the current logged in user's email
	 * @param model passes username into the html
	 * @return returns the email FAQ page
	 */
	@GetMapping ("/group/email")
	public String email(@CurrentSecurityContext(expression="authentication?.name") String username, Model model)
	{
		model.addAttribute("username", username);
		return "email";
	}
	
	/**
	 * computer and hardware FAQ page
	 * @param username holds the current logged in user's email
	 * @param model passes username into the html
	 * @return returns the computer and hardware FAQ page
	 */
	@GetMapping ("/group/computer")
	public String computer(@CurrentSecurityContext(expression="authentication?.name") String username, Model model)
	{
		model.addAttribute("username", username);
		return "computer";
	}
	
	/**
	 * classroom troubleshooting FAQ page
	 * @param username holds the current logged in user's email
	 * @param model passes username into the html
	 * @return returns the classroom troubleshooting FAQ page
	 */
	@GetMapping ("/group/classrooms")
	public String classrooms(@CurrentSecurityContext(expression="authentication?.name") String username, Model model)
	{
		model.addAttribute("username", username);
		return "classrooms";
	}
	
	/**
	 * campus FAQ page
	 * @param username holds the current logged in user's email
	 * @param model passes username into the html
	 * @return returns the campus FAQ page
	 */
	@GetMapping ("/group/campus")
	public String campus(@CurrentSecurityContext(expression="authentication?.name") String username, Model model)
	{
		model.addAttribute("username", username);
		return "campus";
	}
	
	/**
	 * wifi FAQ page
	 * @param username holds the current logged in user's email
	 * @param model passes username into the html
	 * @return returns the wifi FAQ page
	 */
	@GetMapping ("/group/wifi")
	public String wifi(@CurrentSecurityContext(expression="authentication?.name") String username, Model model)
	{
		model.addAttribute("username", username);
		return "wifi";
	}
	
	/**
	 * phone and fax FAQ page
	 * @param username holds the current logged in user's email
	 * @param model passes username into the html
	 * @return returns the phone and fax FAQ page
	 */
	@GetMapping ("/group/phoneandfax")
	public String phonefax(@CurrentSecurityContext(expression="authentication?.name") String username, Model model)
	{
		model.addAttribute("username", username);
		return "phonefax";
	}
	
	/**
	 * data reporting FAQ page
	 * @param username holds the current logged in user's email
	 * @param model passes username into the html
	 * @return returns the data reporting FAQ page
	 */
	@GetMapping ("/group/data")
	public String data(@CurrentSecurityContext(expression="authentication?.name") String username, Model model)
	{
		model.addAttribute("username", username);
		return "data";	
	}
	
	/**
	 * server and database FAQ page
	 * @param username holds the current logged in user's email
	 * @param model passes username into the html
	 * @return returns the server and database FAQ page
	 */
	@GetMapping ("/group/servers")
	public String servers(@CurrentSecurityContext(expression="authentication?.name") String username, Model model)
	{
		model.addAttribute("username", username);
		return "servers";
	}
	
	/**
	 * user account issues FAQ page
	 * @param username holds the current logged in user's email
	 * @param model passes username into the html
	 * @return returns the account issues FAQ page
	 */
	@GetMapping ("/group/userissues")
	public String useracc(@CurrentSecurityContext(expression="authentication?.name") String username, Model model)
	{
		model.addAttribute("username", username);
		return "userIssues";
	}
	
	@GetMapping ("/group/viewUpdates/{report_id}")
	public String updates(@CurrentSecurityContext(expression="authentication?.name") String username,@PathVariable(name = "report_id") Long report_id, Model model) {
		model.addAttribute("username", username);
		Incidents incidents = incidentRepository.findById(report_id).get();
		model.addAttribute("incident", incidents);
		return "viewUpdates";	
	}
	/**
	 * when a user needs to submit a report form, instead of using the FAQ, this is the page they will go to.
	 * @param incident holds the information to save to the DB
	 * @param building pulls the building information to show in the HTML page
	 * @return a page to submit a new incident
	 */
	@GetMapping("/group/incidentReport")
	public String newUserIncidentForm(@CurrentSecurityContext(expression="authentication?.name")String username, Model incident, Model building, Model model) {
		Incidents incidents = new Incidents();
		
		incident.addAttribute("incidents", incidents); //is used for creating and adding in a new incident
		building.addAttribute("buildings", buildingRepository.findAll()); //accessing building information
		model.addAttribute("username", username);

	return "userNewIncident";
	}

	/**
	 * 	 this is what happens when a user goes to submit an incident, it saves it in the database
	 * @param username holds the name of the currently logged in user
	 * @param incidents holds incident information to save to the DB
	 * @param bindingResult holds errors about validation
	 * @param request holds data for HTTP services
	 * @return an incident success page that will let them return to the landing page or see their incidents
	 * @throws UnsupportedEncodingException holds error information for encoding methods
	 * @throws MessagingException holds error information for basic messaging methods
	 */
	@PostMapping("/group/userIncidentSave")
	public String saveUserIncident(@CurrentSecurityContext(expression="authentication?.name")String username, @ModelAttribute("incidents") Incidents incidents, BindingResult bindingResult, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
		if (bindingResult.hasErrors()) {
			return "userNewIncident";
		}
		long buildingid = Long.valueOf(incidents.getBuilding());
		Buildings realBuilding = buildingRepository.findById(buildingid);
		incidents.setBuilding(realBuilding.getBuilding());
		
		incidentService.saveWithEmail(incidents, username, getSiteURL(request));
		
		return "redirect:/group/status";
	}
}
