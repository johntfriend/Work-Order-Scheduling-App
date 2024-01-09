package sru.edu.group1.workorders.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import sru.edu.group1.workorders.domain.*;
import sru.edu.group1.workorders.repository.BuildingRepository;
import sru.edu.group1.workorders.repository.DepartmentsRepository;
import sru.edu.group1.workorders.repository.RoleRepository;
import sru.edu.group1.workorders.repository.RoomsRepository;
import sru.edu.group1.workorders.repository.UserRepository;
import sru.edu.group1.workorders.services.*;
@Controller
@RequestMapping("/excel")
public class ExcelController {
	
	@Autowired
	private ExcelService excelService;
    @Autowired
	private BuildingsService buildingsService;
    @Autowired
	private BuildingRepository buildingRepo;
	@Autowired
	private DepartmentsService departmentsService;
	@Autowired
	private DepartmentsRepository departmentRepo;
	@Autowired
	private RoomServices roomService;
	@Autowired
	private RoomsRepository roomRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private RoleRepository roleRepo;
	
	/**
	 * This is where admins will go to upload data with an excel file
	 * @param model passes username into the HTML
	 * @param username currently does nothing
	 * @return a page with buttons to choose where you would like to upload you data
	 */
	@GetMapping("/uploadexcelfiles")
	public String showAddExcel(Model model, String username) {
		model.addAttribute("username", username);
		return "uploadExcel";
	}
	
	/**
	 * This is where you can upload a excel file for buildings
	 * @return the page to upload to building
	 */
	@GetMapping("/uploadBuilding")
	public String addBuildingExcel() {
		return "uploadBuilding";
	}

	/**
	 * This uploads the building excel file
	 * @param excelData holds MultipartFile interface reference which is used to manage data
	 * @param session holds HttpSession interface reference
	 * @return adds buildings to database
	 */
	@PostMapping("/uploadAllFiles")
	public String LoadFromExcel(@RequestParam("file") MultipartFile excelData, HttpSession session, @CurrentSecurityContext(expression="authentication?.name")
    String username, Model model) {
		try {
			ArrayList<Buildings> dataList = new ArrayList<Buildings>();

			FileInputStream thisxlsBuild;
			
			XSSFWorkbook wb;
			XSSFSheet sheet;
			XSSFRow curRow;

			thisxlsBuild = new FileInputStream("data/buildings.xlsx");
			
			wb = new XSSFWorkbook(thisxlsBuild);
			sheet = wb.getSheetAt(0);
			curRow = sheet.getRow(1);

			int count = 1;
			while (curRow.getRowNum() < sheet.getLastRowNum()) {
				count++;
				//long id = excelService.checkIntType(curRow.getCell(0));
				String building = excelService.checkStringType(curRow.getCell(1));
				if (buildingRepo.existsByBuilding(building) == false && building != "") {
					Buildings buildings = new Buildings();
					//buildings.setId(id);
					buildings.setBuilding(building);
					buildingsService.save(buildings);
				}
				curRow = sheet.getRow(count);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		LoadDepartmentFromExcel();
		LoadRoomsFromExcel();
		Users user = userRepo.findByEmail(username);
		Role role = roleRepo.findByname("Admin");
		
		model.addAttribute("Buildings", buildingRepo.findAll());
		model.addAttribute("Departments", departmentRepo.findAll());
		model.addAttribute("Rooms", roomRepo.findAll());
		
		if(user.getRoles().contains(role))
		{
			model.addAttribute("user", "Admin");
			return "uploadSuccess";
		}
		else
		{
			model.addAttribute("user", "Manager");
			return "uploadSuccess";
		}
	}
	

	/**
	 * This is where you can upload a department excel file
	 * @return the page to upload to a department
	 */
	@GetMapping("/uploadDepartment")
	public String addDepartmentExcel(Model model) {
		return "uploadDepartment";
	}

	/**
	 * This uploads the department excel file
	 * @param excelData holds MultipartFile interface reference which is used to manage data
	 * @param session holds HttpSession interface reference
	 * @return adds departments to database
	 */
	public void LoadDepartmentFromExcel() {
		try {
			ArrayList<Departments> dataList = new ArrayList<Departments>();

			FileInputStream thisxls;
			XSSFWorkbook wb;
			XSSFSheet sheet;
			XSSFRow curRow;

			thisxls = new FileInputStream("data/departments.xlsx");
			wb = new XSSFWorkbook(thisxls);
			sheet = wb.getSheetAt(0);
			curRow = sheet.getRow(1);

			int count = 1;
			while (curRow.getRowNum() < sheet.getLastRowNum()) {
				count++;
				//long id = excelService.checkIntType(curRow.getCell(0));
				String department = excelService.checkStringType(curRow.getCell(1));
				if (departmentRepo.existsByDepartment(department) == false && department != "") {
					Departments departments = new Departments();
					//departments.setId(id);
					departments.setDepartment(department);
					departmentsService.save(departments);
				}
				curRow = sheet.getRow(count);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This is where you can upload a excel file for rooms
	 * @return the page to upload to room
	 */
	@GetMapping("/uploadRoom")
	public String addRoomExcel() {
		return "uploadRoom";
	}
	/**
	 * This uploads the room excel file
	 * @param excelData holds MultipartFile interface reference which is used to manage data
	 * @param session holds HttpSession interface reference
	 * @return adds rooms to database
	 */

	public void LoadRoomsFromExcel() {
		try {
			ArrayList<Rooms> dataList = new ArrayList<Rooms>();

			FileInputStream thisxls;
			XSSFWorkbook wb;
			XSSFSheet sheet;
			XSSFRow curRow;

			thisxls = new FileInputStream("data/rooms.xlsx");
			wb = new XSSFWorkbook(thisxls);
			sheet = wb.getSheetAt(0);
			curRow = sheet.getRow(1);

			int count = 1;
			while (curRow.getRowNum() < sheet.getLastRowNum()) {
				count++;
				//long id = excelService.checkIntType(curRow.getCell(0));
				String room = excelService.checkStringType(curRow.getCell(1));
				if (roomRepo.existsByRoomNumbers(room) == false  && room != "") {
					Rooms rooms = new Rooms();
					//rooms.setId(id);
					rooms.setRoomNumbers(room);
					roomService.save(rooms);
				}
				curRow = sheet.getRow(count);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@GetMapping("/uploadFileSelect")
	public String uploadFileSelect()
	{
		return "uploadFileSelect";
	}
	
	@GetMapping("/combineData/{rooms}/{departments}/{buildling}")
	public String combineData(@PathVariable(name = "rooms")long rooms[], 
			@PathVariable(name = "departments") long departments[], 
			@PathVariable(name = "buildling") String building, 
	@CurrentSecurityContext(expression="authentication?.name")
    String username)
	{
		Buildings buildings = buildingRepo.findByBuilding(building);

		for(int i = 0; i < rooms.length; i++)
		{
			
			Rooms room = roomRepo.findById(rooms[i]);
			buildings.addRooms(room);
		}
		for(int i = 0; i < departments.length; i++)
		{
			
			Departments department = departmentRepo.findById(departments[i]);
			buildings.addDepartments(department);
		}
		buildingRepo.save(buildings);
		Users user = userRepo.findByEmail(username);
		Set<Role> role = user.getRoles();
		return "redirect:/" + role.toString().substring(1, role.toString().length() - 1).toLowerCase() + "/Landing";
	}
}
