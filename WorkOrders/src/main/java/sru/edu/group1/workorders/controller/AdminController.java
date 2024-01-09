package sru.edu.group1.workorders.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import sru.edu.group1.workorders.domain.*;
import sru.edu.group1.workorders.repository.BuildingRepository;
import sru.edu.group1.workorders.repository.DepartmentsRepository;
import sru.edu.group1.workorders.repository.RoleRepository;
import sru.edu.group1.workorders.repository.RoomsRepository;
import sru.edu.group1.workorders.repository.UserRepository;
import sru.edu.group1.workorders.services.*;


/**This is the controller for the Administration role within our project. Admin will mostly deal with user data,
 * they are also able to upload excel data which affects how incidents are created by the users. 
 * 
 * @author Trevor Bell
 * @author Ellie Wurst
 * @author John Friend
 * @author Hunter Minteer
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private BuildingsService buildingsService;
	@Autowired
	private DepartmentsService departmentsService;
	@Autowired
	private RoomServices roomService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserManagementService userManagementService;
	@Autowired
	private AdminServices adminService;
	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private UserServices userService;
	@Autowired
	private BuildingRepository buildingRepo;
	@Autowired
	private DepartmentsRepository departmentRepo;
	@Autowired
	private RoomsRepository roomRepo;
	
	/**
	 * The admin landing page, where they can see their profile, see a list of users, and a number of other options
	 * @param username Holds the name of the person logged in to display in the corner
	 * @param model holds information to send to the HTML pages, such as the username
	 * @return the admin landing page
	 */
	@GetMapping("/Landing")
	public String viewAdminPage(@CurrentSecurityContext(expression="authentication?.name")
    String username, Model model)
	{
		model.addAttribute("username", username);
		return "adminLanding";
	}
	
	/**
	 * Shows all the buildings, departments, and rooms currently in the system, and Admin can add/delete them individually
	 * @param building holds the information for the buildings
	 * @param department holds department information
	 * @param rooms holds room information
	 * @return a page with all known rooms, buildings, and departments
	 */
	@GetMapping("/locationList")
	public String adminListLocations(Model building, Model department, Model rooms) {

		List<Buildings> buildingList = buildingsService.listAll();
		List<Departments> departmentList = departmentsService.listAll();
		List<Rooms> roomList = roomService.listAll();

		department.addAttribute("department", departmentList);
		building.addAttribute("building", buildingList);
		rooms.addAttribute("room", roomList);
		return "adminLocationList";
	}

	/**
	 * Shows a list of all users in the system besides the logged in admin
	 * @param username holds username of the admin logged in
	 * @param model adds data to be used in the HTML page
	 * @return the adminUserList html page
	 */
	@GetMapping("/userList")
	public String userList(@CurrentSecurityContext(expression = "authentication?.name") String username, Model model) {
		List<Users> listUsers = userRepository.findAll();
		model.addAttribute("listUsers", listUsers);
		model.addAttribute("username", username);
		model.addAttribute("roles", roleRepo.findAll());
		return "adminUserList";
	}

	/**
	 * When the change role button is pressed on the AdminUserList page, this is the mapping that gets called to properly change the role of the selected user
	 * @param id holds the id of the user to find
	 * @param role holds the name of the role to find
	 * @return back to the admin list with the newly updated user information
	 */
	@GetMapping("/changeRole/{id}/{role}")
	public String ChangeRoles(@PathVariable(name = "id") long id, @PathVariable(name = "role") String role)
	{
		Users user = userRepository.findById(id);
		Role roles = roleRepo.findByname(role);
		user.deleteRole();
		user.addRole(roles);
		userRepository.save(user);
		return "redirect:/admin/userList";
	}
	
	/**
	 * Occurs whenever you select an account to delete and brings you to a confirm delete page
	 * @param id is the id of the selected user
	 * @param model passes the user information to the HTML
	 * @return returns the admin delete confirmation page
	 */
	@GetMapping("/deleteAccountConfirm/{id}")
	public String adminDeleteAccountConfirm(@PathVariable(name = "id") Long id, Model model) {
		Users user = userRepository.findById(id).get();
		model.addAttribute("currentUser", user);
		return "adminDeleteConfirmation";
	}
	
	/**
	 * Occurs when you confirm to delete the selected account
	 * @param id holds the id of the selected account
	 * @return back to the user list with the deleted account
	 */
	@RequestMapping("/deleteAccount/{id}")
	public String adminDeleteAccount(@PathVariable(name = "id") Long id) {
		Users users = userRepository.findById(id).get();
		adminService.deleteUserAccount(users);
		return "redirect:/admin/userList";
	}

	/**
	 * Allows admin to change a users role to frozen, so they cannot access the project
	 * @param id holds the id of the user to be frozen
	 * @param user will use the id to freeze the user being held here
	 * @return back to the user list with the newly frozen account
	 */
	@RequestMapping("/freezeAccount/{id}")
	public String freezeAccount(@PathVariable(name = "id") Long id, Users user) {
		Users users = userManagementService.getUser(id);
		adminService.disableUserAccount(users);

		return "redirect:/admin/userList";
	}

	/**
	 * Enables the the selected account
	 * @param id holds the id of the selected user
	 * @param user holds the selected users information
	 * @return returns the admin to the list of users
	 */
	@GetMapping("/enableAccount/{id}")
	public String enableAccount(@PathVariable(name = "id") Long id, Users user) {
		user = userRepository.findById(id).get();
		adminService.enableUserAccount(user);
		return "redirect:/admin/userList";
	}
	
	/**
	 * Admin user registration page
	 * @return the form for an admin to register an account
	 */
	@GetMapping("/Registration")
	public String adminRegistration() {
		return "adminRegister";
	}

	/**
	 * This is the page where a new building can be added
	 * @param model holds the information the admin puts in to store into the DB
	 * @return shows a form to add a new building
	 */
	@RequestMapping("/building")
	public String buildingRegistration(Model model) {
		Buildings build = new Buildings();
		model.addAttribute("building", build);
		return "buildingForm";
	}

	/**
	 * Once a building is added, this is what's called to save it
	 * @param buildings holds the new set of building information to save
	 * @return the admin will go back to the list of buildings, rooms, and departments
	 */
	@RequestMapping(value = "/buildingsSave", method = RequestMethod.POST)
	public String processBuilding(@ModelAttribute("buildings") Buildings buildings) {
		buildingsService.save(buildings);
		return "redirect:/admin/locationList";
	}

	/**
	 * This page is for deleting any buildings in the DB for whatever reason
	 * @param id will look for a building id and remove that from the table
	 * @return the admin will go back to the location list, without the removed building
	 */
	@RequestMapping("/buildingDelete/{id}")
	public String adminDeleteBuilding(@PathVariable(name = "id") Long id) {
		buildingsService.delete(id);

		return "redirect:/admin/locationList";
	}

	/**
	 * The same as buildings but with departments, will see a page to individually add a department
	 * @param model holds information to send/receive from the HTML page
	 * @return a form to add a department
	 */
	@RequestMapping("/department")
	public String departmentRegistration(Model model) {
		Departments dept = new Departments();
		List<Buildings> listBuildings = buildingsService.listAll();
		model.addAttribute("buildings", listBuildings);
		model.addAttribute("department", dept);
		return "departmentForm";
	}

	/**
	 * Once a department is saved, this is what gets called
	 * @param departments holds the new department information to save to the DB
	 * @return back to the location list
	 */
	@RequestMapping(value = "/departmentsSave", method = RequestMethod.POST)
	public String processDeparment(@ModelAttribute("departments") Departments departments) {
		departmentsService.save(departments);
		return "redirect:/admin/locationList";
	}

	/**
	 * Admin can delete departments individually
	 * @param id will need the id of the specific department to delete
	 * @return back to the location list
	 */
	@RequestMapping("/departmentDelete/{id}")
	public String adminDeleteDepartment(@PathVariable(name = "id") Long id) {
		departmentsService.delete(id);

		return "redirect:/admin/locationList";
	}

	/**
	 * an admin can individually add a new room to the DB
	 * @param model  holds the room information to store and show to the HTML pages
	 * @param model2 holds the building information to store and show to the HTML pages
	 * @return returns the form to add a room
	 */
	@RequestMapping("/rooms")
	public String roomRegistration(Model model, Model model2) {
		Rooms rooms = new Rooms();
		List<Buildings> listBuildings = buildingsService.listAll();
		model2.addAttribute("buildings", listBuildings);
		model.addAttribute("rooms", rooms);
		return "roomForm";
	}

	/**
	 * Once a room gets saved, this is what happens
	 * @param rooms holds the new rooms to save to the database
	 * @return the admin returns to the location list
	 */
	@RequestMapping(value = "/RoomsSave", method = RequestMethod.POST)
	public String processDeparment(@ModelAttribute("rooms") Rooms rooms) {
		roomService.save(rooms);
		return "redirect:/admin/locationList";
	}

	/**
	 * Admin can also individually delete rooms from the DB
	 * @param id holds the information for the id of the room to be deleted
	 * @return back to the location list
	 */
	@RequestMapping("/roomDelete/{id}")
	public String adminDeleteRoom(@PathVariable(name = "id") Long id) {
		roomService.delete(id);

		return "redirect:/admin/locationList";
	}

	/**
	 * Brings admin to the create user form
	 * @param model holds information to pass to the HTML page
	 * @return the admin create user form
	 */
	@GetMapping("/createUser")
	public String createUser(Model model) {
		model.addAttribute("user", new Users());
		return "adminCreateUser";
	}

	/**
	 * Processes the account thats being registered
	 * @param user holds the info of the user to be stored
	 * @param request holds information for the email to send properly, for HTTP purposes
	 * @param bindingResult holds information about data errors
	 * @return to the success page or two different failure pages
	 * @throws UnsupportedEncodingException holds errors for encoding methods
	 * @throws MessagingException holds errors for basic messaging methods
	 */
	@PostMapping("/registerAccount")
	public String processRegister(@ModelAttribute Users user, HttpServletRequest request, BindingResult bindingResult)
			throws UnsupportedEncodingException, MessagingException {
		if (bindingResult.hasErrors()) {
			return "adminCreateUser";
		}

		String pass = user.getPassword();
		String email = user.getEmail();

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		// stores encoded password instead of plain text
		String encodedPassword = encoder.encode(pass);

		if (checkPassword(pass) == false) {
			return "adminRegistrationFailure";
		} else if (userRepository.existsByEmail(email)) {
			return "adminRegistrationDupeFailure";
		} else {
			user.setPassword(encodedPassword);

			if (checkEmail(user) == false) {
				return "adminRegistration";
			} else {
				userService.register(user, getSiteURL(request));
				return "adminRegistrationSuccess";
			}
		}
	}

	/**
	 * Shows a list of deaprtments and rooms and allows an admin to connect them with a building. 
	 * @param model
	 * @return
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
	 * This is to make sure that the person being register has an email that ends with '@sru.edu' 
	 * @param user passes in the user data
	 * @return if the email is a sru email or not
	 */
	public Boolean checkEmail(Users user) {
		String userEmail = user.getEmail();

		String segments[] = userEmail.split("@");
		String lastSegment = segments[segments.length - 1];

		if (lastSegment.equals("sru.edu")) {
			return true;
		} else {
			return false;
		}
	}

	/**
     * This will check the password to make sure it's a certain length and return false if not
     * @param password is the password the user enters for registration
     * @return returns if the password is the proper length or not
     */
	public boolean checkPassword(String password) {
		if (password.length() < 6) {
			return false;
		} else if (password.length() > 15) {
			return false;
		} else {
			return true;
		}
	}

	/**
     * This is for getting the proper request data for emails
     * @param request holds information for HTTP requests such as sending an email
     * @return returns the request data for emails
     */
	private String getSiteURL(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(), "");
	}
}
